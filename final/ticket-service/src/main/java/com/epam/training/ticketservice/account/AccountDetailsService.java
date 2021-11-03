package com.epam.training.ticketservice.account;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountService accountService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            Account account = accountService.findByUserName(userName);

            User.UserBuilder builder = User.withUsername(userName);
            builder.password(new BCryptPasswordEncoder().encode(account.getPassword()));

            if (account.getAccountType().equals(AccountType.ADMIN)) {
                builder.roles("USER", "ADMIN");
            } else if (account.getAccountType().equals(AccountType.USER)) {
                builder.roles("USER");
            }


            return builder.build();
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("User does not exist");
        }
    }
}
