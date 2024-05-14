package business.hub;

import business.hub.dto.DtoCreateEvent;
import business.hub.entity.ProfileEvent;
import business.hub.repository.ProfileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EventStreamCloudApplication.class)
@Testcontainers
@ActiveProfiles("test")
public class EventStreamCloudApplicationTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private KafkaProperties kafkaProperties;

    private KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    private static final Logger logger = LoggerFactory.getLogger(EventStreamCloudApplicationTest.class);
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new                                                                                    PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("2810");

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeAll
    static void startUp() {
        Map<String, Object> adminConfigs = new HashMap<>();
        adminConfigs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());

        logger.info("НАЧИНАЕМ СОЗДАНИЕ ТОПИКА DTO-TOPIC");
        // Создание топика
        try (AdminClient adminClient = AdminClient.create(adminConfigs)) {
            NewTopic newTopic = new NewTopic("dto-topic", 1, (short) 1);
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();

            logger.info("ТОПИК ГОТОВ К ЭКСПЛУАТАЦИИ");
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                logger.info("ТОПИК УЖЕ СУЩЕСТВУЕТ");
            } else {
                logger.error("ТОПИК НЕ ГОТОВ К ИСПОЛЬЗОВАНИЮ: " + e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("ОПЕРАЦИЯ БЫЛА ПРЕРВАНА: " + e.getMessage(), e);
            throw new RuntimeException("Операция создания топика была прервана", e);
        }
    }
    @BeforeEach
    void setUp() {
        System.out.println(kafkaProperties.getProperties());

        Map<String, Object> configs = new HashMap<>(Map.of(
                "bootstrap.servers", kafkaContainer.getBootstrapServers()
        ));
        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer<>(new ObjectMapper())));
    }

    @AfterEach
   void tearDown() {
        profileRepository.deleteAll();
        postgresContainer.stop();
    }

    @Test
    void profileEventFlowTest() throws InterruptedException, ExecutionException{

        logger.info("НАЧИНАЕМ, НАЧИНАЕМ, НАЧИНАЕМ, НАЧИНАЕМ, НАЧИНАЕМ, НАЧИНАЕМ, НАЧИНАЕМ,");

        DtoCreateEvent dto = new DtoCreateEvent("accountId", "Igor", "Ostrovsky");

        logger.info("НАЧИНАЕМ ОТПРАВКУ СООБЩЕНИЯ: {}",  dto);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.convertValue(dto, JsonNode.class);

        CompletableFuture<SendResult<String, JsonNode>> future = kafkaTemplate.send("dto-topic", node);
        future.whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error("ОШИБКА ПРИ ОТПРАВКЕ СООБЩЕНИЯ ", exception);
                    } else {
                        logger.info("СООБЩЕНИЕ ОТПРАВЛЕНО: {}", result);
                    }
                });
        future.get();

        kafkaTemplate.flush();

        Thread.sleep(10000);

        Optional<ProfileEvent> profileEventOptional = profileRepository.findByAccountId("accountId");
        assertThat(profileEventOptional).isPresent();

        profileEventOptional.ifPresent(event -> {
            assertThat(event.getFirstName()).isEqualTo("Igor");
            assertThat(event.getLastName()).isEqualTo("Ostrovsky");
        });
    }
}
