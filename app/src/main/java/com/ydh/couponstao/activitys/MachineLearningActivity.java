package com.ydh.couponstao.activitys;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.LogUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by ydh on 2022/10/28
 * https://developers.google.com/ml-kit/vision/text-recognition/v2/android
 * CameraX的用法
 * https://developer.android.com/codelabs/camerax-getting-started#0
 */
public class MachineLearningActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;
    private TextRecognizer recognizer;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_learning);
        unBind = ButterKnife.bind(this);
        requestPermission(new String[]{
                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        recognizer = TextRecognition.getClient(new ChineseTextRecognizerOptions.Builder().build());
    }


    @Override
    public void permissonExcute(int requestCode) {
    }

    @Override
    public void permissonCancel() {

    }

    public void openPhoto(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    111);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, 111);
        }
    }

    public void openCamera(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);// 照相机拍照
        // 需要说明一下，以下操作使用照相机拍照，
        // 拍照后的图片会存放在相册中的,这里使用的这种方式有一个好处就是获取的图片是拍照后的原图，
        // 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
        ContentValues values = new ContentValues();
        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, 112);

    }

    private void resultHandle(Text result) {
        String resultText = result.getText();
        tvResult.setText(resultText);
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                    for (Text.Symbol symbol : element.getSymbols()) {
                        String symbolText = symbol.getText();
                        Point[] symbolCornerPoints = symbol.getCornerPoints();
                        Rect symbolFrame = symbol.getBoundingBox();
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 111 && resultCode == RESULT_OK) {
            Uri uri = intent.getData();
            InputImage image;
            try {
                image = InputImage.fromFilePath(mContext, uri);
                activityResult(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 112 && resultCode == RESULT_OK) {
            if (photoUri != null) {
                InputImage image;
                try {
                    image = InputImage.fromFilePath(mContext, photoUri);
                    activityResult(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private void activityResult(InputImage image) {
        recognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        resultHandle(visionText);
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                LogUtils.e(e.getMessage());
                            }
                        });
    }

}