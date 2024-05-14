package business.hub.utils;

import business.hub.services.StreamProcess;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.stereotype.Component;


@Component
public class StreamInit {

    private final StreamsBuilder streamsBuilder;

    private final StreamProcess streamProcess;
            ;

    public StreamInit(StreamsBuilder streamsBuilder, StreamProcess streamProcess) {
        this.streamsBuilder = streamsBuilder;
        this.streamProcess = streamProcess;
    }

    @PostConstruct
    public void init() {

        KStream<String, JsonNode> stream = streamsBuilder.stream(
                "dto-topic",
                Consumed.with(new Serdes.StringSerde(), new JsonSerde<>(JsonNode.class))
        );
        streamProcess.process(stream);
    }
}
