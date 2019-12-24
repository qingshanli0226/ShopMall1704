package com.example.administrator.shaomall.function.base;

/**
 * 显示的一些公共的部分
 */
public interface IBaseView {
    //显示错误提示
    void showError(String message);

    //显示成功提示
    void showSuccess(String message);

    //显示正在加载
    void showLoading();

    void complete();
}
