package com.example.print.app.new_print;

import java.util.ArrayList;
import java.util.List;


public class ExcelChangeManager {

    private List<IExcelDataChangeListener> IExcelDataChangeListeners = new ArrayList<>();

    private static ExcelChangeManager instance;

    private ExcelChangeManager() {

    }

    public List<IExcelDataChangeListener> getIExcelDataChangeListeners() {
        return IExcelDataChangeListeners;
    }

    public static ExcelChangeManager getManager() {
        synchronized (ExcelChangeManager.class) {
            if (instance == null) {
                instance = new ExcelChangeManager();
            }
            return instance;
        }
    }

    public int getCount() {
        int count = 0;
        for (IExcelDataChangeListener vn : IExcelDataChangeListeners) {
            count += vn.getExcelData().size();
        }
        return count;
    }

    public int getMax() {
        int count = 0;
        for (IExcelDataChangeListener vn : IExcelDataChangeListeners) {
            if (vn.getExcelData().size() > count) {
                count = vn.getExcelData().size();
            }
        }
        return count;
    }

    public void postChange(int po) {
        for (IExcelDataChangeListener vn : IExcelDataChangeListeners) {
            vn.onUpdate(po);
        }
    }

    /**
     * 注册通知提醒
     */
    public void registerChange(IExcelDataChangeListener IExcelDataChangeListener) {
        if (!IExcelDataChangeListeners.contains(IExcelDataChangeListener)) {
            IExcelDataChangeListeners.add(IExcelDataChangeListener);
        }
    }

    /**
     * 取消通知提醒
     */
    public void unregisterChange(IExcelDataChangeListener IExcelDataChangeListener) {
        IExcelDataChangeListeners.remove(IExcelDataChangeListener);

    }


    public interface IExcelDataChangeListener {
        void onUpdate(int position);

        void setExcelData(List<String> excelData);

        List<String> getExcelData();

        boolean hasExcelData();


    }

}
