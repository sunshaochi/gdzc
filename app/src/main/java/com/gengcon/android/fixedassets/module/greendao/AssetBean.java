package com.gengcon.android.fixedassets.module.greendao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class AssetBean implements Parcelable {

    /**
     * asset_id : 5c3597e32fd36e707233526d
     * asset_name : 测试一条数据
     * asset_code : JC201985457
     * photourl :
     * status : 1
     * pd_status : 1
     */
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String tag;
    private String pd_no;
    private String asset_id;
    private String asset_name;
    private String asset_code;
    private String photourl;
    private Integer status;
    private Integer pd_status;
    private Integer isScanAsset;
    private String user_id;

    @Generated(hash = 236051683)
    public AssetBean(Long id, String tag, String pd_no, String asset_id,
            String asset_name, String asset_code, String photourl, Integer status,
            Integer pd_status, Integer isScanAsset, String user_id) {
        this.id = id;
        this.tag = tag;
        this.pd_no = pd_no;
        this.asset_id = asset_id;
        this.asset_name = asset_name;
        this.asset_code = asset_code;
        this.photourl = photourl;
        this.status = status;
        this.pd_status = pd_status;
        this.isScanAsset = isScanAsset;
        this.user_id = user_id;
    }

    @Generated(hash = 596597220)
    public AssetBean() {
    }

    protected AssetBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        tag = in.readString();
        pd_no = in.readString();
        asset_id = in.readString();
        asset_name = in.readString();
        asset_code = in.readString();
        photourl = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            pd_status = null;
        } else {
            pd_status = in.readInt();
        }
        if (in.readByte() == 0) {
            isScanAsset = null;
        } else {
            isScanAsset = in.readInt();
        }
        user_id = in.readString();
    }

    public static final Creator<AssetBean> CREATOR = new Creator<AssetBean>() {
        @Override
        public AssetBean createFromParcel(Parcel in) {
            return new AssetBean(in);
        }

        @Override
        public AssetBean[] newArray(int size) {
            return new AssetBean[size];
        }
    };

    public String getPd_no() {
        return pd_no;
    }

    public void setPd_no(String pd_no) {
        this.pd_no = pd_no;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPd_status() {
        return pd_status;
    }

    public void setPd_status(int pd_status) {
        this.pd_status = pd_status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPd_status(Integer pd_status) {
        this.pd_status = pd_status;
    }

    public Integer getIsScanAsset() {
        return this.isScanAsset;
    }

    public void setIsScanAsset(Integer isScanAsset) {
        this.isScanAsset = isScanAsset;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(tag);
        dest.writeString(pd_no);
        dest.writeString(asset_id);
        dest.writeString(asset_name);
        dest.writeString(asset_code);
        dest.writeString(photourl);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        if (pd_status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pd_status);
        }
        if (isScanAsset == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isScanAsset);
        }
        dest.writeString(user_id);
    }
}
