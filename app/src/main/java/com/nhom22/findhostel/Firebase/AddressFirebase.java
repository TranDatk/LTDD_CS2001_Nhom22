package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressFirebase {
    private DatabaseReference databaseReference;

    public AddressFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getAddress(final AddressFirebase.AddressCallback callback) {
        DatabaseReference addressRef = databaseReference.child("address");
        final List<Address> addressList = new ArrayList<>();

        addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addressList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Address address = snapshot.getValue(Address.class);
                    addressList.add(address);
                }
                callback.onAddressLoaded(addressList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface AddressCallback {
        void onAddressLoaded(List<Address> addressesList);

        void onError(String errorMessage);
    }
}
