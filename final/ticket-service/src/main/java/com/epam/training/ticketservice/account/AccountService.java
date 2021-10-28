package com.epam.training.ticketservice.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findByUserName(String userName) {
        return accountRepository.findByUserNameContainingIgnoreCase(userName);
    }


    public void createAccount(String userName, String password) {

        accountRepository.save(Account.builder()
                .userName(userName)
                .password(password)
                .accountType(AccountType.USER)
                .build());
    }


}
