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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.havefun.attendancesystem.ChatUser;
import com.havefun.attendancesystem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<ChatModel> mChat;
    FirebaseUser user;
    public MessageAdapter(Context context,List<ChatModel> user){
        this.context=context;
        this.mChat=user;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
     ChatModel chatModel =mChat.get(position);
     holder.showSenderMessages.setText(chatModel.getMessage());
     getUserImage(chatModel.getSender(),holder);

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }




    // Download the image
    private void getUserImage(String id,@NonNull final MessageAdapter.ViewHolder holder) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://attendance-system-29656.appspot.com/userImages/" +id+ ".jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("Mainprofile:::", "The Uri=  " + uri.toString());
                try {
                    Picasso.get().load(uri.toString()).placeholder(R.drawable.profile5).into(holder.messageSenderImage);

                }catch (Exception e){
                    Log.i("MessageAdapter", "onSuccess: the current image is not available yet ");
                }
                Log.i("User Photo Path", "onBindViewHolder: "+uri.toString());
            }
        });



    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        /// getting the structure from the card
        public TextView showSenderMessages;
        public ImageView messageSenderImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showSenderMessages=itemView.findViewById(R.id.show_message);
            messageSenderImage=itemView.findViewById(R.id.messageSenderImage);

        }
    }

    @Override
    public int getItemViewType(int position) {
        user= FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(user.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }

    }
}
