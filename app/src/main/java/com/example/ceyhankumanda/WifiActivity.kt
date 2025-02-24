// WifiActivity.kt
package com.example.ceyhankumanda
import com.example.ceyhankumanda.Utils
import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ceyhankumanda.databinding.ActivityWifiBinding

class WifiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWifiBinding
    private var webSocketController: WebSocketController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bağlanma butonuna tıklama olayı
        binding.btnConnect.setOnClickListener {
            promptForTvIpAddress()
        }

        // Geri butonuna tıklama olayı
        binding.btnBack.setOnClickListener {
            finish() // WifiActivity'den çık
        }
    }

    // Kullanıcıdan IP adresini isteme fonksiyonu
    private fun promptForTvIpAddress() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("TV IP Adresini Girin")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI
        builder.setView(input)

        builder.setPositiveButton("Tamam") { dialog, which ->
            val tvIpAddress = input.text.toString()
            if (Utils.isValidIp(tvIpAddress)) { // Utils.isValidIp çağrısı
                webSocketController = WebSocketController(this, tvIpAddress)
                webSocketController?.connectToTv()
                Toast.makeText(this, "Bağlantı kuruluyor...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Geçersiz IP adresi.", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("İptal") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketController?.closeConnection()
    }
}
