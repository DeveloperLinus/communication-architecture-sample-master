package com.communication.iot.bean

import com.communication.iot.api.Topic

class ItTopic : Topic {
    override fun getRequestTopic(messageId: String) = "sample/request/${messageId}"
}