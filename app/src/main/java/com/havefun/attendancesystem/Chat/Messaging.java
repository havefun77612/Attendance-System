package com.havefun.attendancesystem.Chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.havefun.attendancesystem.R;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Messaging extends AppCompatActivity {
    ImageView reciver_Image;
    TextView reciverUserName;
    Intent intent;
    String reciverID;
    FirebaseUser user;
    DatabaseReference reference;
    String id, uri, name;
    ImageView sendMessage;
    EditText textMessage;
    MessageAdapter messageAdapter;
    List<ChatModel> mChat;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String TAG="Messaging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
        initializeVars();
        getReciverData();
        addListners();
    }


    /*
     ****** Inintialzation stage of the messaging class vars
     */
    private void initializeVars() {
        reciver_Image = findViewById(R.id.messageUserImage);
        reciverUserName = findViewById(R.id.messageUserName);
        sendMessage = findViewById(R.id.messageSend);
        textMessage = findViewById(R.id.messageText);
        intent = getIntent();
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.messagesRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private void addListners() {
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textMessage.getText().toString();
                if (msg.isEmpty() || msg.equals("")) {
                    FancyToast.makeText(getApplicationContext(), "Can't send empty message", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                } else {
                    sendMessage(user.getUid(), reciverID, msg);

                }
                textMessage.setText("");
            }
        });
    }

    private void getReciverData() {
        reciverID = intent.getStringExtra("userID");
//        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserId")
//                .equalTo(reciverID);


        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("UserId").equalTo(reciverID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        uri = data.child("UserProfileUri").getValue().toString();
                        name = data.child("UserName").getValue().toString();
                    }
                    reciverUserName.setText(name);
                    getUserImage(reciverID);

                    //Toast.makeText(Messaging.this, "Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Messaging.this, "Error ", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "onDataChange:  calling and functions could be called from here ");
                readMessage(user.getUid(),reciverID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    // Download the image
    private void getUserImage(final String user) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://attendance-system-29656.appspot.com/userImages/" + user + ".jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i(TAG, "The Uri=  " + uri.toString());
                Picasso.get().load(uri.toString()).placeholder(R.drawable.profile5).into(reciver_Image);
                Log.i("User Photo Path", "onBindViewHolder: " + uri.toString());
            }
        });

    }

    private void sendMessage(String sender, String reciver, String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", reciver);
        hashMap.put("message", message);

        databaseReference.child("chat").push().setValue(hashMap);

    }

    private void readMessage(final String myId, final String userId) {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chat");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ChatModel chatModel = data.getValue(ChatModel.class);
                    if (chatModel.getReceiver().equals(myId) && chatModel.getSender().equals(userId) ||
                            chatModel.getReceiver().equals(userId) && chatModel.getSender().equals(myId)
                    ) {
                        mChat.add(chatModel);
                    }
                }
                MessageAdapter messageAdapter=new MessageAdapter(getApplicationContext(),mChat);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}