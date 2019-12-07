package com.shaomall.framework.base.presenter;


import com.shaomall.framework.base.view.IBaseView;

/**
 * 获取数据, 各个页面都可以调用该接口获取数据
 *
 * @param <T>
 */
public interface IBasePresenter<T> {

    //requestCode来区分不同的网络请求
    void doGetHttpRequest(int requestCode);     //get请求

    void doPostHttpRequest(int requestCode);    //post请求

    void doAddOneProduct(int requestCode);      //添加一个产品到购物车

//    void doUpLoadHttpRequest(int requestCode);  //上传头像

//    void doDownLoadFileHttpRequest(int requestCode); //下载文件


    void attachView(IBaseView<T> iBaseView); //关联IBaseView

    void detachView(); //解除关联
}
