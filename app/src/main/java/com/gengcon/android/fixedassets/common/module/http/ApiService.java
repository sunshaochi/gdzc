package com.gengcon.android.fixedassets.common.module.http;

import com.gengcon.android.fixedassets.bean.Area;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.request.ApprovalAssetRequest;
import com.gengcon.android.fixedassets.bean.request.AssetOffNetRequest;
import com.gengcon.android.fixedassets.bean.request.AssetSyncDataRequest;
import com.gengcon.android.fixedassets.bean.request.AuditSaveRequest;
import com.gengcon.android.fixedassets.bean.request.CheckPhoneRequest;
import com.gengcon.android.fixedassets.bean.request.CheckRenameRequest;
import com.gengcon.android.fixedassets.bean.request.DelEmpRequest;
import com.gengcon.android.fixedassets.bean.request.FeedbackRequest;
import com.gengcon.android.fixedassets.bean.request.ForgetPwdRequest;
import com.gengcon.android.fixedassets.bean.request.ModifyPasswordRequest;
import com.gengcon.android.fixedassets.bean.request.PhoneCodeRequest;
import com.gengcon.android.fixedassets.bean.request.PrintTagRequest;
import com.gengcon.android.fixedassets.bean.request.RegisterRequest;
import com.gengcon.android.fixedassets.bean.request.SonAuditRequest;
import com.gengcon.android.fixedassets.bean.result.AddAssetCustom;
import com.gengcon.android.fixedassets.bean.result.AddAssetList;
import com.gengcon.android.fixedassets.bean.result.ApprovalDetailBean;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;
import com.gengcon.android.fixedassets.bean.result.ApprovalNum;
import com.gengcon.android.fixedassets.bean.result.AssetCode;
import com.gengcon.android.fixedassets.bean.result.ChooseUserBean;
import com.gengcon.android.fixedassets.bean.result.ClassificationBean;
import com.gengcon.android.fixedassets.bean.result.ContactUs;
import com.gengcon.android.fixedassets.bean.result.ForgetPwd;
import com.gengcon.android.fixedassets.bean.result.Industry;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.bean.result.ResultInventory;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.bean.result.StaffManagerBean;
import com.gengcon.android.fixedassets.bean.result.UpLoadBean;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.bean.result.UserData;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class ApiService {

    /**
     * v3.0接口
     */

    //权限管理
    public interface GetRoleResourceForApp {
        @GET("app/power")
        Observable<Bean<ResultRole>> getRoleResourceForAPP();
    }

    //首页数据
    public interface GetHome {
        @GET("app/reports/assetStatus")
        Observable<Bean<Home>> getHome();
    }

    //获取管理员信息
    public interface GetUsers {
        @GET("common/getUser")
        Observable<Bean<List<User>>> getUsers();
    }

    //扫码登录
    public interface ScanLogin {
        @GET("ucenter/scanPcAutoLogin")
        Observable<Bean> scanLogin(@Query("qr_string") String uuid, @Query("login_status") int login_status);
    }

    //版本更新
    public interface GetVersion {
        @GET("app/systemVersion")
        Observable<Bean<UpdateVersion>> getVersion();
    }

    //扫码查看资产
    public interface AssetDetail {
        @GET("app/asset/scanDetail")
        Observable<Bean> getAssetDetail(@Query("asset_id") String asset_id);
    }

    //首页消息通知
    public interface GetUserPopupNotice {
        @GET("app/notice/newNotice")
        Observable<Bean<UserPopupNotice>> getUserPopupNotice();
    }

    //消息列表页面
    public interface GetUserNotice {
        @GET("app/notice/list")
        Observable<Bean<MessageBean>> getUserNotice(@Query("page") int page);
    }

    //打印接口
    public interface GetPrintTag {
        @POST("printTag/addLog")
        Observable<Bean> getPrintTag(@Body PrintTagRequest printTagRequest);
    }

    //待审批资产列表
    public interface GetApprovalAssetList {
        @POST("doc/getAssetList")
        Observable<Bean<ApprovalDetailBean>> getApprovalAssetList(@Body ApprovalAssetRequest request);
    }

    //获取待审批头部基本信息
    public interface GetApprovalHeadDetail {
        @GET("app/doc/auditDocDetail")
        Observable<Bean<ApprovalHeadDetail>> getApprovalHeadDetail(@Query("doc_no") String doc_no);
    }

    //待审批审核
    public interface GetAuditSave {
        @POST("doc/auditSave")
        Observable<Bean> getAuditSave(@Body AuditSaveRequest request);
    }

    //意见反馈
    public interface GetFeedback {
        @POST("app/ucenter/question")
        Observable<Bean> getFeedback(@Body FeedbackRequest request);
    }

    //修改密码
    public interface GetModifyPassword {
        @POST("ucenter/editPwd")
        Observable<Bean> getModifyPassword(@Body ModifyPasswordRequest request);
    }

    //联系我们
    public interface GetContactUs {
        @GET("app/contactUs")
        Observable<Bean<ContactUs>> getContactUs();
    }

    //个人资料
    public interface GetPersonal {
        @GET("ucenter/personalData")
        Observable<Bean<PersonalBean>> getPersonal();
    }

    //修改密码

    /**
     * type = 1,注册验证码;
     * type = 2,忘记密码验证码;
     * type = 3,修改手机号验证码;
     * type = 4,修改资料;
     * type = 5,注销账号;
     * type = 6,旧手机号验证码;
     */
    public interface GetPhoneCode {
        @POST("system/getcode")
        Observable<Bean> getPhoneCode(@Body PhoneCodeRequest request);
    }

    //校验旧手机号
    public interface GetCheckOldPhone {
        @POST("ucenter/checkOldPhone")
        Observable<Bean> getCheckOldPhone(@Body CheckPhoneRequest request);
    }

    //修改手机号
    public interface GetCheckNewPhone {
        @POST("ucenter/editPhone")
        Observable<Bean> getCheckNewPhone(@Body CheckPhoneRequest request);
    }

    //注册
    public interface GetRegisterPhone {
        @POST("ucenter/checkNewPhone")
        Observable<Bean> getRegisterPhone(@Body CheckPhoneRequest request);
    }

    //获取行业数据
    public interface GetIndustry {
        @GET("common/getIndustry")
        Observable<Bean<List<Industry>>> getIndustry();
    }

    //校验用户名
    public interface GetCheckRename {
        @POST("ucenter/registerCheckRename")
        Observable<Bean> getCheckRename(@Body CheckRenameRequest request);
    }

    //注册完成
    public interface GetRegister {
        @POST("ucenter/register")
        Observable<Bean<UserData>> getRegister(@Body RegisterRequest request);
    }

    //忘记密码验证码
    public interface GetResetPwdVerify {
        @POST("ucenter/resetpwdVerify")
        Observable<Bean<ForgetPwd>> getResetPwdVerify(@Body CheckPhoneRequest request);
    }

    //忘记密码修改校验身份
    public interface GetResetPwd {
        @POST("ucenter/resetpwd")
        Observable<Bean> getResetPwd(@Body ForgetPwdRequest request);
    }

    //获取待审批以及已审批头部数据
    public interface GetApprovalNum {
        @GET("app/doc/auditNum")
        Observable<Bean<ApprovalNum>> getApprovalNum();
    }

    //待审批
    public interface GetWaitApprovalList {
        @GET("app/doc/auditList")
        Observable<Bean<ResultApprovals>> getWaitApprovalList(@Query("page") int page, @Query("limit") int limit);
    }

    //已审批
    public interface GetCompleteApprovalList {
        @GET("app/doc/auditEndList")
        Observable<Bean<ResultApprovals>> getCompleteApprovalList(@Query("page") int page, @Query("limit") int limit);
    }

    //资产入库模板数据
    public interface GetAssetAddList {
        @GET("tpl/getBaseTplGroupAttr")
        Observable<Bean<AddAssetList>> getAddAssetList();
    }

    //资产入库扩展模板
    public interface GetCustomList {
        @GET("tpl/getTplExtendAttr")
        Observable<Bean<List<AddAssetCustom>>> getAddAssetCustomList(@Query("tpl_id") String tpl_id);
    }

    //资产入库所属组织
    public interface GetOrgList {
        @GET("common/getAllowOrg")
        Observable<Bean<List<OrgBean>>> getOrgList();
    }

    //资产入库分类
    public interface GetClassificationList {
        @GET("common/getAllowCustomType")
        Observable<Bean<List<ClassificationBean>>> getClassificationList();
    }

    //获取资产编码
    public interface GetAssetCode {
        @GET("asset/getAssetCode")
        Observable<AssetCode> getAssetCode();
    }

    //选择使用人
    public interface GetAssetUser {
        @GET("common/getEmp")
        Observable<Bean<List<ChooseUserBean>>> getAssetUser(@Query("wd") String wd, @Query("org_id") String org_id);
    }

    //添加资产
    public interface GetAddAsset {
        @POST("asset/add")
        Observable<Bean> getAddAsset(@Body RequestBody request);
    }

    //获取资产区域
    public interface GetArea {
        @GET("common/getArea")
        Observable<Bean<List<Area>>> getArea();
    }

    //组织列表
    public interface GetOrgSettingList {
        @GET("common/getOrg")
        Observable<Bean<List<OrgBean>>> getOrgSettingList();
    }

    //组织下级列表
    public interface GetOrgChildrenList {
        @GET("common/getOrg")
        Observable<Bean<List<OrgBean>>> getOrgChildrenList(@Query("pid") int pid);
    }

    //新增组织
    public interface GetAddOrg {
        @POST("org/add")
        Observable<Bean> getAddOrg(@Body RequestBody request);
    }

    //编辑组织
    public interface GetEditOrg {
        @POST("org/edit")
        Observable<Bean> getEditOrg(@Body RequestBody request);
    }

    //删除组织
    public interface GetDelOrg {
        @POST("org/del")
        Observable<Bean> getDelOrg(@Body RequestBody request);
    }

    //员工列表
    public interface GetStaffManagerList {
        @GET("app/emp/listApp")
        Observable<Bean<StaffManagerBean>> getStaffManagerList();
    }

    //组织下级列表
    public interface GetStaffChildrenList {
        @GET("app/emp/listApp")
        Observable<Bean<StaffManagerBean>> getStaffChildrenList(@Query("org_id") int org_id);
    }

    //员工信息
    public interface GetStaffDetail {
        @GET("emp/detail")
        Observable<Bean<StaffDetailBean>> getStaffDetail(@Query("emp_id") int emp_id);
    }

    //删除员工
    public interface GetDelEmp {
        @POST("emp/del")
        Observable<Bean> getDelEmp(@Body DelEmpRequest request);
    }

    //新增员工
    public interface GetAddEmp {
        @POST("emp/add")
        Observable<Bean> getAddEmp(@Body RequestBody request);
    }

    //获取员工工号
    public interface GetEmpNo {
        @GET("emp/getEmpNo")
        Observable<Bean> getEmpNo();
    }

    //编辑员工
    public interface GetEditEmp {
        @POST("emp/edit")
        Observable<Bean> getEditEmp(@Body RequestBody request);
    }

    //离线盘点列表
    public interface GetOffnetList {
        @GET("app/pd/offnetList")
        Observable<Bean<ResultInventory>> getOffnetList();
    }

    //离线盘点详情列表
    public interface GetAssetOffNetList {
        @POST("app/pd/assetOffNetList")
        Observable<Bean<ResultAsset>> getAssetOffNetList(@Body AssetOffNetRequest request);
    }

    //上传离线盘点数据
    public interface GetAssetSyncData {
        @POST("app/pd/syncUploadPdData")
        Observable<Bean> getAssetSyncData(@Body AssetSyncDataRequest request);
    }

    //子任务-提交审核
    public interface GetSonAudit {
        @POST("app/pd/submitSonAudit")
        Observable<Bean> getSonAudit(@Body SonAuditRequest request);
    }

    public interface upLoad {
        @Multipart
        @POST("asset/upload")
        Observable<Bean<UpLoadBean>> upLoadPhoto(@Part MultipartBody.Part file);
    }

}
