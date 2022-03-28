package com.communication.proto.bean

interface SocketService {
    fun addCallback(callback: Callback)

    fun start()

    fun request(request: Request)

    fun close()

    fun isClose(): Boolean
}