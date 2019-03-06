package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.ApprovalListBean;

public interface ApprovalView extends Iview {
    void showApproval(ApprovalListBean approvalListBean);
    void showMoreApproval(ApprovalListBean approvalListBean);
}
