package com.communication.proto.bean

open class MqttRequest(data: ByteArray, var topic: String, var qos:Int = 1) : Request(data = data)