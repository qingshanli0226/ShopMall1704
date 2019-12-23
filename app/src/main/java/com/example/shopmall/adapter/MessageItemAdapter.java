package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.view.RedView;
import com.example.framework.base.BaseAdapter;
import com.example.shopmall.R;
import com.example.framework.bean.MessageBean;

import java.util.List;

/**
 * 消息适配器
 */
public class MessageItemAdapter extends BaseAdapter<MessageBean,MessageItemAdapter.ViewHolder> {

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_message_inflate;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, List<MessageBean> messageBeans, int position) {
        holder.setData(messageBeans,position);
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RedView red_view_message;
        private TextView tvNameMessage;
        private TextView tvContentMessage;
        private TextView tvViewMoreMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            red_view_message = itemView.findViewById(R.id.red_view_message);
            tvNameMessage = itemView.findViewById(R.id.tv_name_message);
            tvContentMessage = itemView.findViewById(R.id.tv_content_message);
            tvViewMoreMessage = itemView.findViewById(R.id.tv_view_more_message);

        }

        public void setData(final List<MessageBean> messageBeans, final int position) {
            tvNameMessage.setText(messageBeans.get(position).getNameMessage());
            tvContentMessage.setText(messageBeans.get(position).getContentMessage());

            //判断是否读取
            if (messageBeans.get(position).getIsMessage()){
                red_view_message.setVisibility(View.GONE);
            }else {
                red_view_message.setVisibility(View.VISIBLE);
            }

            tvViewMoreMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeliest.getLikeliest(position);
                }
            });

        }
    }

    private Likeliest likeliest;

    public interface Likeliest{
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
