package com.patil.freelanceApp.config;


import com.patil.freelanceApp.models.Certificate;
import com.patil.freelanceApp.models.UserInfo;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    Map<String, Object> config = new HashMap<>();

    public KafkaConsumerConfiguration(){
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, UserInfo> emailConsumerFactory() {

        return new DefaultKafkaConsumerFactory<String, UserInfo>(config, new StringDeserializer(),
                new JsonDeserializer<>(UserInfo.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserInfo> emailKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserInfo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailConsumerFactory());
        return factory;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, Certificate> certificateDefaultKafkaConsumerFactory() {

        return new DefaultKafkaConsumerFactory<String, Certificate>(config, new StringDeserializer(),
                new JsonDeserializer<>(Certificate.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Certificate> certificateKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Certificate> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(certificateDefaultKafkaConsumerFactory());
        return factory;
    }

}
