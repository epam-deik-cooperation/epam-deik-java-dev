package com.epam.training.ticketservice;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.account.AccountRepository;
import com.epam.training.ticketservice.account.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AccountInitializer {

    private final AccountRepository accountRepository;

    @Value("${init-admin}")
    private boolean initAdmin;

    @PostConstruct
    private void initAdmin() {
        if (initAdmin) {
            Account admin = Account.builder()
                    .userName("admin")
                    .password("admin")
                    .accountType(AccountType.ADMIN)
                    .build();

            accountRepository.save(admin);
        }
    }


}
