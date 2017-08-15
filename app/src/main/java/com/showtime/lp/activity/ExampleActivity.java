package com.showtime.lp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.utils.BitmapUtils;
import com.showtime.lp.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class ExampleActivity extends BaseActivity {

    @BindView(R.id.signature_pad)
    SignaturePad signaturePad;
    @BindView(R.id.clear_button)
    Button clearButton;
    @BindView(R.id.save_button)
    Button saveButton;
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
//                Toast.makeText(ExampleActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });
    }

    @OnClick({R.id.clear_button, R.id.save_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_button:
                signaturePad.clear();
                break;
            case R.id.save_button:
//                Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                Bitmap signatureBitmap = signaturePad.getTransparentSignatureBitmap();

                image.setImageBitmap(signatureBitmap);

                BitmapUtils.savePicture(signatureBitmap, "signature_image.png");

//                if (addJpgSignatureToGallery(signatureBitmap)) {
//                    Toast.makeText(ExampleActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ExampleActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
//                }

//                if (addSvgSignatureToGallery(signaturePad.getSignatureSvg())) {
//                    Toast.makeText(ExampleActivity.this, "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ExampleActivity.this, "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);
        return sourceImg;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Bitmap newBitmap = getTransparentBitmap(bitmap, 30);

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);


        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 60, stream);
        stream.close();
    }


    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
//            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_123.png", System.currentTimeMillis()));
            File mediaFile = new File(Constants.PATH_PROJECT_IMAGE + File.separator + "signature_img.png");
            if (mediaFile.exists()) { // 如果包含此张图片就删除
                mediaFile.delete();
            }
            File dirFile = new File(Constants.PATH_PROJECT_IMAGE);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            saveBitmapToJPG(signature, mediaFile);
//            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
