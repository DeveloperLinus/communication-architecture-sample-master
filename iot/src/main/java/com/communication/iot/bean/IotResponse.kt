package com.communication.iot.bean

import com.communication.proto.bean.MqttResponse


data class IotResponse (var mqttResponse: MqttResponse, var messageId: String)