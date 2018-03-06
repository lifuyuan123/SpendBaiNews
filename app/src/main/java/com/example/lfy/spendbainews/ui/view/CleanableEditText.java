package com.example.lfy.spendbainews.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;


public class CleanableEditText extends EditText {

	Drawable mRightDrewable;
	boolean isFoc;

	public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CleanableEditText(Context context) {
		super(context);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		Drawable[] compound = getCompoundDrawables();
		mRightDrewable = compound[2];
		this.setOnFocusChangeListener(new FocusChanges());
		this.addTextChangedListener(new Mwhtcher());
		setWhetherCleanRightDrewable(false);

	}
	 public void setShakeAnimation() {
		    this.setAnimation(shakeAnimation(5));
		  }

	// CycleTimes�����ظ��Ĵ���
	public Animation shakeAnimation(int CycleTimes) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
		translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

	public class FocusChanges implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			isFoc = hasFocus;
			if (hasFocus) {
				boolean isClean = getText().toString().length() >= 1;
				setWhetherCleanRightDrewable(isClean);
			} else {
				setWhetherCleanRightDrewable(false);
			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			boolean isClean = (event.getX() > (getWidth()- getTotalPaddingRight())) && (event.getX() < (getWidth()- getPaddingRight()));
			if(isClean){
				setText("");
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	public class Mwhtcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			boolean isClean = getText().toString().length() >= 1;
			setWhetherCleanRightDrewable(isClean);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
		}

	}

	public void setWhetherCleanRightDrewable(boolean Whether) {
		Drawable rightDrawable;
		if (Whether) {
			rightDrawable = mRightDrewable;
		} else {
			rightDrawable = null;
		}
		setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0],
				getCompoundDrawables()[1], rightDrawable,
				getCompoundDrawables()[3]);
	}

}
