package org.javid.customer;

public record CustomerRegistrationRequest(
        String firstname,
        String lastname,
        String email) {

}
