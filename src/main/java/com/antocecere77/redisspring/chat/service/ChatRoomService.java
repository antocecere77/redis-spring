package com.antocecere77.redisspring.chat.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements WebSocketHandler {

    private final RedissonReactiveClient client;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {

        String room = getChatRoom(webSocketSession);
        RTopicReactive topic = this.client.getTopic(room, StringCodec.INSTANCE);

        //subscribe
        webSocketSession.receive()
            .doOnNext(WebSocketMessage::getPayloadAsText)
            .flatMap(topic::publish)
            .doOnError(System.out::println)
            .doFinally(s -> System.out.println("subscriber finally " + s))
            .subscribe();

        //publisher
        Flux<WebSocketMessage> flux = topic.getMessages(String.class)
                .map(webSocketSession::textMessage)
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("publisher finally " + s));

        return webSocketSession.send(flux);
    }

    private String getChatRoom(WebSocketSession socketSession) {
        URI uri = socketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}











