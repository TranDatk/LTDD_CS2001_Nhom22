package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhom22.findhostel.Data.PostDecorDAO;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class PostDecorService {
    private final static PostDecorDAO POST_DECOR_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference postDecorRef = database.getReference("post_decor");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        POST_DECOR_DAO = new PostDecorDAO(appContext);
    }

    public long addPostDecor(PostDecor postDecor) {
        if(postDecor != null){
            long result = POST_DECOR_DAO.addPostDecor(postDecor);;
            if (result < 1) {
                Log.e("addPostDecor", "Failed to add Post Decor to Sqlite");
            } else {

                PostDecor postDecorFirebase = new PostDecor(Integer.parseInt(String.valueOf(result)), postDecor.getText(),
                        null, postDecor.getCreatedDate(), postDecor.getUserId(), postDecor.getIsActive());

                postDecorRef.child(String.valueOf(postDecorFirebase.getId())).setValue(postDecorFirebase);

                // Tải lên ảnh lên Firebase Storage
                byte[] imageData = postDecor.getImage();
                if (imageData != null) {
                    String fileName = postDecorFirebase.getId() + ".png";
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("post_decorImage").child(fileName);

                    storageRef.putBytes(imageData)
                            .addOnSuccessListener(taskSnapshot -> {
                                // Ảnh đã được tải lên thành công
                                Log.d("AddPostDecotImage", "Image uploaded successfully");
                            })
                            .addOnFailureListener(exception -> {
                                // Xảy ra lỗi khi tải lên ảnh
                                String errorMessage = exception.getMessage();
                                Log.e("AddPostDecotImage", "Failed to upload image: " + errorMessage);
                            });
                }
                return result;
            }
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postDecor", Toast.LENGTH_SHORT).show();
        }
        return -1; // Return -1 to indicate unsuccessful operation
    }

    public List<PostDecor> getAllPostDecor(){
        return POST_DECOR_DAO.getAllPostDecor();
    }

    public void deleteAllPostDecor() {
        POST_DECOR_DAO.deleteAllPostDecor();
    }

    public void resetPostDecorAutoIncrement() {
        POST_DECOR_DAO.resetPostDecorAutoIncrement();
    }

    public void insertImagePostDecor(int idPostDecor, byte[] image) {
        if(idPostDecor > 0 && image != null){
            POST_DECOR_DAO.insertImagePostDecor(idPostDecor,image);
        }else{
            Toast.makeText(YourApplication.getInstance().getApplicationContext(),"No idPostDecor or image",Toast.LENGTH_LONG).show();
        }
    }
}
