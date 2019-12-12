package com.example.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.framework.port.IFragment;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IView;
/**
 * author:李浩帆
 */
public abstract class BaseFragment extends Fragment implements IFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initDate();
    }

    //TODO 吐司
    public void toast(Activity instance, String msg){
        Toast.makeText(instance,msg,Toast.LENGTH_SHORT).show();
    }

}
