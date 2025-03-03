package com.ycsoft.framework.mq.mqtt.config;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker-url}")
    private String brokerUrl;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.persistence-dir:/tmp/mqtt-persistence}")
    private String persistenceDir;

    @Value("${mqtt.keep-alive-interval:60}")
    private int keepAliveInterval;

    @Value("${mqtt.connection-timeout:30}")
    private int connectionTimeout;

    @Value("${mqtt.max-reconnect-delay:30000}")
    private int maxReconnectDelay;

    /**
     * MQTT连接选项配置
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setKeepAliveInterval(keepAliveInterval);
        options.setMaxReconnectDelay(maxReconnectDelay);
        options.setConnectionTimeout(connectionTimeout);

        // 可选：配置遗嘱消息（Last Will and Testament）
        // options.setWill("lastWillTopic", "Client Disconnected".getBytes(), 1, false);

        return options;
    }

    /**
     * MQTT客户端配置（带连接状态管理）
     */
    @Bean(destroyMethod = "disconnect")
    public MqttClient mqttClient(MqttConnectOptions options) throws MqttException {
        MqttClientPersistence persistence = new MqttDefaultFilePersistence(persistenceDir);
        MqttClient client = new MqttClient(brokerUrl, clientId, persistence);
        client.setCallback(new MqttCallbackHandler(mqttMessageExecutor()));
        client.connect(options);
        return client;
    }

    /**
     * 消息处理线程池配置
     */
    @Bean // 不再需要指定destroyMethod
    public Executor mqttMessageExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("mqtt-worker-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // Spring 6.x 新增的优雅关闭配置
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    /**
     * 专用回调处理器（分离业务逻辑）
     */
    private static class MqttCallbackHandler implements MqttCallbackExtended {
        private final Executor executor;

        public MqttCallbackHandler(Executor executor) {
            this.executor = executor;
        }

        @Override
        public void connectionLost(Throwable cause) {
            System.err.println("MQTT连接中断: " + cause.getMessage());
            // 可触发重连或告警通知
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            executor.execute(() -> {
                try {
                    // 调用消息处理服务（建议注入实际业务Service）
                    handleMessage(topic, message);
                } catch (Exception e) {
                    System.err.println("消息处理异常: " + e.getMessage());
                }
            });
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            // 可添加消息发送成功后的处理逻辑
        }

        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            if (reconnect) {
                System.out.println("成功重连至: " + serverURI);
                // 可恢复订阅或执行初始化操作
            }
        }
    }

    /**
     * 消息处理逻辑（建议移至独立Service）
     */
    private static void handleMessage(String topic, MqttMessage message) {
        // 实际业务处理逻辑
        System.out.printf("处理消息 - Topic: %s, QoS: %d, Payload: %s%n",
                topic, message.getQos(), new String(message.getPayload()));
    }
}