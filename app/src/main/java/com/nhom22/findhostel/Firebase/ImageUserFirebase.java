package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageUserFirebase {
    private DatabaseReference databaseReference;

    public ImageUserFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getImageUser(final ImageUserFirebase.ImageUserCallback callback) {
        DatabaseReference imageUserRef = databaseReference.child("image_user_account");
        final List<ImageUser> imageUserList = new ArrayList<>();

        imageUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageUserList.clear(); // Xóa danh sách cũ trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageUser imageUser = snapshot.getValue(ImageUser.class);
                    imageUserList.add(imageUser);
                }
                callback.onImageUserLoaded(imageUserList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface ImageUserCallback {
        void onImageUserLoaded(List<ImageUser> imageUserList);

        void onError(String errorMessage);
    }
}
