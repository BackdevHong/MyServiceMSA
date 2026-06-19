package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class HealthQueryController {
    @QueryMapping
    fun health(): String = "OK"
}