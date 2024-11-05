package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.CreateNewMessageException;
import com.example.exception.UpdateMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    
    @Transactional
    public Message createMessage(Message message) {
        Optional<Account> findUser = accountRepository.findAccountByAccountId(message.getPostedBy());
        if ((findUser.isEmpty()) || (message.getMessageText().length() > 255) || (message.getMessageText().isBlank())) {
            throw new CreateNewMessageException();   
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findMessageByMessageId(id);
    }
    
    @Transactional
    public Integer deleteMessageById(Integer id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return 1;
        }
        else{
            return null;
        }
    }

    @Transactional
    public Integer updateMessageById(Integer id, Message message){
        if ((message.getMessageText().isBlank()) || (message.getMessageText().length() > 255) || (!messageRepository.existsById(id))) {
            throw new UpdateMessageException();
        }
        Message foundMessage = messageRepository.getById(id);
        foundMessage.setMessageText(message.getMessageText());
        return 1;
    }

    public List<Message> getMessagesByAccountId(Integer id){
        return messageRepository.findByPostedBy(id);
    }
}
