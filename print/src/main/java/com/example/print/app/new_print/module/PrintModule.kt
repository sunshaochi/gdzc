package com.example.print.app.new_print.module

import android.os.Parcelable
import java.io.Serializable


/**
 * printView module
 */
data class PrintModule(
        var background: String = "",
        var printerType: String = "",
        var height: Long = 0,
        var width: Long = 0,
        var title: String = "",
        var isShowBackground: Boolean = false,
        var type: Int = 0,
        var actionModel: ActionModelBean = ActionModelBean()
) : Serializable {

    data class ActionModelBean(var models: List<BaseItemModule> = arrayListOf()) : Serializable


}
