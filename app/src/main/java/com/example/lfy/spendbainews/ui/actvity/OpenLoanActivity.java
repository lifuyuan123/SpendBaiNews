package com.example.lfy.spendbainews.ui.actvity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.AccountValidatorUtil;
import com.example.lfy.spendbainews.Utils.AddSpaceTextWatcher;
import com.example.lfy.spendbainews.Utils.GsonUtils;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.dedclick.AntiShake;
import com.example.lfy.spendbainews.application.MyApplication;
import com.example.lfy.spendbainews.databinding.ActivityOpenloanBinding;
import com.example.lfy.spendbainews.entity.BMapAdress;
import com.example.lfy.spendbainews.entity.Login;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;
import com.example.lfy.spendbainews.http.ApiCommonCallback;
import com.example.lfy.spendbainews.ui.view.CustomKeyboard;
import com.example.lfy.spendbainews.ui.view.MyKeyboardView;
import com.example.lfy.spendbainews.ui.view.citypicker.citypickerview.widget.CityPicker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 *基本信息页面
 **/
public class OpenLoanActivity extends BaseActivity {
    private final int PICK_CONTACTONE = 0;
    private final int PICK_CONTACTTWO = 1;
    private String product = "";//借款详情页调过来的参数（未填写基本信息）
    private String locationProvider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;

    CustomKeyboard mCustomKeyboard;

    private Login login;
    private boolean  isall=false;//是否人脸认证了
    private ActivityOpenloanBinding binding;


    @Override
    protected void initView() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_openloan);
        login= MyApplication.getLogin();
        if(!TextUtils.isEmpty(login.getCard())&&!TextUtils.isEmpty(login.getName())&&!login.getCard().equals("")&&!login.getName().equals("")){
            binding.etUName.setText(login.getName());
            binding.etUCard.setText(login.getCard());
        }
        product = getIntent().getStringExtra("product");
        AddSpaceTextWatcher etCardWatcher = new AddSpaceTextWatcher(binding.etUCard, 21);
        etCardWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.IDCardNumberType);
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //人脸识别成功  没有继续认证基本信息就退出页面
                if (isall=true){
//                    EventBus.getDefault().post(new LoginOut(false));
                }
                finish();
            }
        });
        getLocation(OpenLoanActivity.this);
        MyKeyboardView keyboardView = (MyKeyboardView) findViewById(R.id.customKeyboard);
        mCustomKeyboard = new CustomKeyboard(this, keyboardView, binding.etUCard, binding.llKeyBoard);
        //显示键盘的操作
//        etUCard.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mCustomKeyboard.showKeyboard();
//                return false;
//            }
//        });


    }


    /**
     * 调用本地GPS来获取经纬度
     *
     * @param context
     */
    private void getLocation(Context context) {
        //1.获取位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是网络定位
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.PASSIVE_PROVIDER)) {
            //如果是PASSIVE定位
            locationProvider = LocationManager.PASSIVE_PROVIDER;
        } else {
            toastShow("没有可用的位置提供器");
            return;
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
        } else {
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 3000, 1, mListener);
        }
    }


    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        // 如果位置发生变化，重新显示
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };


    /**
     * 获取经纬度
     *
     * @param location
     */
    private void showLocation(Location location) {
        String longtitude = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        getLocation(location);
    }



    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {//必传参数没有名字和身份证
            case R.id.bt_goto:
                final String card = com.example.lfy.spendbainews.Utils.TextUtils.repaceTrim(binding.etUCard.getText().toString().trim());
                String email = com.example.lfy.spendbainews.Utils.TextUtils.repaceTrim(binding.etUemail.getText().toString().trim());

                Log.e("updataUserInfoo","开始1");
                if (TextUtils.isEmpty(binding.etDwmc.getText().toString().trim())||binding.etDwmc.getText().toString().trim().equals("")) {
                    toastShow("请填写单位名称");
                    return;
                }
                if (!AccountValidatorUtil.isEmail(email)) {
                    toastShow("邮箱格式错误");
                    return;
                }

                mainDialog.show();
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("userid", MyApplication.getLogin().getUserid());
                stringStringMap.put("mail", email);
                stringStringMap.put("address", binding.etLocation.getText().toString().trim());
                stringStringMap.put("units", binding.etDwmc.getText().toString().trim());
                httpLoader.updataUserInfoo(stringStringMap, new ApiBaseResponseCallback<Object>() {
                    @Override
                    public void onSuccessful(Object o) {
                        Login login = MyApplication.getLogin();
                        login.setCard(card);
                        login.setName(binding.etUName.getText().toString().trim());
                        login.setMail(binding.etUemail.getText().toString().trim());
                        login.setAddress(binding.etLocation.getText().toString());
                        login.setUnits(binding.etDwmc.getText().toString());
                        MyApplication.setLogin(login);
                        LogUtil.e("成功     "+MyApplication.getLogin().toString());
                        //bugly上报错  method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference
//                        if (product.equals("product")) {
//                            EventBus.getDefault().post(new LoginOut(false));
//                            finish();
//                        }
//                        EventBus.getDefault().post(new Navagation(2));
//                        EventBus.getDefault().post(new LoginOut(false));
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (msg!=null){
                            toastShow(msg);
                            LogUtil.e(msg.toString());
                        }

                    }

                    @Override
                    public void onFinish() {
                        mainDialog.dismiss();
                        LogUtil.e("完成");
                    }
                });

                break;

            case R.id.bt_contactOne:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACTONE);
                break;

            case R.id.bt_contactTwo:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACTTWO);

                break;


            case R.id.bt_adr:
                CityPicker cityPicker = new CityPicker.Builder(OpenLoanActivity.this)
                        .textSize(16)
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .province("四川省")
                        .city("成都市")
                        .district("武侯区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .build();

                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        binding.etLocation.setText(citySelected[0] + citySelected[1] +
                                citySelected[2]);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;

            case R.id.bt_hideKey:
                mCustomKeyboard.hideKeyboard();
                break;
            case R.id.et_uName:
            case R.id.et_uCard:
//                goFaceAuth("14");
                break;

        }

    }


    /**
     * 得到当前经纬度并开启线程去反向地理编码
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("ak", "Tt2TGYoY8vOOG2jH0rEwRQLZuIEb9FQI");
        stringStringMap.put("mcode", "A9:F5:F0:7A:A7:33:56:B0:84:3B:43:70:D4:BB:77:67:BE:23:22:A2;com.rx.chat");
        stringStringMap.put("callback", "renderReverse");
        stringStringMap.put("location", latitude + "," + longitude);
        stringStringMap.put("output", "json");
        stringStringMap.put("pois", "0");

        String url = "http://api.map.baidu.com/geocoder/v2/";
        sendRequest(url, stringStringMap, 0);
    }


    private void sendRequest(String url, final Map<String, String> map, final int type) {
        mainDialog.show();
        httpLoader.common(url, map,new ApiCommonCallback() {

            @Override
            public void onSuccess(ResponseBody responseBody) {

                try {
                    String str = responseBody.string();
                    Log.e("location",str.toString());
                    Log.i("ggband", "type：" + type);
                    Log.i("ggband", "responseBody" + str);
                    if (str != null && !TextUtils.isEmpty(str)) {
                        str = str.replace("renderReverse&&renderReverse", "");
                        str = str.replace("(", "");
                        str = str.replace(")", "");
                        BMapAdress bMapAdress = GsonUtils.GsonToBean(str, BMapAdress.class);
                        String adress = bMapAdress.getResult().getAddressComponent().getProvince() +
                                bMapAdress.getResult().getAddressComponent().getCity() +
                                bMapAdress.getResult().getAddressComponent().getDistrict();
                        binding.etLocation.setText(adress);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("locationIOException",e.toString());
                }
                mainDialog.dismiss();

            }

            @Override
            public void onFailure(String msg) {
                if (msg!=null){
                    Log.e("locationonFailure",msg.toString());
                }
                mainDialog.dismiss();

            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();
                LogUtil.e("完成");

            }
        });
    }

//    private void goFaceAuth(final String type) {
//        AuthBuilder mAuthBuilder = new AuthBuilder(UUID.randomUUID().toString(), "606800ff-8646-454b-bf56-e35c02d8e785", "url", new OnResultListener() {
//            @Override
//            public void onResult(String s) {
//                Log.e("goFaceAuth", "result:" + s);
//                Map<String, String> stringMap = GsonUtils.GsonToMaps(s);
//                stringMap.put("type", type);
//                stringMap.put("uid", MyApplication.getLogin().getUserid());
////                mainDialog.show();
//                httpLoader.returnFace(stringMap, new ApiBaseResponseCallback<UserInfo>() {
//                    @Override
//                    public void onSuccessful(UserInfo userInfo) {
//                        if(userInfo==null)
//                            return;
//                        toastShow("识别成功");
////                        mainDialog.dismiss();
//                        Log.e("goFaceAuth",userInfo.toString());
//                        if(etUName!=null&&etUCard!=null){
//                            etUName.setText(userInfo.getName());
//                            etUCard.setText(userInfo.getCard());
//                        }
//                        Login login = MyApplication.getLogin();
//                        login.setCard(userInfo.getCard());
//                        login.setName(userInfo.getName());
//                        MyApplication.setLogin(login);
//                        isall=true;
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        mainDialog.dismiss();
//
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        toastShow(msg);
//                        mainDialog.dismiss();
//                    }
//                });
//            }
//
//        });
//        mAuthBuilder.setUserId(MyApplication.getLogin().getUserid());
//        //下文调用方法做为范例，请以对接文档中的调用方法为准
//        mAuthBuilder.faceAuth(activity);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        //  获取返回的联系人的Uri信息
        Uri contactDataUri = data.getData();
        Cursor cursor = getContentResolver().query(contactDataUri, null, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            //   获得联系人记录的ID
            String contactId = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            //  获得联系人的名字
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            String phoneNumber = "未找到联系人号码";
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.
                    Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.
                    Phone.CONTACT_ID + "=" + "?", new String[]{contactId}, null);
            if (phoneCursor.moveToFirst()) {
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            //  关闭查询手机号码的cursor
            phoneCursor.close();
            if (requestCode == PICK_CONTACTONE) {
                binding.etConNameOne.setText(name);
                binding.etConPhoneOne.setText(phoneNumber);
            } else {
                binding.etConNameTwo.setText(name);
                binding.etConPhoneTwo.setText(phoneNumber);
            }

        }
        //  关闭查询联系人信息的cursor
        cursor.close();
    }

    @Override
    public void onBackPressed() {

        if (mCustomKeyboard.isShowKeyboard()) {
            mCustomKeyboard.hideKeyboard();
        } else {
            super.onBackPressed();
        }
    }



    //人脸识别成功  没有继续认证基本信息就退出页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            if (isall==true){
//                EventBus.getDefault().post(new LoginOut(false));
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
