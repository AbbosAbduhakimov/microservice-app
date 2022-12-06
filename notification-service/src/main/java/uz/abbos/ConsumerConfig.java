//package uz.abbos;
//
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Configuration
//public class ConsumerConfig {
//
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String brokers;
//    @Bean
//    public ConsumerFactory<String, OrderPlacedEvent> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(org.apache.kafka.clients.CommonClientConfigs.
//                BOOTSTRAP_SERVERS_CONFIG, brokers);
//        props.put(org.apache.kafka.clients.CommonClientConfigs.
//                GROUP_ID_CONFIG, groupId);
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(OrderPlacedEvent.class));
//    }
//    @Bean
//    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderPlacedEvent>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//}
