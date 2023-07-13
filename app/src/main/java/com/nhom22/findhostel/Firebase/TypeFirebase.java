package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeFirebase {
    private DatabaseReference databaseReference;

    public TypeFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getType(final TypeFirebase.TypeCallback callback) {
        DatabaseReference typeRef = databaseReference.child("type");
        final List<Type> typeList = new ArrayList<>();

        typeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                typeList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Type type = snapshot.getValue(Type.class);
                    typeList.add(type);
                }
                callback.onTypeLoaded(typeList);;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface TypeCallback {
        void onTypeLoaded(List<Type> typeList);

        void onError(String errorMessage);
    }
}
