package business.hub.services;

import business.hub.dto.DtoCreateEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StreamProcess {
    private final ProfileEventCreatingServices services;

    private final Logger logger = LoggerFactory.getLogger(StreamProcess.class);

    public StreamProcess(ProfileEventCreatingServices services) {
        this.services = services;
    }

    public DtoCreateEvent convertToDto(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(jsonNode, DtoCreateEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON", e);
        }
    }

    public void process(KStream<String, JsonNode> stream) {

        stream.mapValues(this::convertToDto).foreach((key, dto) -> {
            try {
                services.saveToDatabase(dto);
                logger.info("Successfully saved DTO to database: {}", dto);
            } catch (Exception e) {
                logger.error("Error saving DTO to database {}", dto, e);
            }
        });
    }
}
