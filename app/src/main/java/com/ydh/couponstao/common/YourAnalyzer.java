package com.ydh.couponstao.common;

import android.annotation.SuppressLint;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.common.InputImage;

/**
 * Created by ydh on 2022/10/28
 */
public class YourAnalyzer implements ImageAnalysis.Analyzer {
    private ListenerAnalyzer mListener;

    public YourAnalyzer(ListenerAnalyzer mListener) {
        this.mListener = mListener;
    }


    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        @SuppressLint("UnsafeExperimentalUsageError") Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            mListener.analyze(image);
        }
    }

    public interface ListenerAnalyzer {
        void analyze(InputImage image);
    }
}
