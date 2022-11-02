package org.javid.notification;

import org.javid.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public record NotificationService(NotificationRepository notificationRepository) {

    public void send(NotificationRequest request) {
        notificationRepository.save(
                Notification.builder()
                        .customerId(request.customerId())
                        .customerEmail(request.customerEmail())
                        .sender("Javid")
                        .message(request.message())
                        .build()
        );
    }
}
