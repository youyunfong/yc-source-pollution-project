package com.ycsoft.module.manage.runner;

import com.ycsoft.module.manage.service.mqtt.MqttSubscribeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttAutoSubscribeRunner implements ApplicationRunner {
    @Resource
    private  MqttSubscribeService mqttSubscribeService;

    @Value("${mqtt.topic}")
    private String mqttTopic;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Thread.sleep(1000); // 延迟1秒订阅
            mqttSubscribeService.subscribeToTopic(mqttTopic);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}