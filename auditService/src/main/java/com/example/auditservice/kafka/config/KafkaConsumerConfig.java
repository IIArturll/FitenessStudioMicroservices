package com.example.auditservice.kafka.config;

import com.example.auditservice.core.dtos.AuditDTO;
import com.example.auditservice.core.properties.KafkaProperty;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {



    private final KafkaProperty kafkaProperty;

    public KafkaConsumerConfig(KafkaProperty kafkaProperty) {
        this.kafkaProperty = kafkaProperty;
    }

    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperty.getBootstrapServers());
        return props;
    }

    @Bean
    public ConsumerFactory<String, AuditDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean()
    public ConcurrentKafkaListenerContainerFactory<String, AuditDTO> factory(
            ConsumerFactory<String, AuditDTO> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, AuditDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
