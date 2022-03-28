package com.communication.iot.wapper

import com.communication.iot.bean.IotOptions

class IotWrapper(var iotOptions: IotOptions) {
    @Synchronized
    fun getUserName() {
        "${iotOptions.port}xxx"
    }
}