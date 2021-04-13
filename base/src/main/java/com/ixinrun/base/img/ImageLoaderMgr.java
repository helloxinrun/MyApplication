package com.ixinrun.base.img;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ixinrun.base.BaseApplication;

import java.security.MessageDigest;


/**
 * 描述：图片加载管理器
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public class ImageLoaderMgr {
    private static volatile ImageLoaderMgr mInstance;

    public static ImageLoaderMgr getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderMgr.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderMgr();
                }
            }
        }
        return mInstance.setBuilder(null);
    }

    private Builder mBuilder;

    private ImageLoaderMgr setBuilder(Builder builder) {
        this.mBuilder = builder;
        return this;
    }

    private Builder getBuilder() {
        return mBuilder;
    }

    /**
     * 构建条件
     *
     * @return
     */
    public Builder builder() {
        return new Builder();
    }

    /**
     * 开始加载
     *
     * @param url 图片地址
     * @param iv  展示控件
     */
    @SuppressLint("CheckResult")
    public void load(String url, ImageView iv) {
        Builder builder = getBuilder();
        RequestOptions options = new RequestOptions();

        float thumbnail = 0;
        if (builder != null) {
            if (builder.placeholder != 0) {
                options.placeholder(builder.placeholder);
            }
            if (builder.error != 0) {
                options.error(builder.error);
            }
            if (builder.width > 0 && builder.height > 0) {
                options.override(builder.width, builder.height);
            }
            if (builder.watermarkCallback != null) {
                options.transform(new BitmapTransformation() {
                    @Override
                    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                        return builder.watermarkCallback.creatWatermark(toTransform);
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });
            }

            //缩略值
            thumbnail = builder.thumbnail;
        }

        RequestBuilder rb = Glide.with(BaseApplication.getInstance())
                .load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(200));
        if (thumbnail > 0) {
            rb.thumbnail(thumbnail);
        }
        rb.into(iv);
    }

    public static final class Builder {
        private int placeholder;
        private int error;
        private int width;
        private int height;
        private float thumbnail;
        private WatermarkCallback watermarkCallback;

        /**
         * 设置过渡图
         *
         * @param placeholder
         * @return
         */
        public Builder placeholder(@DrawableRes int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /**
         * 设置错误图
         *
         * @param error
         * @return
         */
        public Builder error(@DrawableRes int error) {
            this.error = error;
            return this;
        }

        /**
         * 设置宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder override(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * 设置缩略图缩放值
         *
         * @param thumbnail
         * @return
         */
        public Builder thumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        /**
         * 水印回調
         *
         * @param callback
         * @return
         */
        public Builder watermark(WatermarkCallback callback) {
            this.watermarkCallback = callback;
            return this;
        }

        public ImageLoaderMgr build() {
            return ImageLoaderMgr.getInstance().setBuilder(this);
        }

        public interface WatermarkCallback {
            /**
             * 添加水印
             *
             * @param old
             * @return
             */
            Bitmap creatWatermark(Bitmap old);
        }
    }
}
