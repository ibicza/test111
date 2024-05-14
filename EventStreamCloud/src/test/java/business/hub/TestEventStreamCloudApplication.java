<<<<<<< HEAD
package business.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;


@TestConfiguration(proxyBeanMethods = false)
public class TestEventStreamCloudApplication {


	public static void main(String[] args) {
		SpringApplication.from(EventStreamCloudApplication::main).with(TestEventStreamCloudApplication.class).run(args);
	}

}
=======
//package business.hub;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.utility.DockerImageName;
//
//
//@TestConfiguration(proxyBeanMethods = false)
//public class TestEventStreamCloudApplication {
//
//
//	public static void main(String[] args) {
//		SpringApplication.from(EventStreamCloudApplication::main).with(TestEventStreamCloudApplication.class).run(args);
//	}
//
//}
>>>>>>> 13de183e9e3878c84200b33d6758877204bea1ce
