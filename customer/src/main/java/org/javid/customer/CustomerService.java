package org.javid.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository,
                              RestTemplate restTemplate, Properties properties) {

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
        log.info(properties.getUrls().get("fraud-check"), customer.getId());
        var fraudCheckResponse = restTemplate.getForObject(
                properties.getUrls().get("fraud-check"),
                FraudCheckResponse.class,
                customer.getId());

        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        // todo: send notification
    }
}
