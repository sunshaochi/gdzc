package com.gengcon.android.fixedassets.module.base;



import com.gengcon.android.fixedassets.common.module.htttp.RxService;

public abstract class BaseModel implements Imodel {

    public <T> T createService(final Class<T> clazz) {
        validateServiceInterface(clazz);
        return (T) RxService.RETROFIT.createRetrofit().create(clazz);
    }

    public <T> void validateServiceInterface(Class<T> service) {
        if (service == null) {
            //AppToast.ShowToast("服务接口不能为空！");
        }
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

}
