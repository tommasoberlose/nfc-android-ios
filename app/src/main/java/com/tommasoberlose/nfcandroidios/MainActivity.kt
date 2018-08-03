package com.tommasoberlose.nfcandroidios

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tommasoberlose.ndefmessagehelper.NdefMessageHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  var isServiceEnabled = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    updateUI()

    action_toggle.setOnClickListener {
      if (isServiceEnabled) {
        NdefMessageHelper.disabledNdefTagEmulation(this)
        isServiceEnabled = true
      } else {
        NdefMessageHelper.activateNfcReaderMode(this)
        NdefMessageHelper.enableNdefTagEmulation(this, input.text.toString())
        isServiceEnabled = false
      }
      updateUI()
    }
  }

  fun updateUI() {
    action_toggle.text = if (isServiceEnabled) "Disable" else "Enable"
    input.isEnabled = !isServiceEnabled
  }
}
