package com.ixinrun.app_base.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;


/**
 * 描述：媒体库工具类
 * 只有让媒体库有必要更新的时候才使用，举例：一些缓存的图片不需要更新媒体库。
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    private static final String TAG = MediaScanner.class.getSimpleName();

    /**
     * 刷新媒体库
     *
     * @param context
     * @param file
     */
    public static void refresh(Context context, File file) {
        if (context == null || file == null) {
            return;
        }

        //如果图片不存在，删除媒体库中记录
        if (!file.exists()) {
            try {
                String filePath = file.getAbsolutePath();
                if (filePath.endsWith(".mp4")) {
                    context.getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            MediaStore.Audio.Media.DATA + "= \"" + filePath + "\"",
                            null);
                } else if (filePath.endsWith(".jpg") || filePath.endsWith(".png") || filePath.endsWith(".bmp")) {
                    context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            MediaStore.Audio.Media.DATA + "= \"" + filePath + "\"",
                            null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        //如果图片存在，更新媒体库
        new MediaScanner(context, file);
    }

    private File mFile;
    private MediaScannerConnection mMsc;

    private MediaScanner(Context context, File file) {
        this.mFile = file;
        this.mMsc = new MediaScannerConnection(context, this);
        mMsc.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMsc.scanFile(mFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMsc.disconnect();
    }
}
