package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.SubDistricts;

import java.util.ArrayList;
import java.util.List;

public class SubDistrictsFirebase {
    private DatabaseReference databaseReference;

    public SubDistrictsFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getSubDistricts(final SubDistrictsFirebase.SubDistrictsCallback callback) {

        DatabaseReference subDistrictsRef = databaseReference.child("sub_districts");
        final List<SubDistricts> subDistrictsList = new ArrayList<>();

        subDistrictsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subDistrictsList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SubDistricts subDistricts = snapshot.getValue(SubDistricts.class);
                    subDistrictsList.add(subDistricts);
                }
                callback.onSubDistrictsLoaded(subDistrictsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface SubDistrictsCallback {
        void onSubDistrictsLoaded(List<SubDistricts> subDistrictsList);

        void onError(String errorMessage);
    }
}
