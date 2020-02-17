package com.havefun.attendancesystem.Chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.username.setText(users.get(position).getUserName());


        if (!users.get(position).getUserProfileUri().isEmpty()) {
            getUserImage(position,holder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Messaging.class);
                intent.putExtra("userID",users.get(position).getUserId());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return users.size();
    }




    // Download the image
    private void getUserImage(final int position,@NonNull final ViewHolder holder) {


                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://attendance-system-29656.appspot.com/userImages/" + users.get(position).getUserId() + ".jpg");
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Mainprofile:::", "The Uri=  " + uri.toString());
                        users.get(position).setUserProfileUri(uri.toString());
                        Picasso.get().load(users.get(position).getUserProfileUri()).placeholder(R.drawable.profile5).into(holder.userImageView);
                        Log.i("User Photo Path", "onBindViewHolder: " + users.get(position).getUserProfileUri());

                    }
                });




    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        /// getting the structure from the card
        public TextView username;
        private ImageView userImageView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.usersUserName);
            userImageView=itemView.findViewById(R.id.usersUserImage);
        }
    }

}
