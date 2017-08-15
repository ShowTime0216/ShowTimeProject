package com.showtime.lp.activity;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devspark.appmsg.AppMsg;
import com.showtime.lp.R;
import com.showtime.lp.adapter.ExampleAdapter;
import com.showtime.lp.base.BaseKjActivity;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.widget.KJScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.MediaRecorderConfig;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * 作者:liupeng
 * 16/8/24 10:36
 * 注释:
 */
public class ProjectActivity extends BaseKjActivity {
    @BindView(id = R.id.textView, click = true)
    private TextView textView;
    @BindView(id = R.id.textView1, click = true)
    private TextView textView1;
    @BindView(id = R.id.textView2, click = true)
    private TextView textView2;
    @BindView(id = R.id.textView3, click = true)
    private TextView textView3;
    @BindView(id = R.id.textView4, click = true)
    private TextView textView4;
    @BindView(id = R.id.textView5, click = true)
    private TextView textView5;
    @BindView(id = R.id.textView6, click = true)
    private TextView textView6;
    @BindView(id = R.id.textView7, click = true)
    private TextView textView7;
    @BindView(id = R.id.kjScrollView)
    private KJScrollView kjScrollView;
    private Context context;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project);
//        context = ProjectActivity.this;
//        Log.e("onCreate", "onCreate------");
//
////        textView = (TextView) findViewById(R.id.textView);
////        textView.setOnClickListener(this);
//        textView1 = (TextView) findViewById(R.id.textView1);
//        textView1.setOnClickListener(this);
//        textView2 = (TextView) findViewById(R.id.textView2);
//        textView2.setOnClickListener(this);
//        textView3 = (TextView) findViewById(R.id.textView3);
//        textView3.setOnClickListener(this);
//        textView4 = (TextView) findViewById(R.id.textView4);
//        textView4.setOnClickListener(this);
//        textView5 = (TextView) findViewById(R.id.textView5);
//        textView5.setOnClickListener(this);
//
//    }

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_project);
        context = ProjectActivity.this;
        Log.e("KJ---", "-111111111");

        if (getIntent().getStringExtra(MediaRecorderActivity.VIDEO_URI) != null) {
            String videoUri = getIntent().getStringExtra(MediaRecorderActivity.VIDEO_URI);
            String videoScreenshot = getIntent().getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
            Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
//        iv_video_screenshot.setImageBitmap(bitmap);
//        et_send_content.setHint("您视频地址为:"+videoUri);
            Toast.makeText(ProjectActivity.this, videoUri, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        Log.e("KJ---", "-222222222");

    }

    private void KJ() {

        KJBitmap kjb = new KJBitmap();
        kjb.display(textView, "图片所在位置"); // 加载本地SD卡图片
        kjb.display(textView, "网络图片地址"); // 加载网络图片
        kjb.display(textView, "图片地址", 100, 100); // 加载固定大小的图片
        kjb.saveImage(context, "要保存的图片地址", "保存的本地路径");
        kjb.display(textView, "图片地址", new BitmapCallBack() { // 添加动画效果
            @Override
            public void onPreLoad() {
                super.onPreLoad();
            }

            @Override
            public void onSuccess(Bitmap bitmap) {
                super.onSuccess(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onDoHttp() {
                super.onDoHttp();
            }
        });


    }

    private List<String> list() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            list.add("测试数据 " + i);
        }
        return list;
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.textView:
                Log.e("KJ---", "-3333333");
                MultiImageSelector.create(context)
                        .count(12)
                        .showCamera(true)
                        .start(ProjectActivity.this, 100);
                break;
            case R.id.textView1:
                AppMsg.makeText(this, "什么！！！！！！", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.CENTER).show();
                View view = View.inflate(context, R.layout.dialog_exapple_list, null);
                ListView listView = (ListView) view.findViewById(R.id.listView);
                Button btn = (Button) view.findViewById(R.id.btn);
                final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(view, false).show();
                ExampleAdapter exampleAdapter = new ExampleAdapter(context, list());
                listView.setAdapter(exampleAdapter);
                break;
            case R.id.textView2:
                AppMsg.makeText(this, "什么！！！！！！", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.blue_00)).setLayoutGravity(Gravity.BOTTOM).show();
                try {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("video/*;image/*");
//        getAlbum.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    Toast.makeText(context, "选择相册异常", Toast.LENGTH_SHORT).show();
                    Log.e("e------", e + "");
                }
                break;
            case R.id.textView3:
                String str = "abcdefg";
                if (str.contains("m")) {
                    AppMsg.makeText(this, "有C!!!", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.CENTER).show();
                } else {
                    AppMsg.makeText(this, "没有!!!", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.CENTER).show();
                }

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 30);
                Log.e("c-------", c.get(Calendar.HOUR_OF_DAY) + "   " + c.get(Calendar.MINUTE));

                intent = new Intent(context, ExampleActivity.class);
                startActivity(intent);

                break;
            case R.id.textView4:
//                intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 21);
                intent = new Intent(context, QNPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.textView5:
                // 录制
                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                        .doH264Compress(new AutoVBRMode()
                        )
                        .setMediaBitrateConfig(new AutoVBRMode()
                        )
                        .smallVideoWidth(480)
                        .smallVideoHeight(360)
                        .recordTimeMax(6 * 1000)
                        .recordTimeMin((1 * 1000))
                        .maxFrameRate(20)
                        .captureThumbnailsTime(1)
                        .build();
                MediaRecorderActivity.goSmallVideoRecorder(this, ProjectActivity.class.getName(), config);

//                // 选择本地视频压缩
//                LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
//                final LocalMediaConfig config = buidler
//                        .setVideoPath(path)
//                        .captureThumbnailsTime(1)
//                        .doH264Compress(new AutoVBRMode())
//                        .setFramerate(15)
//                        .setScale(1.0f)
//                        .build();
//                OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
                break;
            case R.id.textView6:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 22);
                break;
            case R.id.textView7:
//                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                // 只查询jpeg和png的图片
//                Cursor mCursor = getContentResolver().query(mImageUri, null,
//                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
//                        new String[]{"image/jpeg", "image/png"},
//                        MediaStore.Images.Media.DATE_MODIFIED);

//                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("video/*;image/*");
                intent.setType("image/*;video/*");
                startActivityForResult(intent, 23);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            if (data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).size() > 0) {
//                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                Log.e("path", path + "---" + requestCode + "----" + resultCode);
//            }
//        }


        Log.e("data-----", data + "   " + requestCode + "     " + resultCode + "   " + RESULT_OK);
        if (data != null) {
            Uri selectedImage = data.getData();
            if (requestCode == 21 && resultCode == RESULT_OK) {
                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.e("url-----", selectedImage + "   " + picturePath + "   " + filePathColumn);
            } else if (requestCode == 22 && resultCode == RESULT_OK) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.e("url-----", selectedImage + "   " + picturePath + "   " + filePathColumn);
            } else if (requestCode == 23 && resultCode == RESULT_OK) {

                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                Cursor cursor = ProjectActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String dstDir = sdcardPath + File.separator + "showTime";
                    File dstDirFile = new File(dstDir);
                    if (!dstDirFile.exists()) {
                        dstDirFile.mkdir();
                    }
                    Log.e("url-----", selectedImage.getPath() + "   " + picturePath + "    " + dstDir);
                } else {
                    String path = processDataFromAlbum(data);
                    Log.e("url-----", path);
                }
            }

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
