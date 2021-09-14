package npc.com.b2_nguyencongphuong;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.w3c.dom.Attr;

public class SecondaryDialView extends View {

    private int mSelectionCount = 4;
    private float mWidth;
    private float mHeight;
    private Paint mTextPaint;
    private Paint mDialPaint;
    private float mRadius;
    private int mActiveSelection;
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];
    private int mFanOnColor;
    private int mFanOffColor;

    private SecondaryDialView(Context context) {
        super(context);
        init(context, null);
    }

    public SecondaryDialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SecondaryDialView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        mFanOnColor = Color.CYAN;
        mFanOffColor = Color.GRAY;

        if (attributeSet != null) {
            TypedArray typedArray = getContext()
                    .obtainStyledAttributes(attributeSet, R.styleable.DialView, 0, 0);
            mFanOnColor = typedArray.getColor(R.styleable.DialView_fanOnColor, mFanOnColor);
            mFanOffColor = typedArray.getColor(R.styleable.DialView_fanOffColor, mFanOffColor);
            mSelectionCount = typedArray.getInt(R.styleable.DialView_selectionIndicators
                    , mSelectionCount);
            typedArray.recycle();
        }
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(mFanOffColor);

        mActiveSelection = 0;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mActiveSelection = (mActiveSelection + 1) % mSelectionCount;
                if (mActiveSelection > 0) {
                    mDialPaint.setColor(mFanOnColor);
                } else {
                    mDialPaint.setColor(mFanOffColor);
                }
                invalidate();
            }

        });

    }

    public void setSelectionCount(int count) {
        this.mSelectionCount = count;
        this.mActiveSelection = 0;
        mDialPaint.setColor(mFanOffColor);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        mWidth = width;
        mHeight = height;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint);

        final float labelRadius = mRadius + 20;
        StringBuffer label = mTempLabel;
        for (int i = 0; i < mSelectionCount; i++) {
            float[] xyData = computeXYForPosition(i, labelRadius, true);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
        }

        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYForPosition(mActiveSelection, markerRadius, false);
        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);
    }

    private float[] computeXYForPosition(final int pos, final float radius, boolean isLabel) {
        float[] result = mTempResult;
        Double startAngle;
        Double angle;
        if (mSelectionCount > 4) {
            startAngle = Math.PI * (3 / 2d);   // Angles are in radians.
            angle = startAngle + (pos * (Math.PI / mSelectionCount));
            result[0] = (float) (radius * Math.cos(angle * 2)) + (mWidth / 2);
            result[1] = (float) (radius * Math.sin(angle * 2)) + (mHeight / 2);
            if ((angle > Math.toRadians(360)) && isLabel) {
                result[1] += 20;
            }
        } else {
            startAngle = Math.PI * (9 / 8d);
            angle = startAngle + (pos * (Math.PI / mSelectionCount));
            result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
            result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        }
        return result;
    }
}
