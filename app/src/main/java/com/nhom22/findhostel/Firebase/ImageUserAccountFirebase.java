package com.nhom22.findhostel.Firebase;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageUserAccountFirebase {

    public interface ImageDownloadListener {
        void onImageDownloaded(byte[] imageData);
        void onImageDownloadFailed(String errorMessage);
    }

    public static void getImageUserAccount(String imageName, final ImageDownloadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("image_user_account").child(imageName);

        final long MAX_SIZE_BYTES = 1024 * 1024; // Maximum image size set to 1MB

        storageRef.getBytes(MAX_SIZE_BYTES).addOnSuccessListener(bytes -> {
            listener.onImageDownloaded(bytes);
        }).addOnFailureListener(exception -> {
            String errorMessage = exception.getMessage();
            Log.e("ImageUserAccount", "Failed to download image: " + errorMessage);
            listener.onImageDownloadFailed(errorMessage);
        });
    }

    public static void getImages(String imageName, final ImageDownloadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(imageName);

        final long MAX_SIZE_BYTES = 1024 * 1024; // Maximum image size set to 1MB

        storageRef.getBytes(MAX_SIZE_BYTES).addOnSuccessListener(bytes -> {
            listener.onImageDownloaded(bytes);
        }).addOnFailureListener(exception -> {
            String errorMessage = exception.getMessage();
            Log.e("ImageUserAccount", "Failed to download image: " + errorMessage);
            listener.onImageDownloadFailed(errorMessage);
        });
    }
}
