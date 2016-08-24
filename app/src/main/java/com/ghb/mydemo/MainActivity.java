package com.ghb.mydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ghb.mydemo.gallery.GalleryActivity;
import com.ghb.mydemo.parallax.ParallaxActivity;

/**
 * 观察者模式
 * 视差特效
 * 3D Gallery
 * 二维码生成
 * SearchView
 * Gps定位
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setText("生成二维码");
        btn4.setOnClickListener(this);
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn1:
                startActivity(new Intent(this, ParallaxActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, SearchViewActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this, QRCodeActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, TabActivity.class));
                break;
        }
    }
}
