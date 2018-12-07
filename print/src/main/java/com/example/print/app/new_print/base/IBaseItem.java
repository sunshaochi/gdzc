package com.example.print.app.new_print.base;

/**
 * 定义itemView通用操作的接口
 */
public interface IBaseItem {

    /**
     * 初始化
     */
    public void init();


    /**
     * 删除
     */
    public void delete();

    /**
     * 放大
     */
    public void amplification();

    /**
     * 缩小
     */
    public void narrow();

    /**
     * 旋转
     */
    public void rota();

    /**
     * 复制
     */
    public BaseItemView copy();

    /**
     * 锁定
     */
    public void lock();

    /**
     * 撤销
     */
//    public void undo();

    /**
     * 恢复
     */
//    public void restore();


    public void displacement(BaseItemView.Orientation orientation);

    enum Orientation {
        left, top, right, bottom
    }

    enum DrawType {
        none, translate, padding
    }
}
