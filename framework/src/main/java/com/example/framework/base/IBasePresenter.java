package com.example.framework.base;

public interface IBasePresenter<T> {


    void attachGetView(IGetBaseView<T> getBaseView);

    void attachPostView(IPostBaseView<T> postBaseView);

    void attachLoadView(ILoadView iLoadView);

    void detachView();

    void getGetData();

    void getCipherTextData();

    void getPostJsonData();

    void getPostFormData();

    void getPostFile();
}
