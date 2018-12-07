package com.gengcon.android.fixedassets.bean.result;


import java.io.Serializable;

public class ResultInventorysNum implements Serializable {

    int create_num;
    int task_num;

    public int getCreate_num() {
        return create_num;
    }

    public void setCreate_num(int create_num) {
        this.create_num = create_num;
    }

    public int getTask_num() {
        return task_num;
    }

    public void setTask_num(int task_num) {
        this.task_num = task_num;
    }
}
