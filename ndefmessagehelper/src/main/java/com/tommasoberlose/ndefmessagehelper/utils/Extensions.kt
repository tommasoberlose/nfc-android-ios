package com.tommasoberlose.ndefmessagehelper.utils



fun ByteArray.toHex() : String{
  val result = StringBuffer()

  forEach {
    val octet = it.toInt()
    val firstIndex = (octet and 0xF0).ushr(4)
    val secondIndex = octet and 0x0F
    result.append(Utils.HEX_CHARS[firstIndex])
    result.append(Utils.HEX_CHARS[secondIndex])
  }

  return result.toString()
}



fun String.hexStringToByteArray() : ByteArray {

  val result = ByteArray(length / 2)

  for (i in 0 until length step 2) {
    val firstIndex = Utils.HEX_CHARS.indexOf(this[i]);
    val secondIndex = Utils.HEX_CHARS.indexOf(this[i + 1]);

    val octet = firstIndex.shl(4).or(secondIndex)
    result.set(i.shr(1), octet.toByte())
  }

  return result
}