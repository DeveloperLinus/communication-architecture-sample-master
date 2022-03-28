package com.communication.iot

import com.communication.iot.bean.IotOptions

class IotClientFactory private constructor() {
    fun getIotClient(iotOptions: IotOptions): IotClient {
        return IotClientImpl.getInstance(iotOptions)
    }

    companion object {
        @get:Synchronized
        val instance = IotClientFactory()
    }
}