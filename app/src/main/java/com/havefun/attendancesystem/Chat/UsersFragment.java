package com.havefun.attendancesystem.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.havefun.attendancesystem.ChatUser;
import com.havefun.attendancesystem.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {
private RecyclerView recyclerView;
private List<ChatUser> userInfos;
private RecycleAdapter recycleAdapter;
private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_users, container, false);
        repareView();

      return view;
    }

    private void repareView() {
    recyclerView=view.findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    userInfos=new ArrayList<>();
    readUsers();
    }

    private void readUsers() {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    userInfos.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChatUser user = snapshot.getValue(ChatUser.class);
                        assert user != null;
                        assert firebaseUser != null;
                        if (!user.getUserId().equals(firebaseUser.getUid())) {
                            userInfos.add(user);
                        }
                    }
                    recycleAdapter = new RecycleAdapter(getContext(), userInfos);
                    recyclerView.setAdapter(recycleAdapter);
                }else {
                    FancyToast.makeText(getContext(),"Error Getting User Data Please Cheack Your Internet",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
