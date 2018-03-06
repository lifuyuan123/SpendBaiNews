package com.example.lfy.spendbainews.application;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.Utils.Utils;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.entity.ContactsBean;
import com.example.lfy.spendbainews.entity.Login;
import com.example.lfy.spendbainews.greendao.gen.GreenDaoManager;
import com.example.lfy.spendbainews.greendao.gen.LoginDao;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * 自定义application
 */

public class MyApplication extends Application {

    //个人资料
    private static LoginDao loginDao;
    private static Login login;
    //联系人资料
    private static List<ContactsBean> list=new ArrayList<>();
    public static List<ContactsBean> getList() {
        return list;
    }

    public static void setList(List<ContactsBean> list) {
        MyApplication.list = list;
    }

    {
        //友盟分享  相关平台key配置
        PlatformConfig.setWeixin("wx967daebe835fbeac","5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106753926", "kescuBPSxQ8mxEoz");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化操作  包含activity管理
        Utils.init(this,getApplicationContext());
        //友盟分享初始化
        UMShareAPI.get(this);
        //64k方法数限制原理与解决
        MultiDex.install(this);

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);

    }

    public static Login getLogin() {
        if (login == null) {
            loginDao = GreenDaoManager.getSingleTon().getmDaoSession().getLoginDao();
            QueryBuilder<Login> loginQueryBuilder = loginDao.queryBuilder();
            loginQueryBuilder.where(LoginDao.Properties.UID.ge(Config.USERID));
            if (loginQueryBuilder.list() != null && loginQueryBuilder.list().size() > 0) {
                login = loginQueryBuilder.list().get(0);
            }
        }
        return login;
    }

    public static void setLogin(Login login) {
        loginDao = GreenDaoManager.getSingleTon().getmDaoSession().getLoginDao();
        login.setUID(Config.USERID);
        loginDao.insertOrReplace(login);
        MyApplication.login = login;
        SPUtils.getInstance().put(Config.ISLOGIN, true);
    }


    public static boolean isOpen() {
        Login login = getLogin();
        if (login == null || login.getCard() == null || TextUtils.isEmpty(login.getCard()))
            return false;
        else
            return true;
    }

}
