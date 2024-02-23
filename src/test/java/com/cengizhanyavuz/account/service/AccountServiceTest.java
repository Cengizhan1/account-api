package com.cengizhanyavuz.account.service;

import com.cengizhanyavuz.account.TestSupport;
import com.cengizhanyavuz.account.dto.AccountCustomerDto;
import com.cengizhanyavuz.account.dto.AccountDto;
import com.cengizhanyavuz.account.dto.CreateAccountRequest;
import com.cengizhanyavuz.account.dto.TransactionDto;
import com.cengizhanyavuz.account.dto.converter.AccountDtoConverter;
import com.cengizhanyavuz.account.exception.CustomerNotFoundException;
import com.cengizhanyavuz.account.model.Account;
import com.cengizhanyavuz.account.model.Customer;
import com.cengizhanyavuz.account.model.Transaction;
import com.cengizhanyavuz.account.model.TransactionType;
import com.cengizhanyavuz.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest extends TestSupport {

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter converter;
    private Clock clock;
    private AccountService service;

    @BeforeEach
    public void setUp() {
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        converter = mock(AccountDtoConverter.class);
        clock = mock(Clock.class);

        Clock clock = mock(Clock.class);

        service = new AccountService(accountRepository, customerService, converter, clock);

        when(clock.instant()).thenReturn(getCurrentInstant());
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
    }

    @Test
    public void testCreateAccount_whenCustomerIdExistsAndInitialCreditMoreThanZero_shouldCreateAccountWithTransaction() {
        CreateAccountRequest request = generateCreateAccountRequest(0);
        Customer customer = generateCustomer();
        AccountCustomerDto customerDto = generateAccountCustomerDto();

        Account account = generateAccount(0,customer);
        Transaction transaction = new Transaction(null, TransactionType.INITIAL, request.getInitialCredit(),
                getLocalDateTime(), account);
        account.getTransaction().add(transaction);

        TransactionDto transactionDto = new TransactionDto("", TransactionType.INITIAL, new BigDecimal(100),
                getLocalDateTime());
        AccountDto expected = new AccountDto("account-id", new BigDecimal(100), getLocalDateTime(),
                customerDto, Set.of(transactionDto));

        when(customerService.findCustomerById("customer-id")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);

        when(converter.convert(account)).thenReturn(expected);

        AccountDto result = service.createAccount(request);

        assertEquals(result, expected);
    }

    @Test
    public void testCreateAccount_whenCustomerIdExistsAndInitialCreditIsZero_shouldCreateAccountWithTransaction() {
        CreateAccountRequest request = generateCreateAccountRequest(100);
        Customer customer = generateCustomer();
        AccountCustomerDto customerDto = generateAccountCustomerDto();

        Account account = generateAccount(100,customer);

        AccountDto expected = new AccountDto("account-id", new BigDecimal(100), getLocalDateTime(),
                customerDto, Set.of());

        when(customerService.findCustomerById("customer-id")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        when(converter.convert(account)).thenReturn(expected);

        AccountDto result = service.createAccount(request);

        assertEquals(result, expected);
    }

    @Test
    public void testCreateAccount_whenCustomerIdDoesNotExistsAndInitialCreditMoreThanZero_shouldCreateAccountWithTransaction() {
        CreateAccountRequest request = generateCreateAccountRequest(0);

        when(customerService.findCustomerById("customer-id")).thenThrow(new CustomerNotFoundException("test-exception"));

        assertThrows(CustomerNotFoundException.class,
                () -> service.createAccount(request));

        verify(customerService).findCustomerById(request.getCustomerId());
        verifyNoInteractions(accountRepository);
        verifyNoInteractions(converter);
    }


    private Account generateAccount(int balance,Customer customer) {
        return new Account("", new BigDecimal(balance), getLocalDateTime(), customer, new HashSet<>());
    }
}