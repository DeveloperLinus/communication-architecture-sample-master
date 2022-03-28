package com.communication.iot

import com.communication.iot.api.ItTopicFactory
import com.communication.iot.api.Topic

interface TopicFactory {
    fun createTopic(): Topic?
    companion object {
        val DEFAULT: TopicFactory = ItTopicFactory()
    }
}