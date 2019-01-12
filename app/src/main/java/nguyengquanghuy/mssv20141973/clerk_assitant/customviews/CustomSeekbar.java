package nguyengquanghuy.mssv20141973.clerk_assitant.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import nguyengquanghuy.mssv20141973.clerk_assitant.R;

@SuppressLint("AppCompatCustomView")
public class CustomSeekbar extends SeekBar {

    int startX = 0;
    int endX = 0;
    private Context context;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint strokePaint = new Paint();
    private int startProgress;
    private int currentProgress;
    private boolean chose;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public CustomSeekbar(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    public CustomSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    public CustomSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setup() {
        chose = false;
        strokePaint.setStrokeWidth(6);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(context.getResources().getColor(R.color.color_seek_bar));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        try {
            canvas.save();
            super.onDraw(canvas);
            if (chose) {
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                if (endX != 0 && endX > startX) {
                    mCanvas.drawLine(startX + getPaddingLeft(), getHeight() / 2, endX + getPaddingLeft(), getHeight() / 2, strokePaint);
                }
                canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            }
            canvas.restore();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStartChooseProgress(int progress) {
        chose = true;
        setThumb(context.getResources().getDrawable(R.drawable.seekbar_drawable_thumb_chose));
        this.startProgress = progress;
        if (getMax() != 0) {
            float ratio = ((float) startProgress) / getMax();
            Log.e("setStartChoose: ", String.valueOf(ratio));
            startX = (int) (ratio * (getWidth() - 2 * getPaddingLeft()));
        }
        invalidate();
    }

    public void setCurrentProgress(int progress) {
        if (chose) {
            this.currentProgress = progress;
            if (getMax() != 0) {
                float ratio = ((float) currentProgress) / getMax();
                Log.e("setStartChoose: ", String.valueOf(ratio));
                endX = (int) (ratio * (getWidth() - 2 * getPaddingLeft()));
            }
            invalidate();
        }
    }

    public void setStopChooseProgress() {
        setThumb(context.getResources().getDrawable(R.drawable.seekbar_drawable_thumb_unchose));
        chose = false;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(
                Color.TRANSPARENT,
                PorterDuff.Mode.CLEAR);
    }
}
