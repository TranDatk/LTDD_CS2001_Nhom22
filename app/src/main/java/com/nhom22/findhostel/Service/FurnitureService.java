package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.AddressDAO;
import com.nhom22.findhostel.Data.FurnitureDAO;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.YourApplication;

public class FurnitureService {
    private final static FurnitureDAO FURNITURE_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        FURNITURE_DAO = new FurnitureDAO(appContext);
    }

    public Furniture getFurnitureById(int furnitureId) {
        if(furnitureId >= 0){
            return FURNITURE_DAO.getFurnitureById(furnitureId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null furnitureId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
