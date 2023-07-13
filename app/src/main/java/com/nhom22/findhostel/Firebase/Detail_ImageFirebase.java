package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Detail_Image;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Detail_ImageFirebase {
    private DatabaseReference databaseReference;

    public Detail_ImageFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getDetailImage(final Detail_ImageFirebase.DetailImageCallback callback) {
        DatabaseReference detail_imageRef = databaseReference.child("detail_image");
        final List<Detail_Image> detail_imageList = new ArrayList<>();

        detail_imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detail_imageList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Detail_Image detail_image = snapshot.getValue(Detail_Image.class);
                    detail_imageList.add(detail_image);
                }
                try {
                    callback.onDetailImageLoaded(detail_imageList);
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

    public interface DetailImageCallback {
        void onDetailImageLoaded(List<Detail_Image> detail_imageList) throws ParseException;

        void onError(String errorMessage);
    }
}
