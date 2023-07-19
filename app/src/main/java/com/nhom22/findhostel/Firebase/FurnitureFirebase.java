package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Furniture;

import java.util.ArrayList;
import java.util.List;

public class FurnitureFirebase {
    private DatabaseReference databaseReference;

    public FurnitureFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getFurniture(final FurnitureFirebase.FurnitureCallback callback) {
        DatabaseReference furnitureRef = databaseReference.child("furniture");
        final List<Furniture> furnitureList = new ArrayList<>();

        furnitureRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                furnitureList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Furniture furniture = snapshot.getValue(Furniture.class);
                    furnitureList.add(furniture);
                }
                callback.onFurnitureLoaded(furnitureList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface FurnitureCallback {
        void onFurnitureLoaded(List<Furniture> furnitureList);

        void onError(String errorMessage);
    }
}
