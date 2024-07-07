package com.example.Publications.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${rabbit.queue.publications}")
    private String queuePublications;
    @Value("${rabbit.queue.notifications}")
    private String queueNotificationsForSubscribers;
    @Value("${rabbit.queue.like}")
    private String queueLike;
    @Value("${rabbit.queue.comment}")
    private String queueComment;
    @Value("${rabbit.exchange.name}")
    private String exchange;
    @Value("${rabbit.key.publications}")
    private String keyPublications;
    @Value("${rabbit.key.notifications}")
    private String keyNotifications;
    @Value("${rabbit.key.like}")
    private String keyLike;
    @Value("${rabbit.key.comment}")
    private String keyComment;


    @Bean("publications")
    public Queue newPublications(){
        return new Queue(queuePublications, false);
    }
    @Bean("notifications")
    public Queue notifications(){
        return new Queue(queueNotificationsForSubscribers, false);
    }
    @Bean("like")
    public Queue like(){
        return new Queue(queueLike, false);
    }
    @Bean("comment")
    public Queue comment(){
        return new Queue(queueComment, false);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    @Bean
    public Binding bindingNewPublications(@Qualifier("publications") Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(keyPublications);
    }
    @Bean
    public Binding bindingNotifications(@Qualifier("notifications") Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(keyNotifications);
    }
    @Bean
    public Binding bindingLike(@Qualifier("like")Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(keyLike);
    }
    @Bean
    public Binding bindingComment(@Qualifier("comment") Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(keyComment);
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

}
