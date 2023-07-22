package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.PostDecor;

import java.util.ArrayList;
import java.util.List;

public class PostDecorFirebase {
    private DatabaseReference databaseReference;

    public PostDecorFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getPostDecor(final PostDecorFirebase.PostDecorCallback callback) {
        DatabaseReference postDecorRef = databaseReference.child("post_decor");
        final List<PostDecor> postDecorList = new ArrayList<>();

        postDecorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postDecorList.clear(); // Xóa danh sách cũ trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostDecor postDecor = snapshot.getValue(PostDecor.class);
                    postDecorList.add(postDecor);
                }
                callback.onPostDecorLoaded(postDecorList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface PostDecorCallback {
        void onPostDecorLoaded(List<PostDecor> postDecorList);

        void onError(String errorMessage);
    }
}
