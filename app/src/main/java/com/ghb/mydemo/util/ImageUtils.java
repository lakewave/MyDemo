package com.ghb.mydemo.util;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.util.Log;

public class ImageUtils {

	private static Hashtable<Integer, SoftReference<Bitmap>> mCacheHashTable = new Hashtable<Integer, SoftReference<Bitmap>>();

	public static Bitmap getCacheBitmap(Resources res, int id) {

		// 内存中有直接返回
		SoftReference<Bitmap> softReference = mCacheHashTable.get(id);
		if (softReference != null) {
			Bitmap bm = softReference.get();
			if (bm != null) {
				Log.e("00", "内存中获取");
				return bm;
			}
		}

		// 内存没有获取并存储
		Bitmap invertedBitmap = getInvertedBitmap(res, id);
		mCacheHashTable.put(id, new SoftReference<Bitmap>(invertedBitmap));

		return invertedBitmap;
	}

	public static Bitmap getInvertedBitmap(Resources res, int id) {
		// 获取原图
		Bitmap source = BitmapFactory.decodeResource(res, id);

		// 生成倒影
		Matrix m = new Matrix();
		m.setScale(1f, -1f);// 垂直翻转
		Bitmap invertedBitmap = Bitmap.createBitmap(source, 0,
				source.getHeight() / 2, source.getWidth(),
				source.getHeight() / 2, m, false);

		// 合成 原图+倒影
		Bitmap result = Bitmap.createBitmap(source.getWidth(),
				(int) (source.getHeight() * 1.5 + 6), Config.ARGB_8888);

		Canvas canvas = new Canvas(result);// 生成画布
		// Paint paint = new Paint();
		// paint.setAntiAlias(true);
		canvas.drawBitmap(source, 0, 0, null);// 绘制原图
		canvas.drawBitmap(invertedBitmap, 0, source.getHeight() + 6, null);// 绘制倒影

		// 添加遮罩效果 画笔着色器 SetXfermode drawRect
		Paint paint = new Paint();
		// 线性渐变着色器
		LinearGradient shader = new LinearGradient(0, source.getHeight() + 6,
				0, result.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// 设置图像合成规则
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, source.getHeight() + 6, result.getWidth(),
				result.getHeight(), paint);
		return result;
	}
}
