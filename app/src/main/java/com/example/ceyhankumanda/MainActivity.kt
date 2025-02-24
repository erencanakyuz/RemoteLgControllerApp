// MainActivity.kt
package com.example.ceyhankumanda

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ceyhankumanda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menü butonuna tıklama olayı
        binding.btnMenu.setOnClickListener {
            toggleSideMenu()
        }

        // IR butonuna tıklama olayı
        binding.btnIr.setOnClickListener {
            val intent = Intent(this, IrActivity::class.java)
            startActivity(intent)
        }

        // Wi-Fi butonuna tıklama olayı
        binding.btnWifi.setOnClickListener {
            val intent = Intent(this, WifiActivity::class.java)
            startActivity(intent)
        }
    }

    // Yan menüyü açıp kapama fonksiyonu
    private fun toggleSideMenu() {
        if (binding.sideMenu.visibility == View.GONE) {
            binding.sideMenu.visibility = View.VISIBLE
        } else {
            binding.sideMenu.visibility = View.GONE
        }
    }
}
