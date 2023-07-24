package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationFirebase {
    private DatabaseReference databaseReference;

    public NotificationFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getNotification(final NotificationFirebase.NotificationCallback callback) {
        DatabaseReference postDecorRef = databaseReference.child("notification");
        final List<Notification> notificationList = new ArrayList<>();

        postDecorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificationList.clear(); // Xóa danh sách cũ trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification notification = snapshot.getValue(Notification.class);
                    notificationList.add(notification);
                }
                callback.onNotificationLoaded(notificationList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface NotificationCallback {
        void onNotificationLoaded(List<Notification> notificationList);

        void onError(String errorMessage);
    }
}
