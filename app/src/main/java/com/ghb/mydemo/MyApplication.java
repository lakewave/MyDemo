package com.ghb.mydemo;

import android.app.Application;

import com.ghb.mydemo.observer.User;


public class MyApplication extends Application {
	public static User user;
	
	@Override
	public void onCreate() {
		super.onCreate();
		user = new User();
		user.setName("Soyeon Park");
		user.setHeadId(R.mipmap.ic_launcher);
	}

}
