package com.gengcon.android.fixedassets.module.base.loader;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.gengcon.android.fixedassets.R;

import cn.finalteam.galleryfinal.widget.GFImageView;

public class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        RequestOptions options = new RequestOptions()
        .placeholder(defaultDrawable).error(defaultDrawable).override(width, height).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
        Glide.with(activity)
                .load("file://" + path)
//                .placeholder(defaultDrawable)
//                .error(defaultDrawable)
//                .override(width, height)
//                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
//                .skipMemoryCache(true)
                //.centerCrop()
                .apply(options)
                .into(new ImageViewTarget<Drawable>(imageView) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        imageView.setImageDrawable(resource);
                    }

//                    @Override
//                    protected void setResource(GlideDrawable resource) {
//                        imageView.setImageDrawable(resource);
//                    }

                    @Override
                    public void setRequest(Request request) {
                        imageView.setTag(R.id.adapter_item_tag_key, request);
                    }

                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {

    }
}
