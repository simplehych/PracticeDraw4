package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Camera camera = new Camera();
    Matrix matrix = new Matrix();

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center1X = point1.x + bitmap.getWidth() / 2;
        int center1Y = point1.y + bitmap.getHeight() / 2;
        int center2X = point2.x + bitmap.getWidth() / 2;
        int center2Y = point2.y + bitmap.getHeight() / 2;

        canvas.save();
        camera.save();
        matrix.reset();
        camera.rotateX(30);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-center1X, -center1Y);
        matrix.postTranslate(center1X, center1Y);
        canvas.concat(matrix);

        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        //保存Camera的状态
        camera.save();
        //旋转Camera的三维空间
        camera.rotateY(30);
        //旋转之后把绘制内容移动回来
        canvas.translate(center2X, center2Y);
        //把旋转投影到Canvas
        camera.applyToCanvas(canvas);
        //旋转之前把绘制内容移动到轴心(原点)
        canvas.translate(-center2X, -center2Y);
        //恢复Camera的状态
        camera.restore();
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
