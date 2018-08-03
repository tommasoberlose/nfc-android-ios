package com.tommasoberlose.ndefmessagehelper.utils

import android.nfc.NdefRecord
import java.io.UnsupportedEncodingException

object Utils {
  val HEX_CHARS = "0123456789ABCDEF".toCharArray()

  fun createTextRecord(language: String, text: String, id: ByteArray): NdefRecord {
    val languageBytes: ByteArray
    val textBytes: ByteArray
    try {
      languageBytes = language.toByteArray(charset("US-ASCII"))
      textBytes = text.toByteArray(charset("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
      throw AssertionError(e)
    }

    val recordPayload = ByteArray(1 + (languageBytes.size and 0x03F) + textBytes.size)

    recordPayload[0] = (languageBytes.size and 0x03F).toByte()
    System.arraycopy(languageBytes, 0, recordPayload, 1, languageBytes.size and 0x03F)
    System.arraycopy(textBytes, 0, recordPayload, 1 + (languageBytes.size and 0x03F), textBytes.size)

    return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, id, recordPayload)
  }

  fun fillByteArrayToFixedDimension(array: ByteArray, fixedSize: Int): ByteArray {
    if (array.size == fixedSize) {
      return array
    }

    val start = byteArrayOf(0x00.toByte())
    val filledArray = ByteArray(start.size + array.size)
    System.arraycopy(start, 0, filledArray, 0, start.size)
    System.arraycopy(array, 0, filledArray, start.size, array.size)
    return fillByteArrayToFixedDimension(filledArray, fixedSize)
  }
}