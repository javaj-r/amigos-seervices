package org.javid.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(
        scanBasePackages = {
                "org.javid.notification",
                "org.javid.amqp"
        }
)
@EnableEurekaClient
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

/* for seeing uncomment this block
    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig config) {
        return args -> producer.publish(
                new Person("Javid", 35),
                config.getInternalExchange(),
                config.getInternalNotificationRoutingKey());
    }

    record Person(String name, int age) {
    }
*/
}
