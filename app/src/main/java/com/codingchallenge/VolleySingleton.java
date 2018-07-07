package com.codingchallenge;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    private static VolleySingleton mInstance = null;

    public VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null)
            mInstance = new VolleySingleton(context);
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }
}