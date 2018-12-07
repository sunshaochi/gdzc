package com.example.print.app.new_print.image;

import android.widget.ImageView;
import android.widget.TextView;

public interface ImageItem {
    /**
     * 返回文字对象
     * @return TextView
     */
    TextView getText();

    /**
     * 返回图象对象
     * @return ImageView
     */
    ImageView getImageView();

    /**
     * 设置文字在上面
     */
    void setTopText();

    /**
     * 设置文字在下面
     */
    void setBottomText();
    /**
     * 設置沒有文字
     */
    void setNoText();


}
