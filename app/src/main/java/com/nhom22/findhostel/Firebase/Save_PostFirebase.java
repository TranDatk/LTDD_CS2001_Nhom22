package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Save_Post;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Save_PostFirebase {
    private DatabaseReference databaseReference;

    public Save_PostFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getSavePost(final Save_PostFirebase.SavePostCallback callback) {
        DatabaseReference savePostRef = databaseReference.child("save_post");
        final List<Save_Post> save_postList = new ArrayList<>();

        savePostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                save_postList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Save_Post savePost = snapshot.getValue(Save_Post.class);
                    save_postList.add(savePost);
                }
                try {
                    callback.onSavePostLoaded(save_postList);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface SavePostCallback {
        void onSavePostLoaded(List<Save_Post> save_postList) throws ParseException;

        void onError(String errorMessage);
    }
}
