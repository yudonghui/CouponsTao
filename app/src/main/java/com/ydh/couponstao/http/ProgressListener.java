package com.ydh.couponstao.http;

/**
 * Created by ydh on 2021/9/3
 */
public interface ProgressListener {
    void onProgress(long progress, long total, boolean done);
}
