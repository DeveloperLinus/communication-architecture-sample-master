package com.communication.iot

import java.lang.Exception


interface IotCallback {
    fun onFailure(e: Exception)

    fun onStatus(status: Int)

    fun onResponse()
}