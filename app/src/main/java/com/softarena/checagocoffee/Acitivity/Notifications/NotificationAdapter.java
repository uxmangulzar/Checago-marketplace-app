package com.softarena.checagocoffee.Acitivity.Notifications;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Acitivity.Orders.OrderHistoryActivity;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> notificationModelList;
    Context context;
    public NotificationAdapter(List<NotificationModel> notificationModelList,Context context)
    {
        this.notificationModelList = notificationModelList;
        this.context = context;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notifications_list,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        holder.tv_orderStatusNotification.setText(notificationModelList.get(position).getNotificationTitle());
        holder.tv_notificationDate.setText(notificationModelList.get(position).getNotificationCreatedAt());
        Glide.with(context).load(APIClient.BASE_URL+notificationModelList.get(position).getNotificationSenderImage()).into(holder.profile_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!notificationModelList.get(position).getNotificationType().equals("Promotional")){
                    Intent intent=new Intent(context, OrderHistoryActivity.class);
                    context.startActivity(intent);
                }else {
                    Intent intent=new Intent(context, MainMenuDrawerActivity.class);
                    context.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profile_image;
        TextView tv_orderStatusNotification
                ,tv_sourceName,tv_notificationDate,tv_notificationTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderStatusNotification = itemView.findViewById(R.id.tv_orderStatusNotification);
            profile_image = itemView.findViewById(R.id.profile_image);
           // tv_sourceName = itemView.findViewById(R.id.tv_sourceName);
            tv_notificationDate = itemView.findViewById(R.id.tv_notificationDate);
            tv_notificationTime = itemView.findViewById(R.id.tv_notificationTime);

        }

    }
}
