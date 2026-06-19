package com.zeronsoftn.honginsung.notificationservice.adapter.input.messaging

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class NotificationRabbitMqConfig {

    @Bean
    fun userEventsExchange(): TopicExchange {
        return TopicExchange(
            UserCreatedRabbitContract.EXCHANGE,
            true,
            false,
        )
    }

    @Bean
    fun userCreatedQueue(): Queue {
        return Queue(
            UserCreatedRabbitContract.QUEUE,
            true,
        )
    }

    @Bean
    fun userCreatedBinding(
        userCreatedQueue: Queue,
        userEventsExchange: TopicExchange,
    ): Binding {
        return BindingBuilder
            .bind(userCreatedQueue)
            .to(userEventsExchange)
            .with(UserCreatedRabbitContract.ROUTING_KEY)
    }
}