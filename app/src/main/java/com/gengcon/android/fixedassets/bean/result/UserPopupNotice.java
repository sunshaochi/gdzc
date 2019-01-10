package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class UserPopupNotice implements Serializable {

    /**
     * unread : 1
     * list : {"id":114,"type":1,"title":"标题66","outline":"负数负数福建省开了房间拉法基乱世佳人破文件费荆防颗粒圣诞节福利开始放假是的冯绍峰防守打法水电费水电费水电费水电费是否水电费","content":"","push_model":1,"photourl":"","publisher":"发布人","level":1,"scene":3,"created_at":"2019-01-05 11:07:29","push_at":"2019-01-08 16:05:01","end_at":"2019-01-11 16:14:52","is_read":"0","reading_at":"0","notice_name":"站内公告"}
     */

    private int unread;
    private ListBean list;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 114
         * type : 1
         * title : 标题66
         * outline : 负数负数福建省开了房间拉法基乱世佳人破文件费荆防颗粒圣诞节福利开始放假是的冯绍峰防守打法水电费水电费水电费水电费是否水电费
         * content :
         * push_model : 1
         * photourl :
         * publisher : 发布人
         * level : 1
         * scene : 3
         * created_at : 2019-01-05 11:07:29
         * push_at : 2019-01-08 16:05:01
         * end_at : 2019-01-11 16:14:52
         * is_read : 0
         * reading_at : 0
         * notice_name : 站内公告
         */

        private int id;
        private int type;
        private String title;
        private String outline;
        private String content;
        private int push_model;
        private String photourl;
        private String publisher;
        private int level;
        private int scene;
        private String created_at;
        private String push_at;
        private String end_at;
        private String is_read;
        private String reading_at;
        private String notice_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOutline() {
            return outline;
        }

        public void setOutline(String outline) {
            this.outline = outline;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPush_model() {
            return push_model;
        }

        public void setPush_model(int push_model) {
            this.push_model = push_model;
        }

        public String getPhotourl() {
            return photourl;
        }

        public void setPhotourl(String photourl) {
            this.photourl = photourl;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getScene() {
            return scene;
        }

        public void setScene(int scene) {
            this.scene = scene;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getPush_at() {
            return push_at;
        }

        public void setPush_at(String push_at) {
            this.push_at = push_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getReading_at() {
            return reading_at;
        }

        public void setReading_at(String reading_at) {
            this.reading_at = reading_at;
        }

        public String getNotice_name() {
            return notice_name;
        }

        public void setNotice_name(String notice_name) {
            this.notice_name = notice_name;
        }
    }
}
