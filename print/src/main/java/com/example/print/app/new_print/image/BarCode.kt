package com.example.print.app.new_print.image

import com.google.zxing.BarcodeFormat

 enum class BarCode(val code: Int, val realName: String, val barCode: BarcodeFormat) {
    CodeBar(0, "CodeBar", BarcodeFormat.CODABAR),
    Code39(1, "Code39", BarcodeFormat.CODE_39),
    Code93(2, "Code93", BarcodeFormat.CODE_93),
    Code128(3, "通用条码_Code128", BarcodeFormat.CODE_128),
    EAN_8(4, "EAN_8", BarcodeFormat.EAN_8),
    En13(5, "商品条码_EAN_13", BarcodeFormat.EAN_13),
    ITF(6, "ITF", BarcodeFormat.ITF),
    UPC_A(7, "UPC_A", BarcodeFormat.UPC_A),
    UPC_E(7, "UPC_E", BarcodeFormat.UPC_E);

    companion object {
        public fun getBarCodeByName(realName: String): BarCode? {
            values().filter { it.realName == realName }
                    .forEach {
                        return it
                    }
            return Code128
        }

        public fun getBarCodeByCode(code: Int): BarCode? {
            values().filter { it.code == code }
                    .forEach {
                        return it
                    }
            return Code128
        }
    }

}