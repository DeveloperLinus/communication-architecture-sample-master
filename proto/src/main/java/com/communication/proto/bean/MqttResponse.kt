package com.communication.proto.bean

open  class MqttResponse(data: ByteArray, var topic: String, var qos: Int) : Response(data) {
}