package org.javid.notification.rabitt;

import lombok.extern.slf4j.Slf4j;
import org.javid.clients.notification.NotificationRequest;
import org.javid.notification.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record NotificationConsumer(NotificationService notificationService) {

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consume(NotificationRequest notificationRequest) {
        log.info("Consumed message form queue: {}", notificationRequest);
        notificationService.send(notificationRequest);
    }
}