package com.showtime.lp.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.utils.Constants;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class CameraPhotoActivity extends BaseActivity {

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {


    }

    @OnClick({R.id.text1, R.id.text2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                View view = View.inflate(CameraPhotoActivity.this, R.layout.dialog_camera_item, null);
                final MaterialDialog dialog = new MaterialDialog.Builder(CameraPhotoActivity.this).customView(view, false).show();
                LinearLayout camera_layout = (LinearLayout) view.findViewById(R.id.camera_layout);
                LinearLayout record_layout = (LinearLayout) view.findViewById(R.id.record_layout);
                LinearLayout photo_layout = (LinearLayout) view.findViewById(R.id.photo_layout);
                LinearLayout video_layout = (LinearLayout) view.findViewById(R.id.video_layout);
                LinearLayout cancel_layout = (LinearLayout) view.findViewById(R.id.cancel_layout);
                camera_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        path = Constants.PATH_PROJECT + "/" + System.currentTimeMillis() + ".jpg";
                        Uri uri = Uri.fromFile(new File(path));
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 21);
                    }
                });
                record_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                photo_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, 23);

                    }
                });
                video_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        if (Build.VERSION.SDK_INT < 19) {
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("video/*");
                        } else {
                            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("video/*");
                        }
                        startActivityForResult(intent, 24);
                    }
                });
                cancel_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.text2:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("Result---------", requestCode + "    " + resultCode + "    " + RESULT_OK + "   " + data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 21: // 拍照
                    if (!TextUtils.isEmpty(path)) {
                        Log.e("21-----", path + "");
                    } else {
                        Log.e("21-----222", path + "");
                    }

                    break;
                case 22: // 录制视频

                    break;
                case 23: // 选择照片
                    Uri originalUri = data.getData(); //获得所选内容的uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(originalUri, filePathColumn, null, null, null);
                    String picturePath;
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        Log.e("url-----11", originalUri.getPath() + "   " + picturePath + "    ");
                    } else {
                        picturePath = processDataFromAlbum(data);
                        Log.e("url-----22", picturePath);
                    }
                    break;
                case 24: // 选择视频

                    break;
            }

        } else {
            Log.e("error------", requestCode + "    " + resultCode + "    " + RESULT_OK + "   " + data);
        }
    }

    /**
     * 处理选择相册的数据
     *
     * @param data
     */
    public String processDataFromAlbum(Intent data) {
        Uri uri = data.getData();
        if (uri == null) return null;
        String path = null;
        if (uri.getScheme().equals("file")) {
            path = uri.getPath();
            return path;
        }
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = resolver.query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                path = cursor.getString(0);
            }
        } finally {
            if (null != cursor) cursor.close();
        }
        return path;
    }
}
