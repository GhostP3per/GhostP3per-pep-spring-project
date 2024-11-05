package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UserLoginException;
import com.example.exception.UserRegistrationException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    /*
     * The registration will be successful if and only if the username is not blank, 
     * the password is at least 4 characters long, and an Account with that username
     *  does not already exist.
     */
    @Transactional
    public Account registerUser(Account newAccount) {
        if (newAccount.getUsername().isEmpty()) {
            throw new UserRegistrationException();
        } 
        if (newAccount.getPassword().length() < 4) {
            throw new UserRegistrationException();
        }

        Optional<Account> findUsername = accountRepository.findAccountByUsername(newAccount.getUsername());
        if (findUsername.isPresent()) {
            throw new DuplicateUsernameException();
        }
        return accountRepository.save(newAccount);
    }

    public Account loginUser(Account account) {
        Optional<Account> findUsername = accountRepository.findAccountByUsername(account.getUsername());
        if (findUsername.isEmpty()) {
            throw new UserLoginException(); 
        }
        Account foundAccount = findUsername.get();
        if ((foundAccount.getUsername().equals(account.getUsername())) && (foundAccount.getPassword().equals(account.getPassword()))) {
            return foundAccount;
        }
        else {
            throw new UserLoginException();
        }
    } 

}