package com.gengcon.android.fixedassets.view;

public interface ItemTouchListener {

    void onItemClick(int position);

    void onEditMenuClick(String inv_no);

    void onDeleteMenuClick(String inv_no, int position);
}
