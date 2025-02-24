// IrActivity.kt
package com.example.ceyhankumanda

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ceyhankumanda.databinding.ActivityIrBinding

class IrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIrBinding
    private lateinit var irController: IrControllerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IR Controller'ı başlat
        irController = IrControllerHelper(this)

        // Butonları bulma ve atama
        binding.btnVolumeUp.setOnClickListener {
            irController.sendIrCommand("vol_up")
            Toast.makeText(this, "Ses Arttırıldı", Toast.LENGTH_SHORT).show()
        }

        binding.btnVolumeDown.setOnClickListener {
            irController.sendIrCommand("vol_down")
            Toast.makeText(this, "Ses Azaltıldı", Toast.LENGTH_SHORT).show()
        }

        binding.btnChannelUp.setOnClickListener {
            irController.sendIrCommand("channel_up")
            Toast.makeText(this, "Kanal Arttırıldı", Toast.LENGTH_SHORT).show()
        }

        binding.btnChannelDown.setOnClickListener {
            irController.sendIrCommand("channel_down")
            Toast.makeText(this, "Kanal Azaltıldı", Toast.LENGTH_SHORT).show()
        }

        binding.btnPower.setOnClickListener {
            irController.sendIrCommand("on_off")
            Toast.makeText(this, "Burcak Çok Tatlı!", Toast.LENGTH_SHORT).show()
            // Örneğin, bir IR kodu gönder
            // irController.sendIrCommand("burcak")
        }
    }
}
