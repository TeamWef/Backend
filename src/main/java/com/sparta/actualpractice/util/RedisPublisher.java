package com.sparta.actualpractice.util;

import com.sparta.actualpractice.chat.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {
    
    private static final String MESSAGE = "MESSAGE";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, List<MessageResponseDto>> opsHashMessage;
    
    @PostConstruct
    private void init() {
        opsHashMessage = redisTemplate.opsForHash();
    }
    
    public void publishSave(MessageResponseDto messageResponseDto) {
        
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MessageResponseDto.class));
        String chatroomId = String.valueOf(messageResponseDto.getChatRoomId());
        
        List<MessageResponseDto> messageResponseDtoList = opsHashMessage.get(MESSAGE, chatroomId);
        
        if (messageResponseDtoList == null)
            messageResponseDtoList = new ArrayList<>();
        
        messageResponseDtoList.add(0, messageResponseDto);
        
        opsHashMessage.put(MESSAGE, chatroomId, messageResponseDtoList);
    }
}
