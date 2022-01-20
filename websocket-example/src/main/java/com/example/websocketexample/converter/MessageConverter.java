package com.example.websocketexample.converter;

import com.example.websocketexample.dto.MessageDto;
import com.example.websocketexample.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MessageConverter {

    public Function<MessageDto, Message> toEntity() {
        return dto -> {

            if (dto == null)
                return null;

            Message message = new Message();
            message.setContent(dto.getContent());

            return message;
        };
    }

    public Function<Message, MessageDto> toDto() {
        return message -> {
            if (message == null)
                return null;

            MessageDto dto = new MessageDto();
            dto.setContent(message.getContent());
            dto.setUserId(message.getUser().getId());

            return dto;
        };
    }
}
