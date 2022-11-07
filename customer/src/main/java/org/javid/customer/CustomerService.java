package org.javid.customer;

import lombok.extern.slf4j.Slf4j;
import org.javid.amqp.RabbitMQMessageProducer;
import org.javid.clients.fraud.FraudClient;
import org.javid.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record CustomerService(
        CustomerRepository customerRepository,
        FraudClient fraudClient,
        RabbitMQMessageProducer rabbitMQMessageProducer) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .build();

        // todo: check if email valid
        // todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        var fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        // todo: send notification
        var notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                "Hi %s, welcom to Amigos Project".formatted(customer.getFirstname())
        );

        publishNotification(notificationRequest);
    }

    private void publishNotification(NotificationRequest notificationRequest) {
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal-exchange",
                "internal.notification.routing-key");
    }
}
