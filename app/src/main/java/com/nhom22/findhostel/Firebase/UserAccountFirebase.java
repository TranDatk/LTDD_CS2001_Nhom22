package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserAccountFirebase {
    private DatabaseReference databaseReference;

    public UserAccountFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getUserAccount(final UserAccountFirebase.UserAccountCallback callback) {
        DatabaseReference userAccountRef = databaseReference.child("user_account");
        final List<UserAccount> userAccountList = new ArrayList<>();

        userAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userAccountList.clear(); // Xóa danh sách cũ trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserAccount userAccount = snapshot.getValue(UserAccount.class);
                    userAccountList.add(userAccount);
                }
                callback.onUserAccountLoaded(userAccountList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface UserAccountCallback {
        void onUserAccountLoaded(List<UserAccount> userAccountList);

        void onError(String errorMessage);
    }
}
