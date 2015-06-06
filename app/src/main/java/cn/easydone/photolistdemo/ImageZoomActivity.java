package cn.easydone.photolistdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by liang on 15/6/5.
 */
public class ImageZoomActivity extends Activity {

    private int currentPosition;
    private List<String> imageList = new ArrayList<>();
    private TextView tvPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zoom);
        currentPosition = getIntent().getExtras().getInt("CURRENT_IMG_POSITION", 0);
        ImagesItem imagesItem = (ImagesItem) getIntent().getExtras().getSerializable("IMAGES");
        String[] images = imagesItem.getImages().split(";");

        for (String image : images) {
            imageList.add(image);
        }

        tvPage = (TextView) findViewById(R.id.tv_page);
        Button orginPic = (Button) findViewById(R.id.photo_bt_orgin);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOnPageChangeListener(pageChangeListener);
        ImagePageAdapter adapter = new ImagePageAdapter(imageList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentPosition);
        orginPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageZoomActivity.this, ImageOrginActivity.class);
                intent.putExtra("IMAGESURL", imageList.get(currentPosition));
                startActivity(intent);
            }
        });
    }



    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            currentPosition = arg0;
            tvPage.setText((currentPosition + 1) + "/" + imageList.size());
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        public void onPageScrollStateChanged(int arg0) {}
    };


    class ImagePageAdapter extends PagerAdapter {
        private static final int MAX_WIDTH = 500;
        private static final int MAX_HEIGHT = 500;
        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

        private List<String> imageList = new ArrayList<>();
        private ArrayList<PhotoView> mViews = new ArrayList<>();
        protected CustomProgressDialog mDialog;

        public ImagePageAdapter(List<String> imageList) {
            this.imageList = imageList;
            int size = imageList.size();
            for (int i = 0; i != size; i++) {
                final PhotoView iv = new PhotoView(ImageZoomActivity.this);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mViews.add(iv);
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (mViews.size() >= arg1 + 1) {
                ((ViewPager) arg0).removeView(mViews.get(arg1));
            }
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            final PhotoView photoView = mViews.get(arg1);
            photoView.setMaximumScale(2.5f);
            photoView.setMinimumScale(1f);
            photoView.setMediumScale(1.5f);
            photoView.setScale(1.0f, true);

            final String image = imageList.get(arg1);
            if (!TextUtils.isEmpty(image)) {

                if (mDialog == null) {
                    mDialog = CustomProgressDialog.createDialog(ImageZoomActivity.this, "");
                    mDialog.show();
                }
                Log.e("imageUrl", image);
                Picasso.with(ImageZoomActivity.this).load(image)
                        .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                        .resize(size, size)
                        .centerInside()
                        .into(photoView, new Callback() {
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
            }
            ((ViewPager) arg0).addView(photoView);

            return photoView;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }
    }
}
