package com.example.broadcastreceiver_2

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

// logic xử lý: khi click vào button, activity sẽ send đi 1 broadcast và object gửi đi là intent
// sau đó xây dựng 1 broadcast receiver để nhận sự kiện này và đăng kí đối tượng nhận qua hàm registerReceiver trong onStart
                                                            // và hủy đăng kí qua hàm unregisterReceiver trong onStop
// trong hàm onReceive : tvReceived sẽ nhận được giá trị là giá trị của My_text
class MainActivity : AppCompatActivity() {
    private var MY_ACTION : String = "com.broadcast.ACTION"
    private var MY_TEXT : String = "com.broadcast.TEXT"// key để truyền dữ liệu đi

    private var btnSendBroadcast : Button? = null
    private var tvReceived : TextView? = null

    private var mBroadcastReceiver : BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (MY_ACTION.equals(p1?.action)){
                val text = p1?.getStringExtra(MY_TEXT)
                tvReceived?.setText(text)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSendBroadcast = findViewById(R.id.btn_send_broadcast)
        tvReceived = findViewById(R.id.tv_received)

        btnSendBroadcast?.setOnClickListener{
            clickSendBroadCast()
        }
    }
    private fun clickSendBroadCast(){
        var intent : Intent = Intent(MY_ACTION)
        intent.putExtra(MY_TEXT, "This is broadcast demo send data")
        sendBroadcast(intent)
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onStart() {
        super.onStart()
        val intentFilter : IntentFilter = IntentFilter(MY_ACTION)
        // đăng kí 1 broadcast receiver
        registerReceiver(mBroadcastReceiver, intentFilter)//
        // sau khi đăng kí xong thì phải xử lý khi nào hủy broadcast receiver này đi
        // => hủy trong hàm onS top()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mBroadcastReceiver)
    }


    //nhận sự kiện này thông qua broadcast receiver và hiển thị lên tvReceived : TextView
}

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        var action : Int = intent.getIntExtra("com.broadcast.ACTION", 0)

    }
}