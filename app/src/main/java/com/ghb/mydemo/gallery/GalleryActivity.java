package com.ghb.mydemo.gallery;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.ghb.mydemo.MainActivity;
import com.ghb.mydemo.R;
import com.ghb.mydemo.util.ImageUtils;

public class GalleryActivity extends AppCompatActivity {

    /**测试缓存*/
    private static int[] images = {
            R.mipmap.p1,
            R.mipmap.p2,
            R.mipmap.p3,
            R.mipmap.p4,
            R.mipmap.p5,
            R.mipmap.p6,
            R.mipmap.p1,
            R.mipmap.p2,
            R.mipmap.p3,
            R.mipmap.p4,
            R.mipmap.p5,
            R.mipmap.p6,
            R.mipmap.p1,
            R.mipmap.p2,
            R.mipmap.p3,
            R.mipmap.p4,
            R.mipmap.p5,
            R.mipmap.p6
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        MyGallery myGallery = (MyGallery) findViewById(R.id.my_gallery);
        myGallery.setAdapter(new MyAdapter());
    }



    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv;
            if(convertView==null){
                iv = new ImageView(GalleryActivity.this);
            }else{
                iv = (ImageView) convertView;
            }

            Bitmap bitmap = ImageUtils.getCacheBitmap(getResources(), images[position]);
            iv.setImageBitmap(bitmap);
            iv.setLayoutParams(new Gallery.LayoutParams(360, 720));
//			iv.setBackgroundColor(Color.RED);
//			iv.setScaleType(ScaleType.CENTER_CROP);

            return iv;
        }

    }
}
