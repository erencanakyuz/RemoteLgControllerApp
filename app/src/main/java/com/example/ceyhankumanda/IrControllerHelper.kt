// IrControllerHelper.kt
package com.example.ceyhankumanda

import android.content.Context
import android.hardware.ConsumerIrManager
import android.widget.Toast

class IrControllerHelper(private val context: Context) {

    private var irManager: ConsumerIrManager? = null

    init {
        irManager = context.getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
    }

    /**
     * IR komutunu gönderir.
     * @param command Komut anahtarı (örneğin, "vol_up")
     */
    fun sendIrCommand(command: String) {
        val hexCode = irCodes[command]
        if (hexCode != null) {
            val pattern = buildNecPattern(hexCode)
            transmitIr(pattern)
        } else {
            Toast.makeText(context, "Komut bulunamadı: $command", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Hex kodunu NEC zamanlama desenine dönüştürür.
     * @param hexCode Hexadecimal IR kodu (örneğin, "20DF10EF")
     * @return IR iletim desenini temsil eden IntArray
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

    /**
     * IR iletimini gerçekleştirir.
     * @param pattern IR iletim desenini temsil eden IntArray
     */
    private fun transmitIr(pattern: IntArray) {
        if (irManager?.hasIrEmitter() == true) {
            try {
                irManager?.transmit(LG_IR_FREQUENCY, pattern)
                Toast.makeText(context, "IR kodu gönderildi", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "IR kodu gönderilemedi: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Cihazınız IR blaster desteklemiyor", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val LG_IR_FREQUENCY = 38000 // 38 kHz

        // IR Kodları (Hex Formatında)
        private val irCodes = mapOf(
            "on_off" to "20DF10EF",
            "channel_up" to "20DF00FF",      // EKLEYİN
            "channel_down" to "20DF807F",  // EKLEYİN
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
    }
}
