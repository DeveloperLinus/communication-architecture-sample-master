package com.communication.proto.bean

import java.util.*

class CommunicateClient private constructor(builder: Builder) {
    val dispatcher: Dispatcher

    var options: Options? = null
        private set
    private var interceptors: List<Interceptor>? = null

    private fun initBuilder(builder: Builder) {
        options = builder.options
        interceptors = builder.interceptors
    }

    fun newSocketService(): SocketService {
        return RealSocketService.newRealSocketService(this)
    }

    fun getInterceptors(): List<Interceptor>? {
        return interceptors
    }

    class Builder {
        var options: Options? = null
        val interceptors: MutableList<Interceptor>

        fun setOptions(options: Options?): Builder {
            this.options = options
            if (options == null) {
                throw RuntimeException("options is not to be null!")
            }
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        fun build(): CommunicateClient {
            return CommunicateClient(this)
        }

        init {
            interceptors = ArrayList<Interceptor>(2)
        }
    }

    init {
        dispatcher = Dispatcher(this)
        initBuilder(builder)
    }
}