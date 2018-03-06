package com.example.lfy.spendbainews.ui.view;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.lfy.spendbainews.R;


/**
 * Created by yi17.zhang on 2017/5/19.
 */
public class CustomKeyboard {

    private EditText mEdittext;
    private MyKeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private Context context;
    private View parent;

    public CustomKeyboard(final Context context, MyKeyboardView keyboardView, EditText editText, View parent) {
        this.mEdittext = editText;
        mKeyboard = new Keyboard(context, R.xml.keyboard);//从xml中加载自定义的键盘
        mKeyboardView = keyboardView;
        this.context = context;
        this.parent = parent;
        mKeyboardView.setContext(context);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(actionListener);
        parent.findViewById(R.id.bt_hideKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        mEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom
            // keyboard when the edit box gets focus, but also hide it when the
            // edit box loses focus
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showKeyboard();
                else {
                    hideKeyboard();
                }

            }
        });

        mEdittext.setOnClickListener(new View.OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom
            // keyboard again, by tapping on an edit box that already had focus
            // (but that had the keyboard hidden).
            @Override
            public void onClick(View v) {
                showKeyboard();
            }
        });

        mEdittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType(); // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard
                // keyboard
                edittext.onTouchEvent(event); // Call native handler
                edittext.setInputType(inType); // Restore input type
                ((Activity) context).getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        mEdittext.setInputType(mEdittext.getInputType()
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        ((Activity) context).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private KeyboardView.OnKeyboardActionListener actionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEdittext.getText();
            int index = mEdittext.getSelectionStart();//光标位置
            switch (primaryCode) {

                case Keyboard.KEYCODE_DELETE://回退
                    if (editable != null && editable.length() > 0) {
                        if (index > 0) {
                            editable.delete(index - 1, index);
                        }
                    }
                    break;
                case 9995://重输
                    mEdittext.setText("");
                    break;
                case 9994://左移
                    if (index > 0) {
                        mEdittext.setSelection(index - 1);
                    }
                    break;
                case 9996://右移
                    if (index < mEdittext.length()) {
                        mEdittext.setSelection(index + 1);
                    }
                    break;
                default:
                    editable.insert(index, Character.toString((char) primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public void showKeyboard() {
        if (mKeyboardView.getVisibility() != View.VISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
            parent.setVisibility(View.VISIBLE);

        }
        mEdittext.setInputType(InputType.TYPE_NULL); // Disable standard
        ((InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mKeyboardView.getWindowToken(), 0);
        ((Activity) context).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void hideKeyboard() {
        if (mKeyboardView.getVisibility() == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
            parent.setVisibility(View.GONE);
        }
    }

    public boolean isShowKeyboard() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

}

