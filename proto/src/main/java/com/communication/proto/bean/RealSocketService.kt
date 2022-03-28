package com.communication.proto.bean

internal class RealSocketService private constructor(
    private val client: CommunicateClient
) :
    SocketService {
    private val dispatcher: Dispatcher

    private val requestLock = Any()

    override fun isClose(): Boolean {
        return dispatcher.isClose()
    }

    override fun addCallback(callback: Callback) {
        dispatcher.addSocketServiceCallback(callback)
    }

    override fun request(request: Request) {
        synchronized(requestLock) {
            val realCall = RealCall.newRealCall(client, request)
            val socketServiceCallback: Callback = dispatcher.getSocketServiceCallback()
            dispatcher.execute(realCall, socketServiceCallback)
        }
    }

    override fun start() {
        dispatcher.start()
    }

    override fun close() {
        dispatcher.close()
    }

    companion object {
        fun newRealSocketService(client: CommunicateClient): RealSocketService {
            return RealSocketService(client)
        }
    }

    init {
        dispatcher = client.dispatcher
    }
}