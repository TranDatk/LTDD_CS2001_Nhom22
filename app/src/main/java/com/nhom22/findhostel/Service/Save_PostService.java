package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.Save_PostDAO;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Save_PostService {
    private final static Save_PostDAO SAVE_POST_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference savePostsRef = database.getReference("save_post");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        SAVE_POST_DAO = new Save_PostDAO(appContext);
    }

    public Save_Post getSavePostById(int savePostId) throws ParseException {
        if (savePostId >= 0) {
            return SAVE_POST_DAO.getSavePostById(savePostId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public Save_Post getSavePostByPostIdAndUserAccountId(int postId, int userAccountId) throws ParseException {
        if (postId >= 0 && userAccountId >= 0) {
            return SAVE_POST_DAO.getSavePostByPostIdAndUserAccountId(postId, userAccountId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId or userAccountId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public long addASavePost(int postId, int userAccountId) throws ParseException {
        if (postId >= 1 && userAccountId >= 1) {

            long result = SAVE_POST_DAO.addASavePost(postId, userAccountId);

            if (result < 1) {
                Log.d("AddASavePost", "Save post uploaded failed");

            } else {
                PostsService postsService = new PostsService();
                UserAccountService userAccountService = new UserAccountService();

                Save_Post savePostFirebase = new Save_Post();
                savePostFirebase.setId(Integer.parseInt(String.valueOf(result)));
                savePostFirebase.setPosts(postsService.getPostById(postId));
                savePostFirebase.getPosts().getUserAccount().setImage(null);
                savePostFirebase.setUserAccount(userAccountService.getUserAccountById(userAccountId));
                savePostFirebase.getUserAccount().setImage(null);

                savePostsRef.child(String.valueOf(savePostFirebase.getId()))
                        .setValue(savePostFirebase);

                return result;
            }
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId or userAccountId", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public long deleteASavePost(int savePostId) {
        if (savePostId >= 0) {
            return SAVE_POST_DAO.deleteASavePost(savePostId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public long deleteASavePostByUserIdAndPostId(int userId, int postId) throws ParseException {
        if (userId >= 0 && postId >= 0) {
            if(getSavePostByPostIdAndUserAccountId(postId,userId) != null){
                DatabaseReference savePostRef = savePostsRef.child(
                        String.valueOf(getSavePostByPostIdAndUserAccountId(postId,userId).getId()));
                savePostRef.removeValue()
                        .addOnSuccessListener(aVoid -> {

                        })
                        .addOnFailureListener(e -> {
                            // Deletion failed
                            // You can handle the failure here, like displaying an error toast.
                            Context context = YourApplication.getInstance().getApplicationContext();
                            Toast.makeText(context, "Failed to delete save_post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                long result = SAVE_POST_DAO.deleteASavePostByUserIdAndPostId(userId, postId);
                return result; // -1 Unsuccessful, >0 Successful
            }
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(YourApplication.getInstance().getApplicationContext(), "Null savePostId", Toast.LENGTH_SHORT).show();
        return -1;  // Return -1 to indicate unsuccessful operation
    }

    public List<Save_Post> getListSavePostByUserAccountId(int userId) throws ParseException {
        if (userId >= 0) {
            return SAVE_POST_DAO.getListSavePostByUserAccountId(userId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null userId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Posts> getListPostsByUserAccountId(int userId) throws ParseException {
        if (userId >= 0) {
            return SAVE_POST_DAO.getListPostsByUserAccountId(userId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null userId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllSavePost() {
        SAVE_POST_DAO.deleteAllSavePost();
    }

    public void resetSavePostAutoIncrement() {
        SAVE_POST_DAO.resetSavePostAutoIncrement();
    }
}
