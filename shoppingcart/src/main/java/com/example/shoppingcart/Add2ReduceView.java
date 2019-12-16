package com.example.shoppingcart;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Add2ReduceView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private final Context mContext;
    private ImageButton mIbReduce;
    private TextView mTvNum;
    private ImageButton mIbAdd;

    private int amount = 1; //购买数量
    private int goods_storage = 99; //商品库存
    private OnAmountChangeListener mListener;


    public Add2ReduceView(Context context) {
        this(context, null);
    }

    public Add2ReduceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Add2ReduceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_add2reduce, this);
        initView();
    }

    private void initView() {
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mIbAdd = (ImageButton) findViewById(R.id.ib_add);
        mIbReduce = (ImageButton) findViewById(R.id.ib_reduce);
        mTvNum.setText(amount + "");
        mIbAdd.setOnClickListener(this);
        mIbReduce.setOnClickListener(this);
        mTvNum.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_reduce) {
            if (amount > 1) {
                amount--;
                mTvNum.setText(amount + "");
            } else {
                Toast.makeText(mContext, "亲, 不能再减少啦!", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.ib_add) {
            if (amount < goods_storage) {
                amount++;
                mTvNum.setText(amount + "");
            } else {
                Toast.makeText(mContext, "商品限购5个", Toast.LENGTH_SHORT).show();
            }
        }

        //清除焦点
        mTvNum.clearFocus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    //设置购买数量
    public void setAmount(int amount){
        this.amount = amount;
        mTvNum.setText(amount+"");
    }


    //设置库存
    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            mTvNum.setText(goods_storage + "");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    //数量发生改变
    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }
}
