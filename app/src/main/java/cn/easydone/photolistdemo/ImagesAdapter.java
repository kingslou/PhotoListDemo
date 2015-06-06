package cn.easydone.photolistdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by liang on 15/6/5.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context context;
    private List<ImagesItem> mList;
    private LayoutInflater inflater;

    public ImagesAdapter(Context context, List<ImagesItem> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ImagesItem item = mList.get(position);
        String content = item.getContent();
        holder.tvContent.setText(content);
        String images = item.getImages();
        if (!TextUtils.isEmpty(images)) {
            String url;
//            int width = dp2px(70);
            String[] imgs = images.split(";");
            holder.flImages.setVisibility(View.VISIBLE);
            holder.flImages.removeAllViews();
            for (int i = 0; i < imgs.length; i++) {
                SimpleDraweeView view = (SimpleDraweeView) inflater.inflate(R.layout.images, holder.flImages, false);
                url = imgs[i];
                Log.i("url", url);
                view.setImageURI(Uri.parse(url));//Fresco加载图片
                /*ImageView view = new ImageView(context);
                ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(width, width);
                layoutParams.setMargins(0, 30, 20, 0);
                view.setLayoutParams(layoutParams);
//                Picasso.with(context).load(imgs[i]).skipMemoryCache().resize(width, width).centerInside().into(view);//Picasso加载图片
                Bitmap bitmap = getHttpBitmap(imgs[i]);//直接获取流加载图片
                view.setImageBitmap(bitmap);*/
                final int imgPosition = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ImageZoomActivity.class);
                        intent.putExtra("IMAGES", item);
                        intent.putExtra("CURRENT_IMG_POSITION", imgPosition);
                        context.startActivity(intent);
                    }
                });
                holder.flImages.addView(view);
            }
        } else {
            holder.flImages.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;
        public FlowLayout flImages;

        private ViewHolder(View rootView) {
            super(rootView);
            tvContent = (TextView) rootView.findViewById(R.id.tv_content);
            flImages = (FlowLayout) rootView.findViewById(R.id.images_flowlayout);
        }
    }

    private int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Bitmap getHttpBitmap(String url){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
