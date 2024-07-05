package com.example.Publications.configurations;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${rabbit.queue.publications}")
    private String queueName;

    @Value("${rabbit.key.publications}")
    private String routingKey;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory.getRabbitConnectionFactory();
    }

    @Bean("newPublications")
    public Queue newPublications(){
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    }
    @Bean
    public Binding bindingNewPublications(@Qualifier("newPublications")Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey);
    }

}


/*@Bean("notificationsForSubscribers")
    public Queue notificationsForSubscribers(){
        return new Queue("${rabbit.queue.notifications}", false);
    }
    @Bean("newLike")
    public Queue newLike(){
        return new Queue("${rabbit.queue.like}", false);
    }
    @Bean("newComment")
    public Queue newComment(){
        return new Queue("${rabbit.queue.comment}", false);
    }*/

/*@Bean
    public Binding bindingNotifications(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("${rabbit.key.notifications}");
    }
    @Bean
    public Binding bindingLike(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("${rabbit.key.like}");
    }
    @Bean
    public Binding bindingComment(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("${rabbit.key.comment}");
    }*/