package com.communication.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.communication.architecture.databinding.ActMainBinding
import com.communication.iot.IotCallback
import com.communication.iot.IotClient
import com.communication.iot.IotClientFactory
import com.communication.iot.TopicFactory
import com.communication.iot.bean.IotOptions
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActMainBinding
    private lateinit var iotClient: IotClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.act_main)
        binding.btnSend.setOnClickListener {
            val iotOptions = IotOptions.Builder()
                .setAddress("192.168.1.103")
                .setPort(8080)
                .setTopicFactory(TopicFactory.DEFAULT)
                .build()
            iotClient = IotClientFactory.instance.getIotClient(iotOptions)
            iotClient.addCallback(object : IotCallback {
                override fun onFailure(e: Exception) {
                }

                override fun onStatus(status: Int) {
                }

                override fun onResponse() {
                }
            })
            iotClient.startup()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        iotClient.shutdown()
    }
}