package com.gengcon.android.fixedassets.module.addasset.view;

import com.gengcon.android.fixedassets.bean.Area;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.AddAssetCustom;
import com.gengcon.android.fixedassets.bean.result.AddAssetList;
import com.gengcon.android.fixedassets.module.base.Iview;

import java.util.List;

public interface AddAssetListView extends Iview {

    void showAddAssetList(AddAssetList addAssetList);

    void initUsers(List<User> user);

    void showAddAssetCustom(List<AddAssetCustom> assetCustom);

    void showAssetCode(String assetCode);

    void addAsset();

    void contractExpire();

    void initArea(List<Area> areas);
}
