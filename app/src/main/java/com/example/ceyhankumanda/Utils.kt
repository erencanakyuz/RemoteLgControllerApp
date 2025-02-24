// Utils.kt
package com.example.ceyhankumanda

object Utils {
    /**
     * IP adresinin geçerli olup olmadığını kontrol eder.
     * @param ip IP adresi
     * @return Geçerliyse true, aksi halde false
     */
    fun isValidIp(ip: String): Boolean {
        return android.util.Patterns.IP_ADDRESS.matcher(ip).matches()
    }
}
