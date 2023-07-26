package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhom22.findhostel.Data.HostelCollectionDAO;
import com.nhom22.findhostel.Model.HostelCollection;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class HostelCollectionService {
    private final static HostelCollectionDAO HOSTEL_COLLECTION_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference hostelCollectionRef = database.getReference("hostel_collection");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        HOSTEL_COLLECTION_DAO = new HostelCollectionDAO(appContext);
    }

    public long addHostelCollection(HostelCollection hostelCollection) {
        if (hostelCollection != null) {
            long result = HOSTEL_COLLECTION_DAO.addHostelCollection(hostelCollection);

            if (result > 0) {
                byte[] imageData = hostelCollection.getImage();

                hostelCollection.setId(Integer.parseInt(String.valueOf(result)));

                hostelCollection.setImage(null);

                hostelCollectionRef.child(String.valueOf(hostelCollection.getId())).setValue(hostelCollection);

                // Tải lên ảnh lên Firebase Storage

                if (imageData != null) {
                    String fileName = hostelCollection.getId() + ".png";
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("hostel_collectionImage").child(fileName);

                    storageRef.putBytes(imageData)
                            .addOnSuccessListener(taskSnapshot -> {
                                // Ảnh đã được tải lên thành công
                                Log.d("AddHostelCollectionImage", "Image uploaded successfully");
                            })
                            .addOnFailureListener(exception -> {
                                // Xảy ra lỗi khi tải lên ảnh
                                String errorMessage = exception.getMessage();
                                Log.e("AddHostelCollectionImage", "Failed to upload image: " + errorMessage);
                            });

                }
            }
            Log.e("addHostelCollection", String.valueOf(result));
            return result; // -1 Unsuccessful, >0 Successful

        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null hostelCollection", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }


    public HostelCollection getHostelCollectionById(Integer id) {
        if (id != null) {
            return HOSTEL_COLLECTION_DAO.getHostelCollectionById(id); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null hostelCollection", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<HostelCollection> getAllHostelCollection() {
        return HOSTEL_COLLECTION_DAO.getAllHostelCollection();
    }

    public void deleteAllHostelCollection() {
        HOSTEL_COLLECTION_DAO.deleteAllHostelCollection();
    }

    public void resetHostelCollectionAutoIncrement() {
        HOSTEL_COLLECTION_DAO.resetHostelCollectionAutoIncrement();
    }

    public void insertImageHostelCollection(int idHostelCollection, byte[] image) {
        if(getHostelCollectionById(idHostelCollection) != null){
            HOSTEL_COLLECTION_DAO.insertImageHostelCollection(idHostelCollection, image);
        }
        else{
            Toast.makeText(YourApplication.getInstance().getApplicationContext(),
                    "Null idHostelCollection", Toast.LENGTH_SHORT).show();
        }
    }
}
