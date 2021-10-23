package com.epam.training.ticketservice.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findByUserName(String userName) {
        return accountRepository.findByUserName(userName);
    }





}
