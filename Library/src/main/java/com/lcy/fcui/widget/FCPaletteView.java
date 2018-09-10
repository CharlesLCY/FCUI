package com.lcy.fcui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author FanCoder.LCY
 * @date 2018/9/6 13:48
 * @email 15708478830@163.com
 * @desc 自定义小画板
 **/
public class FCPaletteView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Paint mBitmapPaint;
    private Bitmap mBitmap;

    private Path mPath;
    private float mLastX, mLastY;//上次的坐标
    private Canvas mCanvas;

    public FCPaletteView(Context context) {
        super(context);
        initView();
    }

    public FCPaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FCPaletteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundColor(Color.WHITE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4f);

        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        //保存签名的画布
        //拿到控件的宽和高
        post(() -> {
            //获取PaintView的宽和高
            //由于橡皮擦使用的是 Color.TRANSPARENT ,不能使用RGB-565
            mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
            mCanvas = new Canvas(mBitmap);
            //抗锯齿
            mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint
                    .FILTER_BITMAP_FLAG));
            //背景色
            mCanvas.drawColor(Color.WHITE);
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //路径
                mPath = new Path();
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                if (dx >= 3 || dy >= 3) {//绘制的最小距离 3px
                    //利用二阶贝塞尔曲线，使绘制路径更加圆滑
                    mPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                }
                mCanvas.drawPath(mPath, mPaint);//将路径绘制在mBitmap上
                invalidate();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mPath.reset();
                mPath = null;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);//将mBitmap绘制在canvas上,最终的显示
            if (null != mPath) {//显示实时正在绘制的path轨迹
                canvas.drawPath(mPath, mPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSpec(widthMeasureSpec), measureSpec(heightMeasureSpec));
    }

    private int measureSpec(int measureSpec) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public void reset() {
        mPath.reset();
    }

    public void setPaintColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }
}
