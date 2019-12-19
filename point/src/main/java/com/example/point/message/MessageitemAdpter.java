package com.example.point.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.code.Constant;
import com.example.point.R;

import java.util.List;

public class MessageitemAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MessageBean> beanList;
    public final static int ONE=0;
    public final static int TWO=1;
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
                View inflate1 = layoutInflater.inflate(R.layout.message_server, parent, false);
                Message_socket message_socket = new Message_socket(inflate1);
                return  message_socket;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }


    class Message_socket extends RecyclerView.ViewHolder{
        private ImageView socket_img;
        private TextView socket_message;
        public Message_socket(@NonNull View itemView) {
            super(itemView);
            socket_img=itemView.findViewById(R.id.socket_img);
            socket_message=itemView.findViewById(R.id.socket_message);
        }
    }

    class Message_server extends RecyclerView.ViewHolder{
        private ImageView server_img;
        private TextView server_message;
        public Message_server(@NonNull View itemView) {
            super(itemView);
            server_img=itemView.findViewById(R.id.server_img);
            server_message=itemView.findViewById(R.id.server_message);
        }
    }
}
