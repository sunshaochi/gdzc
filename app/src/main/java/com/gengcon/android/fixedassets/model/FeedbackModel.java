package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.FeedbackRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class FeedbackModel extends BaseModel {

    ApiService.GetFeedback feedback = createService(ApiService.GetFeedback.class);

    public Observable<Bean> getFeedback(String feedback_content) {
        return feedback.getFeedback(new FeedbackRequest(feedback_content));
    }
}
