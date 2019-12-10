package com.example.framework.base;

public interface IPostBaseView<T> {

    void onPostDataSucess(T data);

    void onPostDataFailed(String ErrorMsg);
}
