package business.hub.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

/**
 * author Igor Ostrovsky
 * Kafka message flow configuration. Creating consumer.
 * Configure stream by KStream and ProfileEventCreatingServices
 */

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @PostConstruct
    public void logProperties() {
        log.info("Bootstrap servers: {}", bootstrapServers);
    }

    private final Logger log = LoggerFactory.getLogger(KafkaStreamConfig.class);


    /**
     * Setting up reading a Kafka stream from the "dto-topic" topic,
     * the Serde environment for serializing and deserializing a stream of bytes into string and json,
     * filtering the stream by searching for json messages with data defined in the filter.
     * @param builder
     * Getting the incoming parameter Stream builder.
     * @return
     * Returning the deserialized message in json format
     */
    @Bean
    public KStream<String, JsonNode> kStream(StreamsBuilder builder) {

        Serdes.StringSerde stringSerde = new Serdes.StringSerde();
        JsonSerde<JsonNode> jsonNodeSerde = new JsonSerde<>(JsonNode.class);

        log.info("ПОДКЛЮЧАЕМСЯ К KAFKA И DTO-TOPIC");

            KStream<String, JsonNode> stream = builder.stream("dto-topic", Consumed.with(stringSerde, jsonNodeSerde));

        log.info("ПОДКЛЮЧИЛИСЬ К KAFKA И DTO-TOPIC");

        stream.peek((key, value) -> log.info("СООБЩЕНИЕ ПОЛУЧЕНО СООБЩЕНИЕ ПОЛУЧЕНО - key: {}, value: {}", key, value))
                .filter((key, jsonNode) ->
                jsonNode.has("accountId") && jsonNode.has("firstName") && jsonNode.has("lastName"))
                .foreach((key, value) -> System.out.println("Filtered message: " + value));

        return stream;
    }

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;

    @PostConstruct
    public void checkProperties() {
        System.out.println("Trusted packages: " + trustedPackages);
    }

}
