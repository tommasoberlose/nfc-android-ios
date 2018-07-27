package com.tommasoberlose.nfcandroidios

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.app.PendingIntent
import android.nfc.tech.NfcF
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException







class MainActivity : AppCompatActivity() {
  lateinit var adapterNFC: NfcAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Log.i("NFC", "STARTINGGGGGGGGGGG")

    adapterNFC = NfcAdapter.getDefaultAdapter(this)
  }

  override fun onResume() {
    super.onResume()
    val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
    try {
      ndef.addDataType("*/*")    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
    } catch (e: MalformedMimeTypeException) {
      e.printStackTrace()
    }

    val intentFiltersArray = arrayOf(ndef)
    val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))

    var pendingIntent = PendingIntent.getActivity(this, 0, Intent(this@MainActivity, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    adapterNFC.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    adapterNFC.enableReaderMode(this, null, NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS, null)
    adapterNFC.setNdefPushMessage(null, this, this)
    startService(Intent(this, MyHostApduService::class.java))
  }

  override fun onPause() {
    adapterNFC.disableForegroundDispatch(this)
    stopService(Intent(this, MyHostApduService::class.java))

    super.onPause()
  }

  public override fun onNewIntent(intent: Intent) {
    Log.i("NFC", "New Intent?")
  }
}
