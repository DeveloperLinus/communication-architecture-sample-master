package com.communication.iot.api

interface Topic {
    fun getRequestTopic(messageId: String): String
}