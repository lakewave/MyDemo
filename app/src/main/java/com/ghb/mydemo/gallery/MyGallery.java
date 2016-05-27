package com.ghb.mydemo.gallery;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 1.旋转（离开屏幕中间开始旋转） 2.透明度（离开屏幕中间开始变暗） 3.缩放（离开屏幕中间开始缩小） 屏幕中心x坐标，控件是匹配父窗体 图片中心x坐标
 * 最大旋转角度：50^ 当前旋转角度： (centerX - itemCenterX) / itemWidth * 50
 * 
 * ps:关闭硬件加速功能
 */
public class MyGallery extends Gallery {

	private int centerX = 0;
	private Camera camera;
	private static final int ANGLE = 50;
	private int posSelected;

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 启用getChildStaticTransformation被调用
		setStaticTransformationsEnabled(true);
		camera = new Camera();
	}

	/**
	 * 当控件的宽高改变时回调此方法，初次计算出控件宽高也会触发此方法
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = getCenterX();
	}

	/**
	 * 返回gallery的item的图形变幻效果 Transformation 指定当前item的变幻效果
	 */
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		int itemCenterX = getItemCenterX(child);
		int rotateAngle = 0;

		// 如果当前View的中心点x坐标!=控件自身中心点x坐标，表示当前item非选中状态，需要旋转
		if (itemCenterX != centerX) {
			// 当前旋转角度 = (centerX - itemCenterX) / itemWidth * 50
			// gallery中心点 - 图片中心点 = 差值
			int diff = centerX - itemCenterX;

			// 差值 / 图片的宽度 = 比值
			float scale = (float) diff / (float) child.getWidth();

			// 比值 * 最大旋转角度 = 最终的旋转角度
			rotateAngle = (int) (scale * ANGLE);

			// rotateAngle = (int) ((centerX - itemCenterX)
			// / (float) child.getWidth() * ANGLE);
			if (Math.abs(rotateAngle) > ANGLE) {
				// 校正角度范围
				rotateAngle = rotateAngle > 0 ? ANGLE : -ANGLE;
			}
		}
		// if (child.isSelected()) {
		// posSelected = getPositionForView(child);
		// } else {
		// int posCurrent = getPositionForView(child);
		// rotateAngle = posCurrent < posSelected ? ANGLE : -ANGLE;
		// }

		// 设置变幻效果前，需要清除上一个item的变幻效果
		t.clear();
		// 设置变幻效果类型为：矩阵类型
		t.setTransformationType(Transformation.TYPE_MATRIX);

		int pos = getPositionForView(child);
		Log.e("00", "pos==" + pos);
		Log.e("00", "-----------------------");

		startTransform((ImageView) child, rotateAngle, t);

		return true;
	}

	/**
	 * 执行转换
	 */
	private void startTransform(ImageView view, int angle, Transformation t) {
		camera.save();// 保存状态
		int absAngle = Math.abs(angle);

		// 缩放（中间图片 > 两边图片）
		// 100f -> -210 + absAngle * 2，差值范围在200左右效果较好
		 camera.translate(0, 0, 100f);// 给摄像机定位
		int zoom = -250 + (absAngle * 2);
		camera.translate(0, 0, zoom);
//		camera.translate(0, 0, -100f);
//		int zoom = 150 + (absAngle * 2);
//		camera.translate(0, 0, absAngle * 2);
		int pos = getPositionForView(view);
		Log.e("00", "pos==" + pos);
		Log.e("00", "zoom==" + absAngle * 3);
		Log.e("00", "-----------------------");

		// 透明度
		int alpha = (int) (255 - absAngle * 2.5f);
		view.setAlpha(alpha);// 0 ~ 255

		// 旋转
		camera.rotateY(angle);

		// 添加变幻效果
		Matrix matrix = t.getMatrix();
		camera.getMatrix(matrix);// camera会把变幻效果转换成矩阵添加到Transformation的matrix对象

		// 矩阵
		/*
		 * 高度写错，导致图片位置顶部对齐 matrix.preTranslate(-view.getWidth() / 2,
		 * -view.getHeight()); matrix.postTranslate(view.getWidth() / 2,
		 * view.getHeight());
		 */
		matrix.preTranslate(-view.getWidth() / 2, -view.getHeight() / 2);
		matrix.postTranslate(view.getWidth() / 2, view.getHeight() / 2);

		camera.restore();// 恢复到之前保存的状态
	}

	/**
	 * 获取控件中心x坐标
	 */
	private int getCenterX() {
		return this.getWidth() / 2;
	}

	/**
	 * 获取item中心x坐标
	 */
	private int getItemCenterX(View view) {
		// 图片宽度一半+图片在父控件左边的位置
		return view.getWidth() / 2 + view.getLeft();
	}

}
