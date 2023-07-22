package com.nhom22.findhostel.Firebase;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Save_PostFirebase {
    private DatabaseReference databaseReference;

    public Save_PostFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getSavePost(final Save_PostFirebase.SavePostCallback callback) {
        DatabaseReference savePostRef = databaseReference.child("save_post");
        final List<Save_Post> save_postList = new ArrayList<>();

        savePostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                save_postList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Save_Post savePost = snapshot.getValue(Save_Post.class);
                    save_postList.add(savePost);
                }
                try {
                    callback.onSavePostLoaded(save_postList);
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

    public void resetFirebaseIds(String table) {
        DatabaseReference savePostsRef = FirebaseDatabase.getInstance().getReference(table);

        // Lấy dữ liệu từ Firebase Realtime Database và sắp xếp lại theo ID
        savePostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<DataSnapshot> snapshotList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshotList.add(snapshot);
                }

                // Sắp xếp danh sách theo ID
                Collections.sort(snapshotList, (snapshot1, snapshot2) -> {
                    int id1 = Integer.parseInt(snapshot1.getKey());
                    int id2 = Integer.parseInt(snapshot2.getKey());
                    return Integer.compare(id1, id2);
                });

                // Cập nhật lại giá trị ID cho mỗi nút theo số thứ tự mới
                int newId = 1;
                for (DataSnapshot snapshot : snapshotList) {
                    savePostsRef.child(String.valueOf(newId)).setValue(snapshot.getValue());
                    newId++;
                }
                Toast.makeText(YourApplication.getInstance().getApplicationContext(),String.valueOf(newId),Toast.LENGTH_LONG);

                // Xóa giá trị cuối cùng nếu cần thiết
                if(!snapshotList.isEmpty()){
                    int lastId = Integer.parseInt(snapshotList.get(snapshotList.size() - 1).getKey());
                    if (lastId > snapshotList.size()) {
                        savePostsRef.child(String.valueOf(lastId)).removeValue();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi khi đọc dữ liệu từ Firebase Realtime Database
            }
        });
    }

    public interface SavePostCallback {
        void onSavePostLoaded(List<Save_Post> save_postList) throws ParseException;

        void onError(String errorMessage);
    }
}
