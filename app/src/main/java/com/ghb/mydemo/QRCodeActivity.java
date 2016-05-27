package com.ghb.mydemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ghb.mydemo.util.FileUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class QRCodeActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button btn1,btn2;
    private ImageView iv1,iv2;
    private int QR_WIDTH = 200, QR_HEIGHT = 200;
    private static final int IMAGE_HALFWIDTH = 40;// 宽度值，影响中间图片大小

    private FileUtils fileUtils;
    private static final String QRNAME = "qrcode.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        fileUtils = new FileUtils(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn1:
                createQRImage("http://www.baidu.com");
                break;
            case R.id.btn2:
				Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.soyeon);
				try {
					Bitmap target = createLogoQRCode("http://www.tara-china.cn/", logo);
					iv2.setImageBitmap(target);
				} catch (WriterException e) {
					e.printStackTrace();
				}
                break;
        }
    }

    public void createQRImage(String str) {
        try {
            // 判断URL合法性
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(str,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            // 显示到一个ImageView上面
            iv1.setImageBitmap(bitmap);
            String saveName = fileUtils.savaBitmap(bitmap);
            Log.e("00", "saveName=="+saveName);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成带logo二维码图片
     */
    public Bitmap createLogoQRCode(String string, Bitmap mBitmap)
            throws WriterException {
        Matrix m = new Matrix();
        float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
        float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
        m.setScale(sx, sy);// 设置缩放信息
        // 将logo图片按martix设置的信息缩放
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                mBitmap.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();

        Hashtable hst = new Hashtable();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");// 设置字符编码
        BitMatrix matrix = writer.encode(string, BarcodeFormat.QR_CODE, 400, 400, hst);// 生成二维码矩阵信息
        int width = matrix.getWidth();// 矩阵高度
        int height = matrix.getHeight();// 矩阵宽度
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];// 定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
        for (int y = 0; y < height; y++) {// 从行开始迭代矩阵
            for (int x = 0; x < width; x++) {// 迭代列
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {// 该位置用于存放图片信息
                    // 记录图片每个像素信息
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                } else {
                    if (matrix.get(x, y)) {// 如果有黑块点，记录信息
                        pixels[y * width + x] = 0xff000000;// 记录黑块信息
                    }
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
