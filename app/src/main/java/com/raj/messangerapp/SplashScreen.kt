package com.raj.messangerapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import com.raj.messangerapp.registration.LoginActivity
import java.net.NetworkInterface
import kotlin.system.exitProcess

class SplashScreen : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar!!.hide()
        if (!isConnected()){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Internet Connection")
            builder.setMessage("Do you want to exist?")
            builder.setPositiveButton("Yes"){dialoginterface,which ->
                finish()
                exitProcess(0)
            }
            builder.setNegativeButton("Check wifi"){ dialogInterface,which ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                finish()
                exitProcess(0)

            }

            val dialog = builder.create()
            dialog.show()

        } else {
            handler = Handler()
            handler.postDelayed({

                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            },3000)
        }

    }
    fun isConnected():Boolean{
        var connectivityManager:ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var network:NetworkInfo? = connectivityManager.activeNetworkInfo
        if (network != null){
            if (network.isConnected){
                return true
            }
        }
        return false
    }
}