package com.example.shopmall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shopmall.bean.StepBean;
import com.example.shopmall.greendao.DaoMaster;
import com.example.shopmall.greendao.DaoSession;
import com.example.shopmall.greendao.StepBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.LinkedList;
import java.util.List;

public class StepManager {

    private static StepManager stepManager;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private List<IStepChangedListener> iStepChangedListenerList = new LinkedList<>();
    private StepBeanDao stepBeanDao;

    public static StepManager getInstance() {
        if (stepManager == null) {
            stepManager = new StepManager();
        }
        return stepManager;
    }

    public void init(Context context) {
        this.context = context;
        sqLiteDatabase = new DaoMaster.DevOpenHelper(context, "step.db").getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        stepBeanDao = daoSession.getStepBeanDao();
    }

    public void addStep(StepBean stepBean) {
        stepBeanDao.insert(stepBean);
        for (IStepChangedListener stepChangedListener : iStepChangedListenerList) {
            stepChangedListener.onStepChanged(stepBean.getCurrentStrp());
        }
    }

    public List<StepBean> findStep(String date) {
        QueryBuilder<StepBean> findDate = stepBeanDao.queryBuilder().where(StepBeanDao.Properties.Data.eq(date));
        return findDate.list();
    }


    public void registerStepListener(IStepChangedListener iStepChangedListener) {
        if (!iStepChangedListenerList.contains(iStepChangedListener)) {
            iStepChangedListenerList.add(iStepChangedListener);
        }
    }

    public void unregisterStepListener(IStepChangedListener iStepChangedListener) {
        iStepChangedListenerList.remove(iStepChangedListener);
    }

    public interface IStepChangedListener {
        void onStepChanged(String step);
    }

}
