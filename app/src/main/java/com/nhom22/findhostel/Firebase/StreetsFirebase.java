package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Streets;

import java.util.ArrayList;
import java.util.List;

public class StreetsFirebase {
    private DatabaseReference databaseReference;

    public StreetsFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getStreets(final StreetsFirebase.StreetsCallback callback) {

        DatabaseReference streetsRef = databaseReference.child("streets");
        final List<Streets> streetsList = new ArrayList<>();

        streetsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                streetsList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Streets streets = snapshot.getValue(Streets.class);
                    streetsList.add(streets);
                }
                callback.onStreetsLoaded(streetsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface StreetsCallback {
        void onStreetsLoaded(List<Streets> streetsList);

        void onError(String errorMessage);
    }
}
