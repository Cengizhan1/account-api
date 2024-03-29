package com.cengizhanyavuz.account;

import com.cengizhanyavuz.account.dto.AccountCustomerDto;
import com.cengizhanyavuz.account.dto.CreateAccountRequest;
import com.cengizhanyavuz.account.dto.CustomerDto;
import com.cengizhanyavuz.account.model.Customer;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

public class TestSupport {

    public static final String CUSTOMER_API_ENDPOINT = "/v1/customer";
    public static final String ACCOUNT_API_ENDPOINT = "/v1/account";

    public Instant getCurrentInstant() {
        String instantExpected = "2021-06-15T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), Clock.systemDefaultZone().getZone());

        return Instant.now(clock);
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(getCurrentInstant(), Clock.systemDefaultZone().getZone());
    }

    public Customer generateCustomer() {
        return new Customer("customer-id", "customer-name", "customer-surname", Set.of());
    }
    public CustomerDto generateCustomerDto() {
        return new CustomerDto("customer-id", "name", "surname", Set.of());
    }
    public AccountCustomerDto generateAccountCustomerDto(){
        return new AccountCustomerDto("customer-id",
                "customer-name",
                "customer-surname");
    }

    public CreateAccountRequest generateCreateAccountRequest(int initialCredit) {
        return generateCreateAccountRequest("customer-id", initialCredit);
    }

    public CreateAccountRequest generateCreateAccountRequest(String customerId, int initialCredit) {
        return new CreateAccountRequest(customerId, new BigDecimal(initialCredit));
    }
}