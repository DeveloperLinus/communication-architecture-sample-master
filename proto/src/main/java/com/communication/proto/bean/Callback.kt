package com.communication.proto.bean

import java.lang.Exception

interface Callback {
    fun onFailure(e: Exception)

    fun onStatus(status: Int)

    fun onResponse()
}