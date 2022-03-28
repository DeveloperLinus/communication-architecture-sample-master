package com.communication.proto.bean

class RealCall private constructor(
    private val client: CommunicateClient,
    val request: Request
) : Call {

    private val requestLock = Any()
    override fun execute(callback: Callback) {
        synchronized(requestLock) { client.getDispatcher().execute(this, callback) }
    }

    companion object {
        fun newRealCall(client: CommunicateClient, request: Request): RealCall {
            return RealCall(client, request)
        }
    }
}