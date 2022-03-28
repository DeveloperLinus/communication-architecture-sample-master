package com.communication.proto.bean

interface CommunicateProtocol {
    fun send(request: Request, callback: Callback)

    @Throws(InterruptedException::class)
    fun waitStatus(): AsyncStatus

    @Throws(InterruptedException::class)
    fun waitResult(): AsyncResult<*>

    fun shutdown()
}