package com.example.point.stepmanager

import android.content.Context
import com.example.point.DaoMaster
import com.example.point.service.StepBean
import com.example.point.StepBeanDao

class DaoManager {
    private var stepBeanDao: StepBeanDao

    private constructor(ctx: Context) {
        //创建一个db 数据库
        //通过DaoMaster内部类DaoMaster.DevOpenHelper创建一个SqliteOpenHelper类实例， 通过openhelper获取数据db
        var db = DaoMaster.DevOpenHelper(ctx, "1704.db", null).writableDatabase
        var daoMaster = DaoMaster(db)
        var daoSession = daoMaster.newSession()
        stepBeanDao = daoSession.stepBeanDao
    }

    //通过伴生对象，实现单例
    companion object {
        private var instance: DaoManager? = null
        public fun getInstance(ctx: Context): DaoManager {
            if (instance == null) {
                instance = DaoManager(ctx)
            }
            return instance!!
        }
    }
     //添加一个计步bean类
    fun addStepBean(stu: StepBean) {
        stepBeanDao.insert(stu)
    }
     //查询日期
    fun queryStepBean(curr_date:String):MutableList<StepBean> {
        var stuList:MutableList<StepBean> = stepBeanDao.queryRaw("where curr_date=?", curr_date)
        return stuList
    }
    //更新
    fun updateStepBean(stu: StepBean) {
        stepBeanDao.update(stu)
    }
    //加载所有
    fun loadStepBean():MutableList<StepBean> {
        var stuList:MutableList<StepBean> =stepBeanDao.queryBuilder().orderDesc(StepBeanDao.Properties.Id).list()

        return stuList
    }

    //区间查询
    fun areaStepBean(start:String,stop:String):MutableList<StepBean> {
        var start_date:MutableList<StepBean> = stepBeanDao.queryRaw("where curr_date=?", start)//先获取开始日期
        var stop_date:MutableList<StepBean> = stepBeanDao.queryRaw("where curr_date=?", stop)//结束日期
        if (start_date.size==0 || stop_date.size==0){

        }else{
            //获取两个日期之间的ID
            val start_id = start_date.get(0).id
            val stop_id = stop_date.get(0).id

            if(start_id<stop_id){
                //根据ID去查询区间
                var list:MutableList<StepBean> = stepBeanDao.queryBuilder().orderDesc(StepBeanDao.Properties.Id).where(
                    StepBeanDao.Properties.Id.between(start_id,stop_id)).build().list()
                return list
            }else{
                var list:MutableList<StepBean> = stepBeanDao.queryBuilder().orderDesc(StepBeanDao.Properties.Id).where(
                    StepBeanDao.Properties.Id.between(stop_id,start_id)).build().list()
                return list
            }

        }
        var list:MutableList<StepBean> = mutableListOf()
        return  list
    }

    //除了今天以外的记录
    fun queryexcept(curr_date:String):MutableList<StepBean> {
        var stuList:MutableList<StepBean> = stepBeanDao.queryRaw("where curr_date!=?", curr_date)
        return stuList
    }
}