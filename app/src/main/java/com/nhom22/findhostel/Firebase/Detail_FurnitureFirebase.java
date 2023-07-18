package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Detail_Furniture;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Detail_FurnitureFirebase {
    private DatabaseReference databaseReference;

    public Detail_FurnitureFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getDetailFurniture(final Detail_FurnitureFirebase.DetailFurnitureCallback callback) {
        DatabaseReference detail_furnitureRef = databaseReference.child("detail_furniture");
        final List<Detail_Furniture> detail_furnitureList = new ArrayList<>();

        detail_furnitureRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detail_furnitureList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Detail_Furniture detail_furniture = snapshot.getValue(Detail_Furniture.class);
                    detail_furnitureList.add(detail_furniture);
                }
                try {
                    callback.onDetailFurnitureLoaded(detail_furnitureList);
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

    public interface DetailFurnitureCallback {
        void onDetailFurnitureLoaded(List<Detail_Furniture> detail_furnitureList) throws ParseException;

        void onError(String errorMessage);
    }
}
