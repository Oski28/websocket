package com.example.websocketexample.controller;

import com.example.websocketexample.converter.MessageConverter;
import com.example.websocketexample.dto.MessageDto;
import com.example.websocketexample.model.Message;
import com.example.websocketexample.repository.MessageRepository;
import com.example.websocketexample.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MessageController {

    private final MessageRepository messageRepository;
    private final MessageConverter messageConverter;
    private final UserServiceImplementation userService;

    @Autowired
    public MessageController(MessageRepository messageRepository, MessageConverter messageConverter,
                             UserServiceImplementation userService) {
        this.messageRepository = messageRepository;
        this.messageConverter = messageConverter;
        this.userService = userService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat/sent")
    public MessageDto send(@Payload MessageDto dto) {
        Message message = messageConverter.toEntity().apply(dto);
        message.setUser(userService.getById(dto.getUserId()));
        message = this.messageRepository.save(message);
        return this.messageConverter.toDto().apply(message);
    }

    @SubscribeMapping("/chat/get")
    public List<MessageDto> get() {
        return this.messageRepository.findAll().stream().map(this.messageConverter.toDto()).collect(Collectors.toList());
    }

    @MessageExceptionHandler
    @SendToUser("/topic/error")
    public String handleException(Exception exception) {
        return exception.getMessage();
    }
}
