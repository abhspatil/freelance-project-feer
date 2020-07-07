package com.patil.freelanceApp.config;

import com.patil.freelanceApp.models.Certificate;
import com.patil.freelanceApp.models.UserInfo;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KakfaProducerConfiguration {

    Map<String, Object> config = new HashMap<>();

    public KakfaProducerConfiguration(){
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    }

    @Bean
    public ProducerFactory<String, UserInfo> emailproducerFactory() {
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ProducerFactory<String, Certificate> certproducerFactory() {
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, UserInfo> userKafkaTemplate() {
        return new KafkaTemplate<>(emailproducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Certificate> certKafkaTemplate() {
        return new KafkaTemplate<>(certproducerFactory());
    }

}
