package com.ycsoft.module.manage.service.mqtt;

import jakarta.annotation.Resource;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttProducerService {

    @Resource
    private MqttClient mqttClient;

    /**
     * 发送MQTT消息
     *
     * @param topic   主题
     * @param payload 消息内容
     * @param qos     QoS等级（0/1/2）
     * @param retained 是否保留消息
     */
    public void sendMessage(String topic, String payload, int qos, boolean retained) {
        try {
            if (!mqttClient.isConnected()) {
                throw new IllegalStateException("MQTT客户端未连接");
            }
            
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(qos);
            message.setRetained(retained);
            
            mqttClient.publish(topic, message);
            
        } catch (MqttException e) {
            throw new RuntimeException("MQTT消息发送失败", e);
        }
    }

    // 便捷方法重载
    public void sendMessage(String topic, String payload) {
        sendMessage(topic, payload, 1, true);
    }
}