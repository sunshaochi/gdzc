package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class Print implements Serializable {

    /**
     * lable : {"id":1,"qrcode_position":1,"tag_pre_url":"/tags/5030/5030pre-1.png","size_long":"50mm","size_wide":"30mm","size_id":1}
     * list : [{"id":"ojbnedlfkjel3kv6idv40tkk5k","template_attr":["武汉精臣智慧","资产名称:变更","资产编号:201811215170","使用人:赵嫱嫱"]}]
     */

    private LableBean lable;
    private List<ListBean> list;

    public LableBean getLable() {
        return lable;
    }

    public void setLable(LableBean lable) {
        this.lable = lable;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class LableBean {
        /**
         * id : 1
         * qrcode_position : 1
         * tag_pre_url : /tags/5030/5030pre-1.png
         * size_long : 50mm
         * size_wide : 30mm
         * size_id : 1
         */

        private int id;
        private int qrcode_position;
        private String tag_pre_url;
        private String size_long;
        private String size_wide;
        private int size_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQrcode_position() {
            return qrcode_position;
        }

        public void setQrcode_position(int qrcode_position) {
            this.qrcode_position = qrcode_position;
        }

        public String getTag_pre_url() {
            return tag_pre_url;
        }

        public void setTag_pre_url(String tag_pre_url) {
            this.tag_pre_url = tag_pre_url;
        }

        public String getSize_long() {
            return size_long;
        }

        public void setSize_long(String size_long) {
            this.size_long = size_long;
        }

        public String getSize_wide() {
            return size_wide;
        }

        public void setSize_wide(String size_wide) {
            this.size_wide = size_wide;
        }

        public int getSize_id() {
            return size_id;
        }

        public void setSize_id(int size_id) {
            this.size_id = size_id;
        }
    }

    public static class ListBean {
        /**
         * id : ojbnedlfkjel3kv6idv40tkk5k
         * template_attr : ["武汉精臣智慧","资产名称:变更","资产编号:201811215170","使用人:赵嫱嫱"]
         */

        private String id;
        private List<String> template_attr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getTemplate_attr() {
            return template_attr;
        }

        public void setTemplate_attr(List<String> template_attr) {
            this.template_attr = template_attr;
        }
    }
}
