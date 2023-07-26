package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhom22.findhostel.Data.ImagesDAO;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class ImagesService {
    private final static ImagesDAO IMAGES_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference imagesRef = database.getReference("images");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        IMAGES_DAO = new ImagesDAO(appContext);
    }

    public Images getImagesById(int imagesId) {
        if (imagesId >= 0) {
            return IMAGES_DAO.getImagesById(imagesId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null imagesId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Images> getAllImagesByName(String name) {
        List<Images> imagesList = IMAGES_DAO.getAllImagesByName(name);

        if (imagesList == null || imagesList.isEmpty()) {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "No images found with the given name", Toast.LENGTH_SHORT).show();
        }

        return imagesList;
    }
    public long addAImages(Images images) {
        if (images != null) {
            long result = IMAGES_DAO.addAImages(images);
            if (result < 1) {
                Log.e("AddImagesToSqlite", "Failed to add Images to Sqlite");
            } else {
                Images imagesFirebase = new Images(Integer.parseInt(String.valueOf(result)), images.getName(),
                        null, images.getIsActive());

                imagesRef.child(String.valueOf(imagesFirebase.getId())).setValue(imagesFirebase);

                // Tải lên ảnh lên Firebase Storage
                byte[] imageData = images.getImage();
                if (imageData != null) {
                    String fileName = imagesFirebase.getId() + ".png";
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("images").child(fileName);

                    storageRef.putBytes(imageData)
                            .addOnSuccessListener(taskSnapshot -> {
                                // Ảnh đã được tải lên thành công
                                Log.d("AddImagesToFirebase", "Image uploaded successfully");
                            })
                            .addOnFailureListener(exception -> {
                                // Xảy ra lỗi khi tải lên ảnh
                                String errorMessage = exception.getMessage();
                                Log.e("AddImagesToFirebase", "Failed to upload image: " + errorMessage);
                            });
                }
            }

            return result; // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null post", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllImages() {
        IMAGES_DAO.deleteAllImages();
    }

    public void resetImagesAutoIncrement() {
        IMAGES_DAO.resetImagesAutoIncrement();
    }

    public void insertImages(int idImages, byte[] image) {
        IMAGES_DAO.insertImages(idImages, image);
    }
}
