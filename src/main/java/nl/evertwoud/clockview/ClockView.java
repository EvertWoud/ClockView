package evertwoud.nl.portfolio.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

import nl.evertwoud.clockview.R;

public class ClockView extends LinearLayout {
    float rotationHour = 0;
    float rotationMinute = 0;
    float rotationSecond = 0;

    View hour;

    View minute;

    View second;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.clock, this, true);

        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        second = findViewById(R.id.second);


        setupTime();
        startTimers();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(1, Paint.ANTI_ALIAS_FLAG));
        super.dispatchDraw(canvas);
    }

    private void setupTime() {
        Date currentTime = Calendar.getInstance().getTime();

        rotationSecond = currentTime.getSeconds() * 6;
        rotationMinute = currentTime.getMinutes() * 6;
        rotationHour = currentTime.getHours() * 15f;

        RotateAnimation rotateHour = new RotateAnimation(hour.getRotation(), rotationHour, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        RotateAnimation rotateMin = new RotateAnimation(minute.getRotation(), rotationMinute, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        RotateAnimation rotateSec = new RotateAnimation(second.getRotation(), rotationSecond, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);


        rotateHour.setInterpolator(new FastOutSlowInInterpolator());
        rotateMin.setInterpolator(new FastOutSlowInInterpolator());
        rotateSec.setInterpolator(new FastOutSlowInInterpolator());

        rotateHour.setDuration(1000);
        rotateMin.setDuration(1000);
        rotateSec.setDuration(1000);


        hour.startAnimation(rotateHour);
        minute.startAnimation(rotateMin);
        second.startAnimation(rotateSec);


    }

    private void startTimers() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                doHourTick();
                handler.postDelayed(this, 1000);
            }
        };

        final Handler handler2 = new Handler();
        final Runnable r2 = new Runnable() {
            public void run() {
                doMinuteTick();
                handler2.postDelayed(this, 1000);
            }
        };

        final Handler handler3 = new Handler();
        final Runnable r3 = new Runnable() {
            public void run() {
                doSecondTick();
                handler3.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
        handler2.postDelayed(r2, 1000);
        handler3.postDelayed(r3, 1000);
    }

    void doHourTick() {
        RotateAnimation rotate = new RotateAnimation(rotationHour, (rotationHour + 0.0041666666666667f), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new OvershootInterpolator());
        rotationHour = rotationHour + 0.0041666666666667f;
        hour.startAnimation(rotate);

    }

    void doMinuteTick() {
        RotateAnimation rotate = new RotateAnimation(rotationMinute, (rotationMinute + 0.1f), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new OvershootInterpolator());
        rotationMinute = rotationMinute + 0.1f;
        minute.startAnimation(rotate);

    }

    void doSecondTick() {
        RotateAnimation rotate = new RotateAnimation(rotationSecond, rotationSecond + 6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new OvershootInterpolator());
        rotationSecond = rotationSecond + 6;
        second.startAnimation(rotate);

    }


}
