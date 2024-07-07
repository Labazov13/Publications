package com.example.Publications.services;

import com.example.Publications.models.Comment;
import com.example.Publications.models.Like;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
    @Value("${rabbit.key.like}")
    private String keyLike;
    @Value("${rabbit.key.comment}")
    private String keyComment;
    @Value("${rabbit.exchange.name}")
    private String exchange;
    private final RabbitTemplate rabbitTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @RabbitListener(queues = {"${rabbit.queue.like}", "${rabbit.queue.comment}"})
    public void receiveActivity(String message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        if (routingKey.equals(keyLike)){
            LOGGER.info("Got a like");
        } else if (routingKey.equals(keyComment)) {
            LOGGER.info("Got a comment");
        }
        else {
            LOGGER.error("Unknown type message");
        }
    }

    public String like(String username){
        Like like = new Like(username);
        String message = "Like to user: " + like.username();
        rabbitTemplate.convertAndSend(exchange, keyLike, message);
        LOGGER.info(message);
        return message;
    }
    public String comment(Comment comment){
        String message = "Comment to user: " + comment.username() + ", Details: " + comment.comment();
        rabbitTemplate.convertAndSend(exchange, keyComment, message);
        LOGGER.info(message);
        return message;
    }
}
