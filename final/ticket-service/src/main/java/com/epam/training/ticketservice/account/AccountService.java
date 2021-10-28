package com.epam.training.ticketservice.account;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findByUserName(String userName) throws NotFoundException {

        Account account = accountRepository.findByUserNameContainingIgnoreCase(userName);

        if (account != null) {
            return account;
        } else {
            throw new NotFoundException("User does not exist with such name");
        }
    }


    public void createAccount(String userName, String password) throws IllegalArgumentException {
        if (userName.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Username/Password fields can't be blank!");
        } else {
            accountRepository.save(Account.builder()
                    .userName(userName)
                    .password(password)
                    .accountType(AccountType.USER)
                    .build());
        }
    }


}
