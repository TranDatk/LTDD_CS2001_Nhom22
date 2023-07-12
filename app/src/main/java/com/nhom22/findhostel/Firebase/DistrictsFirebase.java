package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Districts;

import java.util.ArrayList;
import java.util.List;

public class DistrictsFirebase {
    private DatabaseReference databaseReference;

    public DistrictsFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getDistricts(final DistrictsCallback callback) {

        DatabaseReference districtsRef = databaseReference.child("districts");
        final List<Districts> districtsList = new ArrayList<>();

        districtsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                districtsList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Districts district = snapshot.getValue(Districts.class);
                    districtsList.add(district);
                }
                callback.onDistrictsLoaded(districtsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface DistrictsCallback {
        void onDistrictsLoaded(List<Districts> districts);

        void onError(String errorMessage);
    }
}
