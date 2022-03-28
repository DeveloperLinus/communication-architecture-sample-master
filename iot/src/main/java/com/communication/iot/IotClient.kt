package com.communication.iot

import com.communication.iot.bean.IotRequest

interface IotClient {
    fun send(iotRequest: IotRequest)

    fun addCallback(callback: IotCallback)

    fun startup()

    fun shutdown()
}