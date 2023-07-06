package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.ImagesDAO;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.YourApplication;

public class ImagesService {
    private final static ImagesDAO IMAGES_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        IMAGES_DAO = new ImagesDAO(appContext);
    }

    public Images getImagesById(int imagesId) {
        if(imagesId >= 0){
            return IMAGES_DAO.getImagesById(imagesId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null imagesId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
