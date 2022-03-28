package com.communication.proto.bean

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque

class Dispatcher(val client: CommunicateClient) {
    private lateinit var communicateProtocol: CommunicateProtocol
    private val asyncCallQueue: LinkedBlockingDeque<AsyncCall> by lazy {
        LinkedBlockingDeque<AsyncCall>()
    }

    @Volatile
    private lateinit var callback: Callback

    @Volatile
    var close = true

    fun isClose(): Boolean {
        return close
    }

    private val requestThreadPool =
        Executors.newSingleThreadExecutor { r ->
            val thread = Thread(r)
            thread.name = "REQUEST"
            thread
        }

    private val statusThreadPool =
        Executors.newSingleThreadExecutor { r ->
            val thread = Thread(r)
            thread.name = "STATUS"
            thread
        }

    private val resultThreadPool =
        Executors.newSingleThreadExecutor { r ->
            val thread = Thread(r)
            thread.name = "RESULT"
            thread
        }

    fun start() {
        if (!close) {
            PrinterHelper.print(TAG, "socket service is already started!")
            return
        }
        close = false
        if (communicateProtocol == null) {
            val options: Options = client.getOptions()
      if (options is MqttOptions) {
                communicateProtocol = Mqtt(options as MqttOptions, callback)
            } else {
                throw RuntimeException(
                    "no such options call " + options.getClass().getSimpleName()
                )
            }
        }
        createQueueThreadPool()
    }

    private class AsyncCall(var realCall: RealCall, var callback: Callback)

    fun addSocketServiceCallback(callback: Callback) {
        this.callback = callback
    }


    fun getSocketServiceCallback(): Callback {
        return callback
    }

    fun execute(realCall: RealCall, callback: Callback) {
        asyncCallQueue.offer(AsyncCall(realCall, callback))
    }

    fun close() {
    }
}