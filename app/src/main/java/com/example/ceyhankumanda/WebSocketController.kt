// WebSocketController.kt
package com.example.ceyhankumanda

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.*

class WebSocketController(private val context: Context, private val tvIpAddress: String) {

    private val client: OkHttpClient = OkHttpClient()
    private var webSocket: WebSocket? = null
    private val TAG = "WebSocketController"

    fun connectToTv() {
        val request = Request.Builder().url("ws://$tvIpAddress:3000").build()
        try {
            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    Log.d(TAG, "TV'ye bağlantı kuruldu")
                    showToast("TV'ye bağlantı kuruldu")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    Log.d(TAG, "TV'den gelen mesaj: $text")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    Log.e(TAG, "Bağlantı hatası: ${t.message}")
                    showToast("Bağlantı hatası: ${t.message}")
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "Bağlantı kapatıldı: $reason")
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            showToast("Bağlantı kurulamadı: ${e.message}")
        }
    }

    fun sendCommand(command: String) {
        if (webSocket != null) {
            val jsonCommand = """{"type": "request", "uri": "$command"}"""
            webSocket?.send(jsonCommand)
        } else {
            Log.e(TAG, "WebSocket başlatılmadı.")
            showToast("TV'ye bağlı değilsiniz.")
        }
    }

    fun closeConnection() {
        webSocket?.close(1000, "Kapalı")
        client.dispatcher.executorService.shutdown()
        client.connectionPool.evictAll()
    }

    private fun showToast(message: String) {
        if (context is Activity) {
            context.runOnUiThread {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
