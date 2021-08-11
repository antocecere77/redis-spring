package com.antocecere77.redisspring.chat.config;

import com.antocecere77.redisspring.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ChatRoomSocketConfig {

    private final ChatRoomService chatRoomService;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = Map.of(
                "/chat", chatRoomService
        );
        return new SimpleUrlHandlerMapping(map, -1);
    }
}
