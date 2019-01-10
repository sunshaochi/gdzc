package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class MessageBean implements Serializable {

    /**
     * total : 7
     * list : [{"id":113,"type":2,"title":"标题","outline":"概要","content":"内容 ","push_model":2,"photourl":"","publisher":"发布人","level":1,"scene":3,"created_at":"2019-01-04 13:48:11","push_at":"2019-01-05 22:16:17","end_at":"","is_read":"0","reading_at":"0","notice_name":"系统消息"},{"id":115,"type":2,"title":"标题","outline":"概要","content":"内容 ","push_model":2,"photourl":"","publisher":"发布人","level":1,"scene":3,"created_at":"2019-01-05 09:06:59","push_at":"2019-01-05 11:09:49","end_at":"","is_read":"0","reading_at":"0","notice_name":"系统消息"},{"id":108,"type":2,"title":"标题","outline":"肯定就发了圣诞节疯狂垃圾的分类看酒店开房记录时代峻峰","content":"肯定就发了圣诞节疯狂垃圾的分类看酒店开房记录时代峻峰","push_model":2,"photourl":"","publisher":"发布人","level":1,"scene":3,"created_at":"2019-01-04 13:25:12","push_at":"2019-01-05 11:09:39","end_at":"","is_read":"0","reading_at":"0","notice_name":"系统消息"},{"id":110,"type":2,"title":"标题","outline":"概要","content":"内容 ","push_model":2,"photourl":"","publisher":"发布人","level":1,"scene":2,"created_at":"2019-01-04 13:40:20","push_at":"2019-01-05 11:09:35","end_at":"","is_read":"0","reading_at":"0","notice_name":"系统消息"},{"id":111,"type":2,"title":"标题","outline":"概要","content":"","push_model":1,"photourl":"","publisher":"发布人","level":1,"scene":3,"created_at":"2019-01-04 13:42:18","push_at":"2019-01-05 11:09:34","end_at":"","is_read":"1","reading_at":"1546671734","notice_name":"系统消息"},{"id":109,"type":1,"title":"标题","outline":"肯定就发了圣诞节疯狂垃圾的分类看酒店开房记录时代峻峰","content":"奋斗就是了飞机速度","push_model":2,"photourl":"","publisher":"发布人","level":1,"scene":1,"created_at":"2019-01-04 13:39:28","push_at":"2019-01-05 11:09:33","end_at":"","is_read":"1","reading_at":"0","notice_name":"站内公告"},{"id":107,"type":2,"title":"标题","outline":"概要","content":"","push_model":1,"photourl":"file/fixasset/uploads/notice/b8fda0704d48bf563a1cb260b779010e.jpg","publisher":"发布人","level":1,"scene":3,"created_at":"2019-01-04 13:25:03","push_at":"2019-01-05 11:09:32","end_at":"","is_read":"0","reading_at":"0","notice_name":"系统消息"}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 113
         * type : 2
         * title : 标题
         * outline : 概要
         * content : 内容
         * push_model : 2
         * photourl :
         * publisher : 发布人
         * level : 1
         * scene : 3
         * created_at : 2019-01-04 13:48:11
         * push_at : 2019-01-05 22:16:17
         * end_at :
         * is_read : 0
         * reading_at : 0
         * notice_name : 系统消息
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
