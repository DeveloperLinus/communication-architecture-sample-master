package com.communication.iot.bean

import com.communication.iot.TopicFactory
import javax.net.SocketFactory

class IotOptions private constructor(
    val address: String?,
    val port: Int?,
    val socketFactory: SocketFactory?,
    val topicFactory: TopicFactory?
) {
    private constructor(builder: Builder) : this(
        builder.address,
        builder.port,
        builder.socketFactory,
        builder.topicFactory
    )

    class Builder {
        var address: String? = null
        var port: Int? = null
        var socketFactory: SocketFactory? = null
        var topicFactory: TopicFactory? = null
        fun setAddress(address: String?) = apply {
            this.address = address
        }
        fun setPort(port: Int?) = apply {
            this.port = port
        }
        fun setSocketFactory(socketFactory: SocketFactory?) = apply {
            this.socketFactory = socketFactory
        }
        fun setTopicFactory(topicFactory: TopicFactory?) = apply {
            this.topicFactory = topicFactory
        }
        fun build() = IotOptions(this)
    }
}