package com.ghb.mydemo.observer;

import java.util.Observable;
import java.util.Observer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ghb.mydemo.MyApplication;
import com.ghb.mydemo.R;

public class UserInfoActivity extends Activity implements Observer {
	private ImageView iv_head;
	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		iv_head = (ImageView) findViewById(R.id.iv_head);
		btn = (Button) findViewById(R.id.btn);
		MyApplication.user.addObserver(this);
		int headId = MyApplication.user.getHeadId();
		iv_head.setImageResource(headId);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				MyApplication.user.setHeadId(R.drawable.sun);
				startActivity(new Intent(UserInfoActivity.this,UserInfoActivity2.class));
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.user.deleteObserver(this);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int headId = MyApplication.user.getHeadId();
		iv_head.setImageResource(headId);
	}

}
