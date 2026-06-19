package com.zeronsoftn.honginsung.userservice.adapter.output.messaging

import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

    @Bean
    fun userEventsExchange() : TopicExchange {
        return TopicExchange(
            UserCreatedRabbitContract.EXCHANGE,
            true,
            false,
        )
    }
}