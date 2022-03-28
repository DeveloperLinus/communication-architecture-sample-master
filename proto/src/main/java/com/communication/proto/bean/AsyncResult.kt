package com.communication.proto.bean

import java.lang.Exception

class AsyncResult<T : Response> {
    var response: Response? = null
    var exception: Exception? = null
}