package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.HostelCollection;

import java.util.ArrayList;
import java.util.List;

public class HostelCollectionFirebase {
    private DatabaseReference databaseReference;

    public HostelCollectionFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getHostelCollection(final HostelCollectionFirebase.HostelCollectionCallback callback) {
        DatabaseReference hostelCollectionRef = databaseReference.child("hostel_collection");
        final List<HostelCollection> hostelCollectionList = new ArrayList<>();

        hostelCollectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hostelCollectionList.clear(); // Xóa danh sách cũ trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HostelCollection hostelCollection = snapshot.getValue(HostelCollection.class);
                    hostelCollectionList.add(hostelCollection);
                }
                callback.onHostelCollectionLoaded(hostelCollectionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface HostelCollectionCallback {
        void onHostelCollectionLoaded(List<HostelCollection> hostelCollectionList);

        void onError(String errorMessage);
    }
}
