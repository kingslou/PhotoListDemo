package cn.easydone.photolistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        List<ImagesItem> imagesItemList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_images_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        for (int i = 0; i < 8; i++) {
            String image = "http://7x2wvb.com1.z0.glb.clouddn.com/20150105133530.jpg";
            list.add(image);
        }

        for (int i = 0; i < 10; i++) {
            ImagesItem imagesItem = new ImagesItem();
            imagesItem.setContent("第" + i + "个条目");
            imagesItem.setImages(convertStrList2String(list));
            imagesItemList.add(imagesItem);
        }

        ImagesAdapter imagesAdapter = new ImagesAdapter(this, imagesItemList);
        recyclerView.setAdapter(imagesAdapter);
    }

    private String convertStrList2String(List<String> list) {
        String string = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                string += list.get(i) + ";";
            } else {
                string += list.get(i);
            }
        }
        Log.i("string", string);
        return string;
    }

}
