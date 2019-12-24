package com.example.administrator.shaomall.function.base;

import java.lang.ref.WeakReference;

/**
 * BaseVM
 * (通过attachView和detachView的方式，获取BaseView的引用和解除)
 */
public class BaseVM<V extends IBaseView> implements IBaseVM {
    //弱引用
    private WeakReference<IBaseView> mVWeakReference;

    @Override
    public void attachView(IBaseView view) {
        mVWeakReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mVWeakReference != null) {
            mVWeakReference.clear();
            mVWeakReference = null;
        }
    }

    @Override
    public IBaseView getIView() {
        if (mVWeakReference != null) {
            return mVWeakReference.get();
        }
        return null;
    }
}
