package com.gengcon.android.fixedassets.util;


import com.gengcon.android.fixedassets.module.base.GApplication;
import com.pow.api.cls.RfidPower;
import com.uhf.api.cls.Reader;

/*
 * 文件名：RFIDUtils
 * 描  述：RFID工具,平台类型初始化,读写器连接,断开连接,读取数据，停止读取
 * 作  者：张彬
 * 时  间：2018/04/13
 * 版  权：武汉精臣智慧标识科技有限公司
 */
public class RFIDUtils {

    private static final String TAG = "RFIDUtils";
    private static RfidPower mRfidPower;//平台类型e
    private static String mPath;//串口路径
    private static Reader mReader;//读写器

    public static final String CONNECT_FAILED = "重连失败";
    public static final String INVENTORY_FAILED = "盘点失败";
    public static final String FAILED_TO_READ_INFORMATION = "读取信息失败";

    /**
     * 设置是否需要重连
     *
     * @param mNeedConnect 是否需要重连
     */
    public static void setNeedConnect(boolean mNeedConnect) {
        RFIDUtils.mNeedConnect = mNeedConnect;
    }

    private static boolean mNeedConnect = true;//是否需要重连,默认需要
    private static int[] mTagcnt = new int[1];//输出参数，标签读取到的标签个数

    /**
     * 初始化平台类型
     */
    private static void initPlatform() {
        if (mRfidPower == null) {
            mRfidPower = new RfidPower(RfidPower.PDATYPE.IDATA);
        }
    }

    /**
     * 初始化读写器
     */
    private static void initReader() {
        if (mReader == null) {
            mReader = new Reader();
        }
    }

    /**
     * 连接读写器
     *
     * @return 是否连接成功
     */
    public static boolean connect(GApplication application) {
        initPlatform();//初始化平台
        initReader();//初始化读写器
        setAntPower((short) 25, (short) 25);
        mPath = mRfidPower.GetDevPath();//获取串口路径
        mPath = "/dev/ttyMT2";
        mRfidPower.PowerUp();//读写器上电
        application.PowerUp();
        Reader.READER_ERR err = mReader.InitReader_Notype(mPath, 1);//连接读写器
        //判断读写器是否连接成功
        if (err == Reader.READER_ERR.MT_OK_ERR) {
            mNeedConnect = false;//连接成功，设置为不需要重连
            return true;
        } else {
            return false;
        }
    }

    /**
     * 断开与读写器的连接,调用改方法需要读写器已连接
     *
     * @return 是否断开成功
     */
    public static boolean disconnect(GApplication application) {
        if (mReader == null) {
            return true;
        }
        mReader.CloseReader();//断开连接
        mNeedConnect = true;
        application.PowerDown();
        return mRfidPower.PowerDown();//读写器断电
    }

    public static boolean isConnect() {
        initPlatform();//初始化平台
        initReader();//初始化读写器
        mPath = mRfidPower.GetDevPath();//获取串口路径
        mPath = "/dev/ttyMT2";
        Reader.READER_ERR err = mReader.InitReader_Notype(mPath, 1);//连接读写器
        //判断读写器是否连接成功
        if (err == Reader.READER_ERR.MT_OK_ERR) {
            mNeedConnect = false;//连接成功，设置为不需要重连
            return true;
        } else {
            return false;
        }
    }

    public static String getDevPath() {
        initPlatform();
        return mRfidPower.GetDevPath();
    }

    /**
     * 开启盘点
     *
     * @return 返回读取信息或者错误信息
     */
    public static String startInventory(GApplication application) {
        //盘点前判断是否需要重连
        if (mNeedConnect) {
            if (connect(application)) {//重连成功
                Reader.READER_ERR err = mReader.TagInventory_Raw(new int[]{1}, mTagcnt.length, (short) 50, mTagcnt);//开始读取信息,调试设备天线默认为1,读取超时50MS
                if (err == Reader.READER_ERR.MT_OK_ERR) {//盘点成功
                    Reader.TAGINFO taginfo = mReader.new TAGINFO();//读取标签信息
                    if (mReader.GetNextTag(taginfo) == Reader.READER_ERR.MT_OK_ERR) {//读取成功
                        return Reader.bytes_Hexstr(taginfo.EpcId);//解析读取信息
                    } else {
                        return FAILED_TO_READ_INFORMATION;//读取失败
                    }
                } else {
                    return INVENTORY_FAILED;//盘点失败
                }
            } else {//重连失败
                return CONNECT_FAILED;
            }
        } else {
            Reader.READER_ERR err = mReader.TagInventory_Raw(new int[]{1}, mTagcnt.length, (short) 50, mTagcnt);//开始读取信息
            if (err == Reader.READER_ERR.MT_HARDWARE_ALERT_ERR_BY_TOO_MANY_RESET) {//读写器复位次数超上限
                mNeedConnect = true;//需要重连
                connect(application);//进行重连
            }

            if (err == Reader.READER_ERR.MT_OK_ERR) {//盘点成功
                Reader.TAGINFO taginfo = mReader.new TAGINFO();//读取标签信息
                if (mReader.GetNextTag(taginfo) == Reader.READER_ERR.MT_OK_ERR) {//读取成功
                    return Reader.bytes_Hexstr(taginfo.EpcId);//解析读取信息
                } else {
                    return FAILED_TO_READ_INFORMATION;//读取失败
                }
            } else {
                return INVENTORY_FAILED;//盘点失败
            }
        }

    }

    public static boolean setAntPower(short rp, short wp) {
        Reader.AntPowerConf apcf = mReader.new AntPowerConf();
        Reader.AntPower jaap = mReader.new AntPower();
        jaap.antid = 0;
        jaap.readPower = (short) (500 + 100 * rp);

        jaap.writePower = (short) (500 + 100 * wp);
        apcf.Powers[0] = jaap;
        Reader.READER_ERR er = mReader.ParamSet(
                Reader.Mtr_Param.MTR_PARAM_RF_ANTPOWER, apcf);
        if (er == Reader.READER_ERR.MT_OK_ERR) {
            return true;
        }
        return false;

    }

    public static int[] getAntPower() {
        int[] powers = new int[2];
        Reader.AntPowerConf apcf = mReader.new AntPowerConf();
        Reader.READER_ERR er = mReader.ParamGet(Reader.Mtr_Param.MTR_PARAM_RF_ANTPOWER, apcf);
        if (er == Reader.READER_ERR.MT_OK_ERR) {
            powers[0] = (apcf.Powers[0].readPower - 500) / 100;
            powers[1] = (apcf.Powers[0].writePower - 500) / 100;
        }
        return powers;
    }

}
