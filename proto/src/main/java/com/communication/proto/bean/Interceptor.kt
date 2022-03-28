package com.communication.proto.bean

interface Interceptor {
    fun intercept(request: Request): Request

    fun intercept(response: Response): Response
}