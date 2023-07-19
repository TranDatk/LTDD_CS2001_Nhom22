package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Utilities;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesFirebase {
    private DatabaseReference databaseReference;

    public UtilitiesFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getUtilities(final UtilitiesFirebase.UtilitiesCallback callback) {
        DatabaseReference utilitiesRef = databaseReference.child("utilities");
        final List<Utilities> utilitiesList = new ArrayList<>();

        utilitiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                utilitiesList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Utilities utilities = snapshot.getValue(Utilities.class);
                    utilitiesList.add(utilities);
                }
                callback.onUtilitiesLoaded(utilitiesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface UtilitiesCallback {
        void onUtilitiesLoaded(List<Utilities> utilitiesList);

        void onError(String errorMessage);
    }
}
