package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Images;

import java.util.ArrayList;
import java.util.List;

public class ImagesFirebase {
    private DatabaseReference databaseReference;

    public ImagesFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getImages(final ImagesFirebase.ImagesCallback callback) {
        DatabaseReference imagesRef = databaseReference.child("images");
        final List<Images> imagesList = new ArrayList<>();

        imagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imagesList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Images images = snapshot.getValue(Images.class);
                    imagesList.add(images);
                }
                callback.onImagesLoaded(imagesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface ImagesCallback {
        void onImagesLoaded(List<Images> images);

        void onError(String errorMessage);
    }
}
