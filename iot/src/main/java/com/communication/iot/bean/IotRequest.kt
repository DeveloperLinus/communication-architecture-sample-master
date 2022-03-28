package com.communication.iot.bean

import com.communication.proto.bean.MqttRequest

class IotRequest : MqttRequest {
    constructor(data: ByteArray, topic: String, qos: Int) : super(
        data,
        topic,
        qos
    ) {
    }

    constructor(data: String, topic: String, qos: Int) : super(
        data.toByteArray(),
        topic,
        qos
    ) {
    }

    constructor(data: ByteArray, topic: String) : super(data, topic, 1) {}
    constructor(data: String, topic: String) : super(data.toByteArray(), topic, 1) {}
}