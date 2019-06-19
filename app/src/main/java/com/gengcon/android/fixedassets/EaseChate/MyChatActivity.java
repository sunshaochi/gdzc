package com.gengcon.android.fixedassets.EaseChate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;

import com.gengcon.android.fixedassets.R;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.Config;

public class MyChatActivity extends FragmentActivity {
    protected MyChatFragment chatFragment = null;
    protected String toChatUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //http://stackoverflow.com/questions/4341600/how-to-prevent-multiple-instances-of-an-activity-when-it-is-launched-with-differ/
        //理论上应该放在launcher activity,放在基类中所有集成此库的app都可以避免此问题
//        if(!isTaskRoot()){
//            Intent intent = getIntent();
//            String action = intent.getAction();
//            if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)){
//                finish();
//                return;
//            }
//        }

        setContentView(R.layout.hd_activity_chat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //IM服务号
        assert bundle != null;
        toChatUsername = bundle.getString(Config.EXTRA_SERVICE_IM_NUMBER);
        chatFragment = new MyChatFragment();
        // 传入参数
        chatFragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        ChatClient.getInstance().chatManager().bindChat(toChatUsername);
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(Config.EXTRA_SERVICE_IM_NUMBER);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示
        UIProvider.getInstance().getNotifier().reset();
    }

    /**
     * 通过xml查找相应的ID，通用方法
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }


}
