package cn.easydone.photolistdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liang on 15/6/6.
 */
public class ImageOrginActivity extends Activity {

    protected CustomProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgin_image);
        final PhotoView photoView = (PhotoView) findViewById(R.id.orgin_image);

        final String imageUrl = getIntent().getExtras().getString("IMAGESURL");
        if (!TextUtils.isEmpty(imageUrl)) {
            photoView.setMaximumScale(2.5f);
            photoView.setMinimumScale(1f);
            photoView.setMediumScale(1.5f);
            photoView.setScale(1.0f, true);

            if (mDialog == null) {
                mDialog = CustomProgressDialog.createDialog(ImageOrginActivity.this, "");
                mDialog.show();
            }
            Log.e("imageUrl", imageUrl);
            Picasso.with(ImageOrginActivity.this).load(imageUrl).into(photoView, new Callback() {
                @Override
                public void onSuccess() {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onError() {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });

            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final String[] strings = new String[]{"保存到手机"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(ImageOrginActivity.this);
                    builder.setItems(strings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            photoView.buildDrawingCache();
                            Bitmap bitmap = photoView.getDrawingCache();
                            savePhoto(bitmap, getCurrentTime() + ".jpg");
                            Toast.makeText(ImageOrginActivity.this, "图片保存成功", Toast.LENGTH_SHORT).show();
                        }
                    }).show();

                    return false;
                }
            });
        }
    }

    private String savePhoto(Bitmap bmp, String fileName) {

        File appDir = new File(Environment.getExternalStorageDirectory(), "photolistdemo"); //创建文件夹
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaScannerConnection.scanFile(ImageOrginActivity.this,
                    new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appDir.toString();
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
