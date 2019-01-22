package com.gengcon.android.fixedassets.htttp;

import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.request.CheckApiRouteRequest;
import com.gengcon.android.fixedassets.bean.request.CreateInventoryRequest;
import com.gengcon.android.fixedassets.bean.request.EditAssetRequest;
import com.gengcon.android.fixedassets.bean.request.EditAssetListRequest;
import com.gengcon.android.fixedassets.bean.request.InventoryAssetRequest;
import com.gengcon.android.fixedassets.bean.request.InventoryRRequest;
import com.gengcon.android.fixedassets.bean.request.PreviewRequest;
import com.gengcon.android.fixedassets.bean.request.PrintTagRequest;
import com.gengcon.android.fixedassets.bean.request.UpdateInventoryRequest;
import com.gengcon.android.fixedassets.bean.request.UploadInventoryRequest;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryR;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.bean.result.PreviewInfo;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.bean.result.ResultInventorysNum;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ApiService {

    /**
     * v3.0接口
     */
    //权限管理
//    public interface GetRoleResourceForApp {
//        @GET("feRoute")
//        Observable<Bean<ResultRole>> getRoleResourceForAPP();
//    }

    //权限管理
    public interface GetRoleResourceForApp {
        @GET("common/getAppPower")
        Observable<Bean<ResultRole>> getRoleResourceForAPP();
    }

    //首页数据
    public interface GetHome {
        @GET("reports/appHome")
        Observable<Bean<Home>> getHome();
    }

    //权限检查
    public interface CheckApiRoute {
        @POST("checkApiRoutePower")
        Observable<Bean<List<Boolean>>> checkApiRoute(@Body CheckApiRouteRequest request);
    }

    //获取盘点单页面我的任务与我创建的数量
    public interface GetInventorysNum {
        @GET("inventory/myNum")
        Observable<Bean<ResultInventorysNum>> getInventorysNum();
    }

    //我的任务
    public interface GetMyTaskInventory {
        @POST("inventory/myTaskList")
        Observable<Bean<ResultInventorys>> getMyTaskInventory(@Query("page") int page);
    }

    //我创建的
    public interface GetMyCreateInventory {
        @POST("inventory/myCreateList")
        Observable<Bean<ResultInventorys>> getMyCreateInventory(@Query("page") int page);
    }

    //保存新建盘点单
    public interface AddInventory {
        @POST("inventory/add")
        Observable<Bean> addInventory(@Body CreateInventoryRequest request);
    }

    //保存编辑盘点单
    public interface UpdateInventory {
        @POST("inventory/edit")
        Observable<Bean> updateInventory(@Body UpdateInventoryRequest request);
    }

    //获取管理员信息
    public interface GetUsers {
        @GET("common/getUser")
        Observable<Bean<List<User>>> getUsers();
    }

    //获取盘点单头部基本信息
    public interface GetHeadDetail {
        @GET("inventory/getDetail")
        Observable<Bean<InventoryHeadDetail>> getHeadDetail(@Query("inv_no") String inv_no);
    }

    //获取盘点单详情资产列表
    public interface GetAssetListDetail {
        @POST("inventory/getAssetList")
        Observable<Bean<InventoryDetail>> getInventoryDetail(@Body InventoryAssetRequest request);
    }

    //盘点单编辑入口
    public interface GetEditDetail {
        @POST("inventory/editAssetList")
        Observable<Bean<InventoryDetail>> getEditDetail(@Body EditAssetRequest request);
    }

    //盘点单编辑资产
    public interface GetEditAssetListDetail {
        @POST("inventory/editAssetList")
        Observable<Bean<InventoryDetail>> getEditAssetListDetail(@Body EditAssetListRequest request);
    }

    //删除盘点单
    public interface DeleteInventory {
        @POST("inventory/del")
        Observable<Bean> deleteInventory(@Query("inv_no") String inv_no);
    }

    //上传盘点结果
    public interface UploadInventoryResult {
        @POST("inventory/uploadResult")
        Observable<Bean> uploadInventoryResult(@Body UploadInventoryRequest request);
    }

    //盘点预览
    public interface PreviewInventoryInfo {
        @POST("inventory/preview")
        Observable<Bean<PreviewInfo>> previewInventoryInfo(@Body PreviewRequest request);
    }

    //盘点结果
    public interface ShowInventoryResult {
        @POST("inventory/getResultDetail")
        Observable<Bean<InventoryR>> showInventoryResult(@Body InventoryRRequest request);
    }

    //扫码登录
    public interface ScanLogin {
        @GET("ucenter/scanPcAutoLogin")
        Observable<Bean> scanLogin(@Query("qr_string") String uuid, @Query("login_status") int login_status);
    }

    //版本更新
    public interface GetVersion {
        @GET("systemVersion")
        Observable<Bean<UpdateVersion>> getVersion();
    }

    //扫码查看资产
    public interface AssetDetail {
        @GET("asset/scanDetail")
        Observable<Bean> getAssetDetail(@Query("asset_id") String asset_id);
    }

    //首页消息通知
    public interface GetUserPopupNotice {
        @GET("noticeTag/getUserPopupNotice")
        Observable<Bean<UserPopupNotice>> getUserPopupNotice();
    }

    //全体消息已读接口
    public interface GetReadAddNotice {
        @POST("noticeTag/add")
        Observable<Bean> getReadAddNotice(@Query("notice_id") int notice_id);
    }

    //非全体消息已读接口
    public interface GetReadEditNotice {
        @POST("noticeTag/edit")
        Observable<Bean> getReadEditNotice(@Query("notice_id") int notice_id);
    }

    //消息列表页面
    public interface GetUserNotice {
        @GET("noticeTag/getUserNotice")
        Observable<Bean<MessageBean>> getUserNotice();
    }

    //打印接口
    public interface GetPrintTag {
        @POST("printTag/addLog")
        Observable<Bean> getPrintTag(@Body PrintTagRequest printTagRequest);
    }
}
