package com.example.lfy.spendbainews.greendao.gen;

import android.database.sqlite.SQLiteDatabase;

import com.example.lfy.spendbainews.Utils.Utils;


/**
 * author:ggband
 * date:2017/8/18 10:13
 * email:ggband520@163.com
 * desc:
 */

public class GreenDaoManager { private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static GreenDaoManager greenDaoUtils;

    private GreenDaoManager(){}

    public static GreenDaoManager getSingleTon(){
        if (greenDaoUtils==null){
            greenDaoUtils=new GreenDaoManager();
        }
        return greenDaoUtils;
    }

    private void initGreenDao(){
        mHelper=new DaoMaster.DevOpenHelper(Utils.getContext(),"xiaomo-db",null);
        db=mHelper.getWritableDatabase();
        // TODO: 2017/8/19 上线去掉
     //  mHelper.onUpgrade(db,0,1);//更新数据库
        mDaoMaster=new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        if (mDaoMaster==null){
            initGreenDao();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        if (db==null){
            initGreenDao();
        }
        return db;
    }

}