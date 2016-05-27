package com.ghb.mydemo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
	/**
	 * sd卡的根目录
	 */
	private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
	/**
	 * 手机的缓存根目录
	 */
	private static String mDataRootPath = null;
	/**
	 * 保存Image的目录名
	 */
	private final static String FOLDER_NAME = "/code99";

	public FileUtils(Context context) {
		mDataRootPath = context.getCacheDir().getPath();
	}

	/**
	 * 获取储存Image的目录
	 * 注意读写权限问题
	 * @return
	 */
	public String getStorageDirectory() {
		String str = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
		File file = new File(str);
		if(!file.exists()){
			boolean create = file.mkdirs();
		}
		return str;
	}

	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 * 
	 * @param bitmap
	 * @throws IOException
	 */
	public void savaBitmap(String url, Bitmap bitmap) throws IOException {
		if (bitmap == null) {
			return;
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		File file = new File(path + File.separator + getFileName(url));
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
	}
	
	/**
	 * 保存并返回存储路径
	 * @param bitmap
	 * @return
	 */
	public String savaBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return "bitmap is null";
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		File file = new File(path + File.separator + "qrcode.jpg");
		FileOutputStream fos = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fos.close();// 注意读写权限问题
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file.getPath();
	}
	
	public String savePhotoToSDCard(Bitmap photoBitmap) {
		if (photoBitmap == null) {
			return null;
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		File photoFile = new File(path + File.separator + "qrcode.png");

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(photoFile);
			if (photoBitmap.compress(CompressFormat.PNG, 100,
					fileOutputStream)) {
				fileOutputStream.flush();
				// fileOutputStream.close();
			}
		} catch (FileNotFoundException e) {
			photoFile.delete();
			e.printStackTrace();
		} catch (IOException e) {
			photoFile.delete();
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return photoFile.getPath();
	}

	/**
	 * 从手机或者sd卡获取Bitmap
	 * 
	 * @return
	 */
	public Bitmap getBitmap(String url) {
		return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + getFileName(url));
	}

	/**
	 * 判断文件是否存在，网络路径
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExists(String fileName) {
		return new File(getStorageDirectory() + File.separator + getFileName(fileName)).exists();
	}
	
	/**
	 * 判断本地文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isLocalFileExists(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName).exists();
	}

	/**
	 * 获取文件的大小
	 * 
	 * @return
	 */
	public long getFileSize(String url) {
		return new File(getStorageDirectory() + File.separator + getFileName(url)).length();
	}

	/**
	 * 删除SD卡或者手机的缓存图片和目录
	 */
	public void deleteFile() {
		File dirFile = new File(getStorageDirectory());
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}

		dirFile.delete();
	}

	/**
	 * 
	 * @Title: getFileName
	 * @说 明: 根据url截取文件名
	 * @参 数: @param url
	 * @参 数: @return
	 * @return String 返回类型
	 * @throws
	 */
	public String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

}
