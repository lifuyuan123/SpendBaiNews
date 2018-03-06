package com.example.lfy.spendbainews.ui.actvity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.ToastUtils;
import com.example.lfy.spendbainews.application.MyApplication;
import com.example.lfy.spendbainews.databinding.ActivityMainBinding;
import com.example.lfy.spendbainews.entity.ContactsBean;
import com.example.lfy.spendbainews.ui.fragment.CreditFragment;
import com.example.lfy.spendbainews.ui.fragment.LoanProductFragment;
import com.example.lfy.spendbainews.ui.fragment.NewsFragment;
import com.example.lfy.spendbainews.ui.fragment.SlideMineFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseNoStatusActivity {

    private ActivityMainBinding binding;
    private FragmentTransaction transaction;
    private Fragment[] fragments = new Fragment[3];
    private int lastFragmentIndex;
    private int nowcheckedId;
    private Fragment currentFragment;
    private long firstTime;//第一次点击
    private long secondTime;//第二次点击
    private long spaceTime;//两次时间差
    private int[] a = {R.drawable.main_buttom_one, R.drawable.main_button_two,R.drawable.main_button_three};
    //需要用的的权限数组
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION
            ,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW
            ,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS,Manifest.permission.READ_CONTACTS};

    //注意  manifests里面也要添加相对应的权限才行
     private List<String> permissionlis=new ArrayList<>();//添加未被授权的权限



    @Override
    protected void initView() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.buttomContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //   StatusBarUtil.setColorNoTranslucent(activity, Color.parseColor(fragmentColors[checkedId]));

                RadioButton radioButton = (RadioButton) findViewById(checkedId);
//                //未登陆不能调信用页面
//                if (radioButton.getText().toString().trim().equals("信用") ) {
//                    toastShow("请先登录");
//                    nowcheckedId=checkedId;
//                    Intent intent = new Intent(activity, LogingActivity.class);
//                    startActivityForResult(intent,101);
//                    return;
//                }
                //根据下标展示fragment
                showFragment(checkedId);
            }
        });


        StatusBarUtil.setTranslucentForCoordinatorLayout(activity, 0);
        if (hasSoftKeys(getWindowManager())) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.buttomContent.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, getNavigationBarHeight(activity));
        }
        addButtom();

//        //获取通讯录权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED) {
//            //申请权限  第二个参数是一个 数组 说明可以同时申请多个权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
//        } else { //已授权
//            //获取通讯录
//            getContacts();
//        }


//        if(Build.VERSION.SDK_INT>=23){
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this,mPermissionList,123);
//        }

        //权限检查
        cpermissioCheck();


    }
    //权限检查
    private void cpermissioCheck() {
        //大于等于6.0  手动获取
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i <permissions.length ; i++) {
                if(ContextCompat.checkSelfPermission(this,permissions[i])!=PackageManager.PERMISSION_GRANTED){
                    permissionlis.add(permissions[i]);
                }
            }
                  //如果集合里有未授权的权限 则申请权限
                  if(!permissionlis.isEmpty()){
                     String [] permission=permissionlis.toArray(new String[permissionlis.size()]);//将未被授权的权限转为String数组
                     ActivityCompat.requestPermissions(this,permission,123);
                  //集合中没有未授权的权限  直接操作
                  }else {

                  }
        //小于6.0  无需操作
        } else {
            //获取权限后的操作
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();


        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);


        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;


        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);


        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;


        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - secondTime;
            secondTime = firstTime;
            if (spaceTime > 2000) {
                toastShow("再按一次退出程序");
            } else
                MainActivity.this.finish();
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //获取通讯录权限
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请成功
                getContacts();
            } else {
                Toast.makeText(MainActivity.this, "获取联系人的权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }
//        //友盟相关权限
//        if (requestCode==123){
//
//        }

        switch (requestCode) {
            case 123:
                if(grantResults.length>0){
                    for (int i = 0; i <grantResults.length ; i++) {//遍历
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            LogUtil.e(i+"");

                        //相关操作
                        }else {
                            ToastUtils.showShort("成功");
                        }
                    }
                }else {
                    ToastUtils.showShort("取消");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    //获取联系人
    public void getContacts() {
        List<ContactsBean> lists = new ArrayList<>();

        //获取手机通讯录联系人
        ContentResolver resolver = this.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(phoneCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(phoneCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                //把联系人用装起来装起来
                ContactsBean bean = new ContactsBean(contactName, phoneNumber);
                lists.add(bean);
            }
            phoneCursor.close();
        }

        MyApplication.setList(lists);
        LogUtil.e(MyApplication.getList().toString());

    }

    //根据下标展示fragment
    private void showFragment(int indext){
        transaction = getSupportFragmentManager().beginTransaction();
        currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(indext));
        Fragment last = getSupportFragmentManager().findFragmentByTag(String.valueOf(lastFragmentIndex));
        if (last != null) {
            transaction.hide(last);
        }

        if (currentFragment == null)
            currentFragment = fragments[indext];


        if (currentFragment != null)
            if (!currentFragment.isAdded())
                transaction.add(R.id.fragment_container, currentFragment, String.valueOf(indext));
        transaction.show(currentFragment).commitAllowingStateLoss();
        lastFragmentIndex = indext;
    }

    //添加fragment和radiobutton
    private void addButtom() {
        fragments[0] = new NewsFragment();
//        fragments[0] = new LoanProductFragment();
        fragments[1] = new CreditFragment();
        fragments[2] = new SlideMineFragment();
        binding.buttomContent.removeAllViews();
        String[] array = getResources().getStringArray(R.array.buttom_array);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        for (int i = 0; i < array.length; i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setCompoundDrawablesWithIntrinsicBounds(0, a[i], 0, 0);
            button.setButtonDrawable(new BitmapDrawable());
            button.setBackgroundDrawable(null);
            button.setText(array[i]);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            button.setTextColor(getResources().getColorStateList(R.color.buttom_color_selector));
            binding.buttomContent.addView(button);
        }
        binding.buttomContent.getChildAt(0).performClick();


    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}
