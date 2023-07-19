package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Detail_Utilities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Detail_UtilitiesFirebase {
    private DatabaseReference databaseReference;

    public Detail_UtilitiesFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getDetailUtilities(final Detail_UtilitiesFirebase.DetailUtilitiesCallback callback) {
        DatabaseReference detailUtilitiesRef = databaseReference.child("detail_utilities");
        final List<Detail_Utilities> detail_utilitiesList = new ArrayList<>();

        detailUtilitiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detail_utilitiesList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Detail_Utilities detail_utilities = snapshot.getValue(Detail_Utilities.class);
                    detail_utilitiesList.add(detail_utilities);
                }
                try {
                    callback.onDetailUtilitiesLoaded(detail_utilitiesList);
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

    public interface DetailUtilitiesCallback {
        void onDetailUtilitiesLoaded(List<Detail_Utilities> detail_utilitiesList) throws ParseException;

        void onError(String errorMessage);
    }}
