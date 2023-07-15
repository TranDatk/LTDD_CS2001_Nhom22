package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Posts;

import java.util.ArrayList;
import java.util.List;

public class PostsFirebase {
    private DatabaseReference databaseReference;

    public PostsFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getPosts(final PostsFirebase.PostsCallback callback) {
        DatabaseReference postsRef = databaseReference.child("posts");
        final List<Posts> postsList = new ArrayList<>();

        postsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postsList.clear(); // Xóa danh sách cũ trước khi thêm mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Posts posts = snapshot.getValue(Posts.class);
                    postsList.add(posts);
                }
                callback.onPostsLoaded(postsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface PostsCallback {
        void onPostsLoaded(List<Posts> postsList);

        void onError(String errorMessage);
    }
}
