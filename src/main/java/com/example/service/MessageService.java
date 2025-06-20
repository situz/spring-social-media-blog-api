package com.example.service;

import java.util.List;
import java.util.Optional;

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

    public Message getMessageById(int id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()){
            return messageOptional.get();
        }
        else{
            return null;
        }
    }

    public int deleteMessageById(int id){
        if (getMessageById(id) != null){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public int patchMessageByIdAndMessageText(int id, String messageText){
        if ((messageText == null) || (messageText.length() <= 0) || (messageText.length() > 255)){
            return 0;
        }
        if (messageRepository.existsById(id) == true){
            Message message = messageRepository.findById(id).get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesByPostedBy(int postedBy){
        List<Message> allMessages = messageRepository.findMessagesByPostedBy(postedBy);
        return allMessages;
    }


}
