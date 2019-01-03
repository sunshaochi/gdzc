package com.gengcon.android.fixedassets.util;

import android.text.TextUtils;
import android.util.Log;

import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;
import com.gengcon.android.fixedassets.bean.result.Print;

public class JCPrinter {

    private static final String TAG = "JCPrinter";
    private static final String RESULT__PRINTER_SUCCESS = "SUCCESS";
    private static final String RESULT_PRINTER_FAILED = "PRINTER_FAILED";
    public static LPAPI mDzAPI;

    public static boolean isSupported(String printerName) {
        if (TextUtils.isEmpty(printerName)) {
            return false;
        }
//        return Pattern.compile("^B\\d{2}[A-Za-z]?-\\d{8,12}$").matcher(printerName).matches();
        return true;
    }

    public static boolean openPrinter(String printerName) {
        init();
        return mDzAPI.openPrinterSync(printerName);

    }

    public static void init() {
        if (mDzAPI == null) {
            mDzAPI = LPAPI.Factory.createInstance(new LPAPI.Callback() {
                @Override
                public void onProgressInfo(IDzPrinter.ProgressInfo progressInfo, Object o) {
                    Log.e(TAG, "onProgressInfo: " + progressInfo);
                }

                @Override
                public void onStateChange(IDzPrinter.PrinterAddress printerAddress, IDzPrinter.PrinterState printerState) {
                    Log.e(TAG, "onStateChange: " + printerState);
                }

                @Override
                public void onPrintProgress(IDzPrinter.PrinterAddress printerAddress, Object o, IDzPrinter.PrintProgress printProgress, Object o1) {
                    Log.e(TAG, "onPrintProgress: " + printProgress);
                }

                @Override
                public void onPrinterDiscovery(IDzPrinter.PrinterAddress printerAddress, IDzPrinter.PrinterInfo printerInfo) {
                    Log.e(TAG, "onPrinterDiscovery: " + printerInfo);
                }
            });
        }

    }

    public static boolean printLabel(Print.LableBean label, Print.ListBean printList) {
        boolean flag = false;
        if (label.getSize_id() == 1) {
//            PrinterSubString.substring5030(printList.getTemplate_attr());
            if (label.getQrcode_position() == 1){
                flag = printerLabel50301(printList);
            }else if (label.getQrcode_position() == 2){
                flag = printerLabel50302(printList);
            }
        } else if (label.getSize_id() == 2) {
//            PrinterSubString.substring7050(printList.getTemplate_attr());
            if (label.getQrcode_position() == 1) {
                flag = printerLabel70501(printList);
            } else if (label.getQrcode_position() == 2) {
                flag = printerLabel70502(printList);
            } else if (label.getQrcode_position() == 3) {
                flag = printerLabel70503(printList);
            } else if (label.getQrcode_position() == 4) {
                flag = printerLabel70504(printList);
            } else if (label.getQrcode_position() == 5) {
                flag = printerLabel70505(printList);
            }
        }
        if (!flag) {
            return false;
        }
        return true;
    }

    /**
     * @return
     */
    private static boolean printerLabel50301(Print.ListBean list) {
        mDzAPI.startJob(50, 30, 0);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 1, 4, 45, 6, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 1, 10, 45, 6, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(2), 1, 16, 32, 6, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(3), 1, 23, 32, 6, 3.125);
        mDzAPI.draw2DQRCode(list.getId(), 33.7, 15.8, 11);
        mDzAPI.drawRectangle(0, 2, 45.5, 26, 0.2);
        mDzAPI.drawLine(0, 8.5, 45.3, 8.5, 0.2);
        mDzAPI.drawLine(0, 15, 45.3, 15, 0.2);
        mDzAPI.drawLine(0, 21.5, 33, 21.5, 0.2);
        mDzAPI.drawLine(33, 15, 33, 27.8, 0.2);
        return mDzAPI.commitJob();
    }

    private static boolean printerLabel50302(Print.ListBean list) {
        mDzAPI.startJob(50, 30, 0);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 1, 14, 33.5, 6, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 1, 22, 33.5, 6, 3.125);
        mDzAPI.draw2DQRCode(list.getId(), 35, 15.3, 10);
        mDzAPI.drawRectangle(0, 12.5, 46, 16, 0.2);
        mDzAPI.drawLine(0, 20.3, 34, 20.3, 0.2);
        mDzAPI.drawLine(34, 12.5, 34, 28.5, 0.2);
        return mDzAPI.commitJob();
    }

    private static boolean printerLabel70501(Print.ListBean list) {
        mDzAPI.startJob(50, 70, 0);
        mDzAPI.drawRectangle(1, 2, 46, 66, 0.2);
        mDzAPI.drawLine(1, 11.42, 46.5, 11.42, 0.2);
        mDzAPI.drawLine(1, 20.84, 46.5, 20.84, 0.2);
//        mDzAPI.drawLine(1, 30.26, 46.5, 30.26, 0.2);
        mDzAPI.drawLine(1, 30.20, 46.5, 30.20, 0.2);
        mDzAPI.drawLine(1, 39.68, 46.5, 39.68, 0.2);
        mDzAPI.drawLine(1, 49.1, 46.5, 49.1, 0.2);
        mDzAPI.drawLine(1, 58.52, 27.16, 58.52, 0.2);
        mDzAPI.drawLine(27.1, 49.1, 27.16, 68, 0.2);
        mDzAPI.draw2DQRCode(list.getId(), 28.5, 50.5, 16);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 1.5, 5, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 1.5, 14.42, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(2), 1.5, 23., 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(3), 1.5, 33.26, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(4), 1.5, 42.68, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(5), 1.5, 52.1, 27.5, 10, 2.725);
        mDzAPI.drawText(list.getTemplate_attr().get(6), 1.5, 61.52, 27.5, 10, 3.125);
        return mDzAPI.commitJob();
    }

    private static boolean printerLabel70502(Print.ListBean list) {
        mDzAPI.startJob(50, 70, 0);
        mDzAPI.drawRectangle(0.2, 2, 46, 66, 0.2);
        mDzAPI.drawLine(0.2, 21.8, 46, 21.8, 0.2);
        mDzAPI.drawLine(0.2, 28.4, 46, 28.4, 0.2);
        mDzAPI.drawLine(0.2, 35, 46, 35, 0.2);
        mDzAPI.drawLine(0.2, 41.6, 46, 41.6, 0.2);
        mDzAPI.drawLine(0.2, 48.2, 46, 48.2, 0.2);
        mDzAPI.drawLine(0.2, 54.8, 46, 54.8, 0.2);
        mDzAPI.drawLine(0.2, 61.4, 46, 61.4, 0.2);
        mDzAPI.draw2DQRCode(list.getId(), 15, 4, 16);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 1.5, 24.1, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 1.5, 30.7, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(2), 1.5, 37.3, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(3), 1.5, 43.9, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(4), 1.5, 50.5, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(5), 1.5, 57.1, 45, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(6), 1.5, 63.2, 45, 10, 3.125);
        return mDzAPI.commitJob();
    }

    private static boolean printerLabel70503(Print.ListBean list) {
        mDzAPI.startJob(70, 50, 1);
        mDzAPI.drawRectangle(2, 4, 66, 46, 0.2);
        mDzAPI.drawLine(2, 13.2, 49, 13.2, 0.2);
        mDzAPI.drawLine(49, 4, 49, 22.4, 0.2);
        mDzAPI.drawLine(2, 22.4, 68, 22.4, 0.2);
        mDzAPI.drawLine(2, 31.6, 68, 31.6, 0.2);
        mDzAPI.drawLine(2, 40.8, 68, 40.8, 0.2);
        mDzAPI.drawLine(2, 40.8, 68, 40.8, 0.2);
        mDzAPI.draw2DQRCode(list.getId(), 51, 5, 16);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 2.5, 7, 49, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 2.5, 16, 49, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(2), 2.5, 25, 65, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(3), 2.5, 35, 65, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(4), 2.5, 44, 65, 10, 3.125);

        return mDzAPI.commitJob();
    }

    private static boolean printerLabel70504(Print.ListBean list) {
        mDzAPI.startJob(70, 50, 1);
        mDzAPI.drawRectangle(2, 4, 66, 46, 0.2);
        mDzAPI.drawLine(2, 13.2, 68, 13.2, 0.2);
        mDzAPI.drawLine(49, 31.5, 49, 51, 0.2);
        mDzAPI.drawLine(2, 22.4, 68, 22.4, 0.2);
        mDzAPI.drawLine(2, 31.6, 68, 31.6, 0.2);
        mDzAPI.drawLine(2, 40.8, 49, 40.8, 0.2);
        mDzAPI.drawLine(2, 40.8, 49, 40.8, 0.2);
        mDzAPI.draw2DQRCode(list.getId(), 51, 33, 16);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 2.5, 7, 66, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 2.5, 16, 66, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(2), 2.5, 25, 66, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(3), 2.5, 35, 47, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(4), 2.5, 44, 47, 10, 3.125);

        return mDzAPI.commitJob();
    }

    private static boolean printerLabel70505(Print.ListBean list) {
        mDzAPI.startJob(70, 50, 1);
        mDzAPI.drawRectangle(2, 22, 66, 28, 0.2);
        mDzAPI.drawLine(50, 30.6, 50, 50, 0.2);
        mDzAPI.drawLine(2, 30.6, 68, 30.6, 0.2);
        mDzAPI.drawLine(2, 39.8, 50, 39.8, 0.2);
        mDzAPI.draw2DQRCode(list.getId(), 51, 32, 16);
        mDzAPI.drawText(list.getTemplate_attr().get(0), 2.5, 24, 67, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(1), 2.5, 33, 47, 10, 3.125);
        mDzAPI.drawText(list.getTemplate_attr().get(2), 2.5, 42.5, 47, 10, 3.125);

        return mDzAPI.commitJob();
    }

    public static void closed() {
        init();
        mDzAPI.closePrinter();
    }

    public static boolean isPrinterConnected() {
        init();
        IDzPrinter.PrinterState state = mDzAPI.getPrinterState();
        if (state != null && !state.equals(IDzPrinter.PrinterState.Disconnected)) {
            return !state.equals(IDzPrinter.PrinterState.Connecting);
        } else {
            return false;
        }
    }

//    private static List<String> subString(ResultPrint.PrintList list) {
//        List<String> drawTextList = new ArrayList<>();
//        for (int i = 0; i < list.getTemplate_attr().size(); i++) {
//            String drawText = list.getTemplate_attr().get(i);
//            if (drawText.length() > 24) {
//                drawText = drawText.substring(0, 23);
//             }
//            drawTextList.add(drawText);
//        }
//        return drawTextList;
//    }
}
