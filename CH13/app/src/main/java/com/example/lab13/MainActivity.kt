package com.example.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val tv_clock: TextView? = null
    private val btn_start: Button? = null
    private var flag = false
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val b = intent.extras
            tv_clock!!.text = String.format(
                "%02d:%02d:%02d",
                b!!.getInt("H"), b.getInt("M"), b.getInt("S")
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv_clock = findViewById<TextView>(R.id.tv_clock)
        val btn_start = findViewById<Button>(R.id.btn_start)
        registerReceiver(receiver, IntentFilter("MyMessage"))
        flag = MyService.flag
        if (flag) btn_start.setText("暫停") else btn_start.setText("開始")
        btn_start.setOnClickListener(View.OnClickListener {
            flag = !flag
            if (flag) {
                btn_start.setText("暫停")
                Toast.makeText(
                    this@MainActivity, "計時開始",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                btn_start.setText("開始")
                Toast.makeText(
                    this@MainActivity, "計時暫停",
                    Toast.LENGTH_SHORT
                ).show()
            }
            startService(
                Intent(
                    this@MainActivity,
                    MyService::class.java
                ).putExtra("flag", flag)
            )
        })
    }
    public override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}

