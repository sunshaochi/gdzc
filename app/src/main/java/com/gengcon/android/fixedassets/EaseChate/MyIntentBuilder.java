package com.gengcon.android.fixedassets.EaseChate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hyphenate.helpdesk.easeui.ui.BaseChatActivity;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.AgentIdentityInfo;
import com.hyphenate.helpdesk.model.QueueIdentityInfo;
import com.hyphenate.helpdesk.model.VisitorInfo;

public class MyIntentBuilder {

    private Context mContext;
    private Class<? extends Activity> mActivityClass;
    private String toChatUsername;
    private AgentIdentityInfo agentIdentityInfo;
    private QueueIdentityInfo queueIdentityInfo;
    private boolean showUserNick;
    private VisitorInfo visitorInfo;
    private String titleName;
    private Bundle bundle;

    public MyIntentBuilder(Context context) {
        this.mContext = context;
    }

    public MyIntentBuilder setTargetClass(Class<? extends Activity> targetClass) {
        this.mActivityClass = targetClass;
        return this;
    }

    public MyIntentBuilder setServiceIMNumber(String toChatUsername) {
        this.toChatUsername = toChatUsername;
        return this;
    }

    public MyIntentBuilder setScheduleAgent(AgentIdentityInfo info) {
        agentIdentityInfo = info;
        return this;
    }

    public MyIntentBuilder setScheduleQueue(QueueIdentityInfo info) {
        this.queueIdentityInfo = info;
        return this;
    }

    public MyIntentBuilder setVisitorInfo(VisitorInfo visitorInfo) {
        this.visitorInfo = visitorInfo;
        return this;
    }

    public MyIntentBuilder setShowUserNick(boolean showNick) {
        this.showUserNick = showNick;
        return this;
    }

    public MyIntentBuilder setTitleName(String titleName) {
        this.titleName = titleName;
        return this;
    }

    public MyIntentBuilder setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public Intent build() {
        if (mActivityClass == null) {
            mActivityClass = MyChatActivity.class;
        }
        Intent intent = new Intent(mContext, mActivityClass);
        if (!TextUtils.isEmpty(toChatUsername)) {
            intent.putExtra(Config.EXTRA_SERVICE_IM_NUMBER, toChatUsername);
        }
        if (visitorInfo != null) {
            intent.putExtra(Config.EXTRA_VISITOR_INFO, visitorInfo);
        }

        if (agentIdentityInfo != null) {
            intent.putExtra(Config.EXTRA_AGENT_INFO, agentIdentityInfo);
        }

        if (titleName != null) {
            intent.putExtra(Config.EXTRA_TITLE_NAME, titleName);
        }

        if (queueIdentityInfo != null) {
            intent.putExtra(Config.EXTRA_QUEUE_INFO, queueIdentityInfo);
        }
        intent.putExtra(Config.EXTRA_SHOW_NICK, showUserNick);
        if (bundle != null) {
            intent.putExtra(Config.EXTRA_BUNDLE, bundle);
        }
        return intent;
    }


}
