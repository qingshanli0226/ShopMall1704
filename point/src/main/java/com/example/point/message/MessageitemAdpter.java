package com.example.point.message;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.framework.bean.MessageBean;
import com.example.framework.manager.AccountManager;
import com.example.net.AppNetConfig;
import com.example.point.R;

import java.util.HashMap;
import java.util.List;

public class MessageitemAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MessageBean> beanList;
    private static final int ONE=0;
    private static final int TWO=1;
    public MessageitemAdpter(Context context, List<MessageBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ONE:
                View inflate = layoutInflater.inflate(R.layout.message_server, parent, false);
                Message_server message_server = new Message_server(inflate);
                return  message_server;
            case TWO:
                View inflate1 = layoutInflater.inflate(R.layout.message_socket, parent, false);
                Message_socket message_socket = new Message_socket(inflate1);
                return  message_socket;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof Message_server){
           Message_server  message_server= (Message_server) holder;
           message_server.server_message.setText(beanList.get(position).getMessage_message());
           message_server.server_date.setText(beanList.get(position).getMessage_date());
           Glide.with(context).load(beanList.get(position).getMessage_img()).into(message_server.server_img);

       }else   if (holder instanceof Message_socket){
           Message_socket  message_socket= (Message_socket) holder;
           message_socket.socket_message.setText(beanList.get(position).getMessage_message());
           message_socket.socket_date.setText(beanList.get(position).getMessage_date());
           if (AccountManager.getInstance().isLogin()) {
               if (AccountManager.getInstance().user.getName() != null) {
                   //登录
                   if (AccountManager.getInstance().user.getAvatar() != null) {
                       Glide.with(context).load("" + AppNetConfig.BASE_URL + AccountManager.getInstance().user.getAvatar()).apply(new RequestOptions().circleCrop()).into(message_socket.socket_img);
                   }
               }
           } else {
               //没有登录
               Glide.with(context).load(beanList.get(position).getMessage_img()).into(message_socket.socket_img);
           }
       }
    }

    @Override
    public int getItemViewType(int position) {
     if (position==0){
         return ONE;
     }else if (position%2==1||position%2==0){
         return TWO;
     }
     return ONE;
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class Message_socket extends RecyclerView.ViewHolder{
        private ImageView socket_img;
        private TextView socket_message;
        private TextView socket_date;
        public Message_socket( View itemView) {
            super(itemView);
            socket_img=itemView.findViewById(R.id.socket_img);
            socket_message=itemView.findViewById(R.id.socket_message);
            socket_date=itemView.findViewById(R.id.socket_date);
        }
    }

    class Message_server extends RecyclerView.ViewHolder{
        private ImageView server_img;
        private TextView server_message;
        private TextView server_date;
        public Message_server( View itemView) {
            super(itemView);
            server_img=itemView.findViewById(R.id.server_img);
            server_message=itemView.findViewById(R.id.server_message);
            server_date=itemView.findViewById(R.id.server_date);
        }
    }
}
