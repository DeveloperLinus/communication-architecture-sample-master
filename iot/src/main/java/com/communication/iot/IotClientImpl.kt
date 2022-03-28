package com.communication.iot

import com.communication.iot.bean.IotOptions
import com.communication.iot.bean.IotRequest
import com.communication.iot.wapper.IotWrapper
import com.communication.proto.bean.Callback
import com.communication.proto.bean.SocketService
import java.util.concurrent.Executors
import javax.net.SocketFactory

internal class IotClientImpl private constructor(iotOptions: IotOptions) : IotClient, Callback {
    private var socketService: SocketService? = null
    private var iotWrapper: IotWrapper? = null
    private var iotCallback: IotCallback? = null
    private val cacheThreadPool =
        Executors.newCachedThreadPool()
    private val sendLock = Any()

    @Volatile
    var isConnected = false
        private set
    private var topic: Topic? = null
    private fun initMqttWithOptions(iotOptions: IotOptions) {
        val mqttOptions: MqttOptions = createWithIotOptions(iotOptions)
        val communicateClient: CommunicateClient = Builder()
            .setOptions(mqttOptions)
            .build()
        socketService = communicateClient.newSocketService()
    }

    private fun createWithIotOptions(iotOptions: IotOptions): MqttOptions {
        iotWrapper = IotWrapper(iotOptions)
        val clientId: String = iotWrapper.getClientId()
        val userName: String = iotWrapper.getUserName()
        val password: String = iotWrapper.getPassword()
        val socketFactory: SocketFactory = iotOptions.getSocketFactory()
        val reConnectDelay: Long = iotOptions.getReconnectDelay()
        topic = iotOptions.getTopicFactory().createTopic()
        topic.init(
            iotOptions.getProjectId(),
            iotOptions.getDeviceName(),
            iotOptions.getProductKey()
        )
        val address: String = iotOptions.getAddress()
        val timeToWait: Long = iotOptions.getTimeToWait()
        val port: Int = iotOptions.getPort()
        return Builder()
            .setAddress(address)
            .setPort(port)
            .setTopic(topic.getSubscribeTopic())
            .setClientId(clientId)
            .setUserName(userName)
            .setTimeToWait(timeToWait)
            .setPassword(password)
            .setHttpsHostnameVerificationEnabled(false)
            .setSocketFactory(socketFactory)
            .setReconnectDelay(reConnectDelay)
            .build()
    }

    override fun send(iotRequest: IotRequest) {
        cacheThreadPool.execute {
            synchronized(sendLock) {
                socketService.request(iotRequest)
            }
        }
    }

    fun sendRequest(message: String, messageId: String) {
        send(IotRequest(message.toByteArray(), topic.getRequestTopic(messageId)))
    }

    override fun addCallback(iotCallback: IotCallback) {
        this.iotCallback = iotCallback
        socketService.addCallback(this)
    }

    override fun startup() {
        socketService.start()
    }

    override fun shutdown() {
        socketService.close()
    }

    override fun onFailure(e: Exception) {
        if (iotCallback != null) {
            iotCallback.onFailure(e)
        }
    }


    fun onStatus(status: Int) {
        if (status == CommunicateStatus.CONNECTED) {
            isConnected = true
        } else if (status == CommunicateStatus.DISCONNECTED) {
            isConnected = false
        }
        if (iotCallback != null) {
            iotCallback!!.onStatus(status)
        }
    }

    /**
     * 请求成功
     *
     * @param response
     */
    fun onResponse(response: Response) {
        if (iotCallback != null) {
            val mqttResponse: MqttResponse = response as MqttResponse
            val messageId: String = topic.getMessageIdWithTopic(mqttResponse.getTopic())
            iotCallback!!.onResponse(IotResponse(mqttResponse, messageId))
        }
    }

    companion object {
        @Volatile
        private lateinit var INSTANCE: IotClientImpl
        fun getInstance(iotOptions: IotOptions): IotClientImpl {
            if (INSTANCE == null) {
                synchronized(IotClientImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = IotClientImpl(iotOptions)
                    }
                }
            }
            return INSTANCE
        }
    }

    init {
        initMqttWithOptions(iotOptions)
    }
}