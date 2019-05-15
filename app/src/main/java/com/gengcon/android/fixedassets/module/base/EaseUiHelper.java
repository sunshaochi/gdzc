package com.gengcon.android.fixedassets.module.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.AgentInfo;
import com.hyphenate.helpdesk.model.MessageHelper;

public class EaseUiHelper {

    public static EaseUiHelper instance = new EaseUiHelper();
    //必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
    public static final String EASEMOB_KEY = "1430190319061960#kefuchannelapp66683";
    //必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
    public static final String TENANTID = "66683";
    //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
    public static final String SERVICEIMNUMBER="kefuchannelimid_372284";

    private UIProvider _uiProvider;
    private Context appContext;

    private EaseUiHelper() {
    }

    public synchronized static EaseUiHelper getInstance() {
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(final Context context) {
        appContext = context;
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(EASEMOB_KEY);//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId(TENANTID);//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
        options.setConsoleLog(true);//// 设置为true后，将打印日志到logcat, 发布APP时应关闭该选项
        // Kefu SDK 初始化
//        if (!ChatClient.getInstance().init(appContext, options)) {//初始化时候怕retrun所以这里把demo的改下直接放在true下面走
//            return;
//        }
        if (ChatClient.getInstance().init(appContext, options)) {
            // Kefu EaseUI的初始化
//        UIProvider.getInstance().init(this);
            //后面可以设置其他属性
            _uiProvider = UIProvider.getInstance();
            //初始化EaseUI
            _uiProvider.init(appContext);
            //调用easeui的api设置providers
            setEaseUIProvider(appContext);
        }
    }

    private void setEaseUIProvider(Context context) {
        //设置头像和昵称 某些控件可能没有头像和昵称，需要注意
        UIProvider.getInstance().setUserProfileProvider(new UIProvider.UserProfileProvider() {
            @Override
            public void setNickAndAvatar(Context context, Message message, ImageView userAvatarView, TextView usernickView) {
                if (message.direct() == Message.Direct.RECEIVE) {
                    //设置接收方的昵称和头像
//                    UserUtil.setAgentNickAndAvatar(context, message, userAvatarView, usernickView);
                    AgentInfo agentInfo = MessageHelper.getAgentInfo(message);
                    if (usernickView != null) {
                        usernickView.setText(message.from());
                        if (agentInfo != null) {
                            if (!TextUtils.isEmpty(agentInfo.getNickname())) {
                                usernickView.setText(agentInfo.getNickname());
                            }
                        }
                    }
                    if (userAvatarView != null) {
//                        if (agentInfo != null) {
//                            if (!TextUtils.isEmpty(agentInfo.getAvatar())) {
//                                String strUrl = agentInfo.getAvatar();
//                                // 设置客服头像
//                                if (!TextUtils.isEmpty(strUrl)) {
//                                    if (!strUrl.startsWith("http")) {
//                                        strUrl = "http:" + strUrl;
//                                    }
//                                    //正常的string路径
//                                    Glide.with(context).load(strUrl).apply(RequestOptions.placeholderOf(com.hyphenate.helpdesk.R.drawable.hd_default_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop()).into(userAvatarView);
////                                    Glide.with(context).load(strUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(com.hyphenate.helpdesk.R.drawable.hd_default_avatar).transform(new GlideCircleTransform(context)).into(userAvatarView);
//                                    return;
//                                }
//                            }
//                        }
//                        userAvatarView.setImageResource(com.hyphenate.helpdesk.R.drawable.hd_default_avatar);
                        userAvatarView.setImageResource(R.drawable.kfphoto);
                    }
                } else {
                    //此处设置当前登录用户的头像，
                    if (userAvatarView != null) {
                        userAvatarView.setImageResource(R.drawable.hd_default_avatar);
//                        Glide.with(context).load("http://oev49clxj.bkt.clouddn.com/7a8aed7bjw1f32d0cumhkj20ey0mitbx.png").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.hd_default_avatar).into(userAvatarView);
//                        如果用圆角，可以采用此方案：http://blog.csdn.net/weidongjian/article/details/47144549
                    }
                }
            }
        });
    }

    /**
     * 注册环信聊天
     */
    public void RegistEasemo(final String user, final String password){
        ChatClient.getInstance().register(user, password, new Callback() {
            @Override
            public void onSuccess() {
                Log.i("环信注册成功", "onSuccess: ");
                LoginEasemo(user,password);
            }

            @Override
            public void onError(int code, String error) {
                //203表示用户已经存在
                if(code==203){
                    LoginEasemo(user,password);
                }else {
                    Log.e("环信注册失败", "onError: " + code + "," + error);
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.i("环信注册中", "registing:");
            }
        });
    }

    /**
     * 环信登录成功
     * @param user
     * @param password
     */
    public  void LoginEasemo(String user,String password) {
        if (!isLogin()) {//如果没有登录就走登录
            ChatClient.getInstance().login(user, password, new Callback() {
                @Override
                public void onSuccess() {
                    Log.i("环信登录", "onSuccess: ");
                    goTalk();
                }

                @Override
                public void onError(int code, String error) {
                    Log.e("环信登录失败", "onError: " + code + "," + error);
                }

                @Override
                public void onProgress(int progress, String status) {
                    Log.i("环信登录中", "Login..: ");
                }
            });
        }else {
               goTalk();
        }

    }


    /**
     * 跳转到聊天界面
     */
    public void goTalk(){
            Intent intent = new IntentBuilder(appContext).setServiceIMNumber(SERVICEIMNUMBER)
                    .setTitleName("武汉精臣智慧标识科技有限公司")
                    .build();
            appContext.startActivity(intent);

    }

    /**
     * 退出环信
     */
    public void LoginOutEase(){
        if(isLogin()){//如果是登录的
            //已经登录，可以直接进入会话界面
            ChatClient.getInstance().logout(true, new Callback() {
                @Override
                public void onSuccess() {
                    Log.i("环信退出", "onSuccess: ");
                }

                @Override
                public void onError(int code, String error) {
                    Log.e("环信退出失败", "onError: "+code+","+error);
                }

                @Override
                public void onProgress(int progress, String status) {

                }
            });
        }
    }





    /**
     * 是否是登录状态码
     * @return
     */
    public boolean isLogin(){
        if(ChatClient.getInstance().isLoggedInBefore()){
            return true;
        }else {
            return false;
        }

    }

}
