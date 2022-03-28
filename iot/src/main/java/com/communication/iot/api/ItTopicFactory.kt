package com.communication.iot.api

import com.communication.iot.TopicFactory
import com.communication.iot.bean.ItTopic

class ItTopicFactory : TopicFactory {
    override fun createTopic(): Topic = ItTopic()
}