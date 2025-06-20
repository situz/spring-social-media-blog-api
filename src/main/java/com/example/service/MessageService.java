package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message postMessage(Message message){
        if ((message.getMessageText() != null) && (message.getMessageText().length() > 0) && (message.getMessageText().length() <= 255)){
            if (accountRepository.findById(message.getPostedBy()).isPresent()){
                Message newMessage = messageRepository.save(message);
                return newMessage;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessages(){
        List<Message> allMessages = (List<Message>) messageRepository.findAll();
        return allMessages;
    }


}
