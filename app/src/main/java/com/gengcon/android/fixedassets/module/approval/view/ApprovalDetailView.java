package com.gengcon.android.fixedassets.module.approval.view;

import com.gengcon.android.fixedassets.module.base.Iview;
import com.gengcon.android.fixedassets.bean.result.ApprovalDetailBean;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;


public interface ApprovalDetailView extends Iview {

    void showApprovalDetail(ApprovalDetailBean approvalDetail);

    void showApprovalMoreDetail(ApprovalDetailBean approvalDetail);

    void showHeadDetail(ApprovalHeadDetail headDetail);

    void agreeApproval();

    void contractExpire();

}
