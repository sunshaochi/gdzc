@file: JvmName("TranslateUtils")
package com.gengcon.android.fixedassets.util

import java.util.*


private var servicePxList: ArrayList<Float> = arrayListOf(18f, 20f, 24f, 28f, 32f, 36f, 40f, 44f, 48f, 58f, 64f, 70f, 84f, 98f, 126f, 146f,158f,170f,194f,248f)
    private var localPxList: ArrayList<Float> = arrayListOf(18f, 20f, 24f, 28f, 32f, 36f, 40f, 44f, 48f, 58f, 64f, 70f, 84f, 98f, 126f, 146f,158f,170f,194f,248f)
    private var with = 0F
     var mTemplateWidth = 0F
     var mTemplateHeight = 0F
    /**
     * 服务器与本地数据比率
     */
    private var rate = 0F


    fun initTemplate(templateWidth: Float,templateHeight:Float){
        mTemplateWidth = templateWidth
        mTemplateHeight=templateHeight
    }


    fun initRate(width: Float) {
        rate = width / mTemplateWidth / 8F
        localPxList.clear()
        servicePxList.forEach {
            localPxList.add(it * rate)
        }
    }

    /**
     * 获取服务器文字大小
     */
    fun getServiceTextSize(position: Int): Float {
        return if (position < servicePxList.size && position >= 0) {
            servicePxList[position]
        } else
            -1f
    }

    /**
     * 获取本地文字大小
     */
    fun getLocationTextSize(position: Int): Float {
        return if (position < localPxList.size && position >= 0) {
            localPxList[position]
        } else {
            -1f
        }
    }

    /**
     * 获取当前文字的索引
     */
    fun getPositionFromLocal(textSize: Float): Int {
        return localPxList.let {
            var position=2
            for (i in 0 until it.size) {
                if (textSize == it[i]) {
                    position = i
                    break
                }
            }
            position
        }

    }

    /**
     * 获取当前文字的索引
     */
    fun getPositionFromService(textSize: Float): Int {
        return servicePxList.let {
            var position = 2
            for (i in 0 until it.size) {
                if (textSize == it[i]) {
                    position = i
                    break
                }
            }
            position
        }

    }

    fun getLocalRate() = rate * 8;

    /**
     *获取本地px
     */
    fun getPxByServicePx(servicePx: Float) = servicePx * rate

    /**
     *获取服务器px
     */
    fun getPxByLocalPx(localPx: Float) = localPx / rate


    /**
     *获取本地mm
     */
    fun getMmByServiceMm(serviceMm: Float) = serviceMm * rate

    /**
     *获取服务器mm
     */
    fun getMmByLocalMm(localMm: Float) = localMm / rate

    /**
     * 本地像素转毫米
     */
    fun getLocalMmByLocalPx(localPx: Float) = localPx / rate / 8

    /**
     * 本地毫米转像素
     */
    fun getLocalPxByLocalMm(localMm: Float) = localMm * rate * 8

    /**
     * 服务器像素转毫米
     */
    fun getServiceMmByServicePx(servicePx: Float) = servicePx / 8

    /**
     * 服务器毫米转像素
     */
    fun getServicePxByServiceMm(serviceMm: Float) = serviceMm * 8