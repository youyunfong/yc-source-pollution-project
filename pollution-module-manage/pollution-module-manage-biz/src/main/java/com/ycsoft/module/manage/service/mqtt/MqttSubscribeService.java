package com.ycsoft.module.manage.service.mqtt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ycsoft.module.manage.dal.dataopject.*;
import com.ycsoft.module.manage.dal.mysql.device.DeviceCommPollutionMapper;
import com.ycsoft.module.manage.dal.mysql.flow.FlowHistoryMapper;
import com.ycsoft.module.manage.dal.mysql.flow.FlowRealtimeMapper;
import com.ycsoft.module.manage.dal.mysql.grade.GradeHistoryMapper;
import com.ycsoft.module.manage.dal.mysql.grade.GradeRealtimeMapper;
import com.ycsoft.module.manage.dal.mysql.water.WaterHistoryMapper;
import com.ycsoft.module.manage.dal.mysql.water.WaterRealtimeMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
@Transactional
public class MqttSubscribeService {
    @Resource
    private IMqttClient mqttClient;
    @Resource
    private DeviceCommPollutionMapper deviceCommPollutionMapper;
    @Resource
    private GradeHistoryMapper gradeHistoryMapper;
    @Resource
    private GradeRealtimeMapper gradeRealtimeMapper;
    @Resource
    private FlowRealtimeMapper flowRealtimeMapper;
    @Resource
    private FlowHistoryMapper flowHistoryMapper;
    @Resource
    private WaterHistoryMapper waterHistoryMapper;
    @Resource
    private WaterRealtimeMapper waterRealtimeMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Executor executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new ThreadFactoryBuilder().setNameFormat("mqtt-worker-%d").build()
    );
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic, 1, (t, message) -> executor.execute(() -> {
                try {
                    String factoryDeviceId = extractDeviceId(t);
                    List<DeviceCommPollutionDO> dataList = parseMessage(message, factoryDeviceId);

                    if (!dataList.isEmpty()) {
                        dataList.forEach(this::processSensorData);
                    }
                } catch (Exception e) {
                    log.error("消息处理失败 - Topic: {}", t, e);
                }
            }));
            log.info("成功订阅主题: {}", topic);
        } catch (MqttException e) {
            log.error("订阅失败: {}", e.getMessage());
        }
    }

    private String extractDeviceId(String topic) {
        String[] parts = topic.split("/");
        if (parts.length >= 3) {
            return parts[parts.length - 1];
        }
        throw new IllegalArgumentException("无效主题格式: " + topic);
    }

    private List<DeviceCommPollutionDO> parseMessage(MqttMessage message, String factoryDeviceId) {
        try {
            String payload = cleanPayload(message.getPayload());

            // 严格校验JSON格式
            if (!payload.startsWith("[")) {
                log.warn("过滤订阅mqtt时初始数据: {}", payload);
                return Collections.emptyList();
            }

            List<DeviceCommPollutionDO> dataList = objectMapper.readValue(payload, new TypeReference<>() {});

            // 统一设置公共字段
            dataList.forEach(data -> {
                data.setFactoryDeviceId(factoryDeviceId);
                data.setInsertTime(LocalDateTime.now());
            });

            return dataList;
        } catch (IOException e) {
            log.error("JSON解析失败: {}", new String(message.getPayload()), e);
            return Collections.emptyList();
        }
    }

    private String cleanPayload(byte[] payload) {
        return new String(payload, StandardCharsets.UTF_8)
                .replaceAll("[\\u0000-\\u001F]", "")  // 移除控制字符
                .replaceAll("\\s+$", "")             // 移除末尾空白
                .trim();
    }

    private void processSensorData(DeviceCommPollutionDO data) {
        try {
            log.info("处理数据 - {}", data);
            deviceCommPollutionMapper.insert(data);

            // 新增档位处理逻辑
            if (data.getPortId() == 10) { // 仅处理档位相关数据
                handleGradeData(data);
            }else if (data.getPortId() == 5) {
                handleWaterData(data);
            }else if (data.getPortId() == 0) {
                handleFlowData(data);
            }
        } catch (Exception e) {
            log.error("数据持久化失败: {}", data, e);
            throw new RuntimeException("数据处理失败", e);
        }
    }
    //更新档位表
    private void handleGradeData(DeviceCommPollutionDO data) {
        String factoryDeviceId = data.getFactoryDeviceId();
        String grade = data.getSdata();
        String currentTime = LocalDateTime.now().format(DATE_FORMATTER);

        // 1. 保存到历史记录表
        GradeHistoryDO historyRecord = GradeHistoryDO.builder()
                .factoryDeviceId(factoryDeviceId)
                .grade(Integer.parseInt(grade))
                .tm(currentTime)
                .build();
        gradeHistoryMapper.insert(historyRecord);

        // 2. 更新实时表（存在则更新，不存在则插入）
        GradeRealtimeDO realtimeRecord = GradeRealtimeDO.builder()
                .factoryDeviceId(factoryDeviceId)
                .grade(Integer.parseInt(grade))
                .tm(currentTime)
                .build();
        gradeRealtimeMapper.insertOrUpdate(realtimeRecord);
    }


    //更新水位表
    private void handleWaterData(DeviceCommPollutionDO data) {
        String factoryDeviceId = data.getFactoryDeviceId();
        String water = data.getSdata();
        String currentTime = LocalDateTime.now().format(DATE_FORMATTER);

        // 1. 保存到历史记录表
        WaterHistoryDO waterHistory = WaterHistoryDO.builder()
                .factoryDeviceId(factoryDeviceId)
                .water(BigDecimal.valueOf(Long.parseLong(water)))
                .tm(currentTime)
                .build();
        waterHistoryMapper.insert(waterHistory);
        // 2. 更新实时表（存在则更新，不存在则插入）
        WaterRealtimeDO waterRealtime = WaterRealtimeDO.builder()
                .factoryDeviceId(factoryDeviceId)
                .water(BigDecimal.valueOf(Long.parseLong(water)))
                .tm(currentTime)
                .build();
        waterRealtimeMapper.insertOrUpdate(waterRealtime);
    }


    //更新流量表
    private void handleFlowData(DeviceCommPollutionDO data) {
        String factoryDeviceId = data.getFactoryDeviceId();
        String flow = data.getSdata();
        String currentTime = LocalDateTime.now().format(DATE_FORMATTER);

        // 1. 保存到历史记录表
        FlowHistoryDO FlowHistory = FlowHistoryDO.builder()
                .factoryDeviceId(factoryDeviceId)
                .flow(BigDecimal.valueOf(Long.parseLong(flow)))
                .tm(currentTime)
                .build();
        flowHistoryMapper.insert(FlowHistory);
        // 2. 更新实时表（存在则更新，不存在则插入）
        FlowRealtimeDO flowRealtime = FlowRealtimeDO.builder()
                .factoryDeviceId(factoryDeviceId)
                .flow(BigDecimal.valueOf(Long.parseLong(flow)))
                .tm(currentTime)
                .build();
        flowRealtimeMapper.insertOrUpdate(flowRealtime);
    }
}