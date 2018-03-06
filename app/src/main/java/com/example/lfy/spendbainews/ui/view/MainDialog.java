package com.example.lfy.spendbainews.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.lfy.spendbainews.R;


/**
 * 自定义dialog
 */
public class MainDialog extends Dialog {

    private Context context;
    private static MainDialog dialog;
    private ImageView ivProgress;
    RotateAnimation rotate;
    Animation animation;


    public MainDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MainDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    //显示dialog的方法
    public static MainDialog showDialog(Context context) {
        dialog = new MainDialog(context, R.style.MyDialog);//dialog样式
        dialog.setContentView(R.layout.dialog_layout);//dialog布局文件
        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
        return dialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (rotate != null)
            rotate.cancel();
    }

    @Override
    public void show() {
        super.show();
        if (dialog != null) {
            ivProgress = (ImageView) dialog.findViewById(R.id.ivProgress);
//            animation = AnimationUtils.loadAnimation(context, R.anim.dialog_progress_anim);
//            ivProgress.startAnimation(animation);

            rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            LinearInterpolator lin = new LinearInterpolator();
            rotate.setInterpolator(lin);
            rotate.setDuration(1500);//设置动画持续时间
            rotate.setRepeatCount(-1);//设置重复次数
            rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            rotate.setStartOffset(10);//执行前的等待时间
            ivProgress.startAnimation(rotate);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }


}
