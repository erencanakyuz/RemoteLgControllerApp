package com.example.ceyhankumanda

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var irManager: ConsumerIrManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!irManager.hasIrEmitter()) {
            Toast.makeText(this, "Cihazınız IR Blaster desteklemiyor", Toast.LENGTH_LONG).show()
            return
        }

        // IR Blaster'ı başlatın
        irManager = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        setContentView(R.layout.activity_main)

        // Butonları bulma ve atama
        val btnVolumeUp = findViewById<Button>(R.id.btnVolumeUp)
        val btnVolumeDown = findViewById<Button>(R.id.btnVolumeDown)
        val btnChannelUp = findViewById<Button>(R.id.btnChannelUp)
        val btnChannelDown = findViewById<Button>(R.id.btnChannelDown)
        val btnBurcak = findViewById<Button>(R.id.btnPower)

        // Diğer butonlar (Opsiyonel) için ekleyebilirsiniz

        // Buton tıklama olaylarını ayarlama
        btnVolumeUp.setOnClickListener {
            sendIrCommand("vol_up")
        }

        btnVolumeDown.setOnClickListener {
            sendIrCommand("vol_down")
        }

        btnChannelUp.setOnClickListener {
            sendIrCommand("channel_up")
        }

        btnChannelDown.setOnClickListener {
            sendIrCommand("channel_down")
        }

        btnBurcak.setOnClickListener {
            // Burcak butonuna özel bir işlem ekleyebilirsiniz
            Toast.makeText(this, "Burcak cok tatlı!", Toast.LENGTH_SHORT).show()
            // Örneğin, bir IR kodu gönder
            // sendIrCommand("burcak")
        }
    }

    private fun sendIrCommand(commandKey: String) {
        val hexCode = irCodes[commandKey]
        if (hexCode != null) {
            val pattern = buildNecPattern(hexCode)
            transmitIr(pattern)
        } else {
            Toast.makeText(this, "Komut bulunamadı: $commandKey", Toast.LENGTH_SHORT).show()
        }
    }

    private fun transmitIr(pattern: IntArray) {
        if (irManager.hasIrEmitter()) {
            try {
                irManager.transmit(LG_IR_FREQUENCY, pattern)
                Toast.makeText(this, "IR kodu gönderildi", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "IR kodu gönderilemedi: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Cihazınız IR blaster desteklemiyor", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val LG_IR_FREQUENCY = 38000 // 38 kHz

        // IR Kodları (Hex Formatında)
        private val irCodes = mapOf(
            "on_off" to "20DF10EF",
            "tv_rad" to "20DF0FF0",
            "input" to "20DFD02F",
            "settings" to "20DFC23D",
            "live_zoom" to "20DFF50A",
            "subtitle" to "20DF9C63",
            "1" to "20DF8877",
            "2" to "20DF48B7",
            "3" to "20DFC837",
            "4" to "20DF28D7",
            "5" to "20DFA857",
            "6" to "20DF6897",
            "7" to "20DFE817",
            "8" to "20DF18E7",
            "9" to "20DF9867",
            "0" to "20DF08F7",
            "list" to "20DFCA35",
            "guide" to "20DFD52A",
            "vol_up" to "20DF40BF",
            "vol_down" to "20DFC03F",
            "info" to "20DF55AA",
            "search" to "20DF1EE1",
            "mute" to "20DF906F",
            "prog_page_up" to "20DF00FF",
            "prog_page_down" to "20DF807F",
            "recent" to "20DFAD52",
            "home" to "20DF3EC1",
            "live_menu" to "20DF7986",
            "back" to "20DF14EB",
            "exit" to "20DFDA25",
            "arrow_up" to "20DF02FD",
            "arrow_down" to "20DF827D",
            "arrow_right" to "20DF609F",
            "arrow_left" to "20DFE01F",
            "ok_button" to "20DF22DD",
            "teletext" to "20DF04FB",
            "teletext_options" to "20DF847B",
            "ad" to "20DF8976",
            "rec" to "20DFBD42",
            "stop" to "20DF8D72",
            "play" to "20DF0DF2",
            "pause" to "20DF5DA2",
            "rew" to "20DFF10E",
            "forward" to "20DF718E",
            "red_button" to "20DF4EB1",
            "green_button" to "20DF8E71",
            "yellow_button" to "20DFC639",
            "blue_button" to "20DF8679"
        )

        // Opsiyonel Butonlar (Yorum Satırı Olarak)
        private val optionalIrCodes = mapOf(
            "power_off" to "20DFA35C",
            "power_on" to "20DF23DC",
            "energy" to "20DFA956",
            "quick_view" to "20DF58A7",
            "fav" to "20DF7887",
            "ez_ratio" to "20DF9E61",
            "ez_picture" to "20DFB24D",
            "ez_sleep" to "20DF708F",
            "3D" to "20DF3BC4",
            "ez_adjust" to "20DFFF00",
            "in_start" to "20DFDF20",
            "input_antenna" to "20DF6B94",
            "input_component_1" to "20DFFD02",
            "input_av" to "20DF5AA5",
            "input_hdmi1" to "20DF738C",
            "input_hdmi2" to "20DF33CC",
            "input_hdmi3" to "20DF9768",
            "record_list" to "20DF09F6",
            "audio" to "20DF50AF",
            "simplink_toggle" to "20DF7E81",
            "tv_guide" to "20DF956A",
            "user_guide" to "20DF5EA1",
            "tv_radio_toggle" to "20DFF00F"
            // Diğer opsiyonel butonlar eklenebilir
        )
    }

    /**
     * Hex kodunu NEC zamanlama desenine dönüştürür.
     */
    private fun buildNecPattern(hexCode: String): IntArray {
        val binaryString = hexCode.toLong(16).toString(2).padStart(32, '0')
        val patternList = mutableListOf<Int>()

        // Lider Kod
        patternList.add(9000) // 9ms ON
        patternList.add(4500) // 4.5ms OFF

        // Veri Bitleri
        for (bit in binaryString) {
            patternList.add(560) // 0.56ms ON
            if (bit == '0') {
                patternList.add(560) // 0.56ms OFF for '0'
            } else {
                patternList.add(1690) // 1.69ms OFF for '1'
            }
        }

        // Sonlandırıcı Kod
        patternList.add(560) // 0.56ms ON

        return patternList.toIntArray()
    }
}
