package com.example.shoppingcart.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shaomall.framework.bean.ShoppingCartBean;
import com.example.shoppingcart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RvAdp extends RecyclerView.Adapter<RvAdp.Myhodler> {
    List<ShoppingCartBean> arr;
    Context context;

    public RvAdp(List<ShoppingCartBean> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }


    @NonNull
    @Override
    public Myhodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, null);
        return new Myhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myhodler myhodler, final int i) {
        Picasso.with(context).load(arr.get(i).getUrl()).into(myhodler.ivAdapterListPic);
        myhodler.tvGoodsName.setText(arr.get(i).getProductName());
        myhodler.tvGoodsPrice.setText(arr.get(i).getProductPrice());
        myhodler.tvNum.setText(arr.get(i).getProductNum());
        myhodler.checkBox.setChecked(arr.get(i).isSelect());


        //TODO 购物车详情页面的按钮
        myhodler.tvGoodsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemDetailsCallBack.onItemDetailsCallBack(i);
            }
        });

        //TODO 减少的按钮
        myhodler.tvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(arr.get(i).getProductNum()) == 1) {
                    Toast.makeText(context, "已经不能在减啦", Toast.LENGTH_SHORT).show();
                } else {
                    //选中
                    myhodler.checkBox.setChecked(true);
                    int num = Integer.parseInt(arr.get(i).getProductNum()) - 1;
                    itemNumCallBack.onItemNumCallBack(i, num, myhodler.checkBox.isChecked());
                    myhodler.tvNum.setText(num + "");
                }
            }
        });

        //TODO 增加的按钮
        myhodler.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中
                myhodler.checkBox.setChecked(true);
                int num = Integer.parseInt(arr.get(i).getProductNum()) + 1;
                itemNumCallBack.onItemNumCallBack(i, num, myhodler.checkBox.isChecked());
                myhodler.tvNum.setText(num + "");
            }
        });


        //TODO 多选框按钮
        myhodler.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 设置多选框是否选中
                arr.get(i).setSelect(myhodler.checkBox.isChecked());
                //TODO 返回点击的第几个
                itemOnCheckBoxClickListener.onClick(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arr.size();
    }

    class Myhodler extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView ivAdapterListPic;
        private TextView tvGoodsName;
        private TextView tvGoodsPrice;
        private TextView tvTypeSize;
        private TextView tvReduce;
        private TextView tvNum;
        private TextView tvAdd;


        public Myhodler(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            ivAdapterListPic = (ImageView) itemView.findViewById(R.id.iv_adapter_list_pic);
            tvGoodsName = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tvTypeSize = (TextView) itemView.findViewById(R.id.tv_type_size);
            tvReduce = (TextView) itemView.findViewById(R.id.tv_reduce);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvAdd = (TextView) itemView.findViewById(R.id.tv_add);

        }
    }


    //checkbox要使用到的的接口回调
    private ItemOnCheckBoxClickListener itemOnCheckBoxClickListener;

    //OnClickListener

    public void setItemCheckBoxOnClickListener(ItemOnCheckBoxClickListener itemCallBack) {
        this.itemOnCheckBoxClickListener = itemCallBack;
    }

    //TODO checkbox要使用到的的接口回调
    public interface ItemOnCheckBoxClickListener {
        void onClick(int i);
    }

    private ItemNumCallBack itemNumCallBack;

    public void setItemNumCallBack(ItemNumCallBack itemNumCallBack) {
        this.itemNumCallBack = itemNumCallBack;
    }

    //TODO 购物车数量要使用到的接口
    public interface ItemNumCallBack {
        void onItemNumCallBack(int i, int num, boolean isSelect);
    }


    //购物车点击展示详情页面的接口
    private ItemDetailsCallBack itemDetailsCallBack;

    public void setItemDetailsCallBack(ItemDetailsCallBack itemDetailsCallBack) {
        this.itemDetailsCallBack = itemDetailsCallBack;
    }

    //TODO 购物车点击展示详情页面的接口
    public interface ItemDetailsCallBack {
        void onItemDetailsCallBack(int i);
    }
}

