package com.havefun.attendancesystem.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.havefun.attendancesystem.ChatUser;
import com.havefun.attendancesystem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private List <ChatUser> users;
    public RecycleAdapter(Context context,List<ChatUser> user){
       this.context=context;
       this.users=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_card,parent,false);
        return new RecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatUser user=users.get(position);
        holder.username.setText(user.getUserName());
        try {
            Picasso.get().load(user.getUserProfileUri()).placeholder(R.drawable.profile5).into(holder.userImageView);
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder{
        /// getting the structure from the card
        public TextView username;
        public ImageView userImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.usersUserName);
            userImageView=itemView.findViewById(R.id.usersUserImage);
        }
    }

}
