package com.example.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.framework.R;
import com.example.framework.port.IFragment;

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

    //TODO 跳转新的Activity
    public void startActivity(Class Activity,Bundle bundle){
        Intent intent = new Intent(getActivity(),Activity);
        //TODO 携带数据
        if(bundle != null && bundle.size() != 0){
            intent.putExtra("data",bundle);
        }
        getActivity().startActivity(intent);
        //TODO 添加入场动画以及退场动画
        getActivity().overridePendingTransition(R.anim.slide_to_left_in,R.anim.slide_to_left_out);
    }

    //TODO 吐司
    public void toast(Activity instance, String msg){
        Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
    }

}
