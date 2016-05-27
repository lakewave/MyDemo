package com.ghb.mydemo.parallax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ghb.mydemo.R;
import com.ghb.mydemo.util.Cheeses;

/**
 * 视差特效展示
 */
public class ParallaxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);

        final ParallaxListView parallaxListView = (ParallaxListView) findViewById(R.id.lv);

        final View headerView = View.inflate(this, R.layout.header_parallax, null);
        final ImageView iv = (ImageView) headerView.findViewById(R.id.iv);
        parallaxListView.addHeaderView(headerView);

        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 当布局填充结束之后, 此方法会被调用
                parallaxListView.setParallaxImage(iv);
                headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        parallaxListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Cheeses.NAMES));

    }
}
