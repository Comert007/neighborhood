package com.coder.neighborhood;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;

import com.coder.neighborhood.config.AppConfig;
import com.coder.neighborhood.mvp.WWApplication;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qiniu.pili.droid.streaming.StreamingEnv;

import ww.com.core.Debug;
import ww.com.core.utils.ACache;
import ww.com.http.OkHttpRequest;

/**
 * @author feng
 * @Date 2017/12/21.
 */

public class BaseApplication extends WWApplication {

    private static BaseApplication instance;

    private ACache cache;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        cache = ACache.get(this);
        boolean debug = BuildConfig.DEBUG;

        Debug.setDebug(debug);
        Debug.setTag("Neighbor");
        initImageLoader(getApplicationContext());
        initEMOptions();
        initStreaming();
        OkHttpRequest.setLogging(AppConfig.DEBUG);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCache(DefaultConfigurationFactory.createDiscCache(context, new
                        Md5FileNameGenerator(), 0, 100))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(150)
                .build();
        ImageLoader.getInstance().init(config);
    }
    public static DisplayImageOptions getDisplayImageOptions(int drawableRes) {
        return getDisplayImageOptions(drawableRes, drawableRes, drawableRes);
    }
    public static DisplayImageOptions getDisplayImageOptions(int onLoading,
                                                             int emptyUri, int onFail) {
        return getDisplayImageBuilder(onLoading, emptyUri, onFail).build();
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int onLoading, int emptyUri, int onFail) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .showImageOnLoading(onLoading).showImageForEmptyUri(emptyUri)
                .showImageOnFail(onFail).build();
        return builder;
    }

    private void initEMOptions(){
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAutoLogin(true);
        options.setAcceptInvitationAlways(false);
        options.setDeleteMessagesAsExitGroup(false);
        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        EaseUI.getInstance().init(getApplicationContext(), options);
    }

    /**
     * 七牛直播
     */
    private void initStreaming(){
        StreamingEnv.init(getApplicationContext());
    }
}
