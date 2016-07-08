package com.oshorn.appfeedback.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.oshorn.appfeedback.FeedbackManager;
import com.oshorn.appfeedback.R;


public class FeedbackService extends Service {

    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private LinearLayout mLayout;
    private ImageButton mButton;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatview();
    }

    private void createFloatview(){
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mLayout = (LinearLayout)inflater.inflate(R.layout.float_layout, null);
        mButton = (ImageButton)mLayout.findViewById(R.id.float_image);

        mWindowManager.addView(mLayout, wmParams);
        mLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wmParams.x = (int) event.getRawX() - mButton.getMeasuredWidth() / 2;
                wmParams.y = (int) event.getRawY() - mButton.getMeasuredHeight() / 2 - 25;
                mWindowManager.updateViewLayout(mLayout, wmParams);
                return false;
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(FeedbackManager.getInstance().getActivity()).setTitle("标题")
                        .setMessage("简单消息框")
                        .setPositiveButton("确定", null).create();
                dialog.getWindow().setWindowAnimations(R.style.dialogAnimation);

                dialog.show();
            }
        });
    }


    @Override
    public void onDestroy() {
        mWindowManager.removeView(mLayout);
        super.onDestroy();
    }
}
