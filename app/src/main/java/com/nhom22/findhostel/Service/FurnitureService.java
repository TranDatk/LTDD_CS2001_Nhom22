package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.FurnitureDAO;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class FurnitureService {
    private final static FurnitureDAO FURNITURE_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference furnitureRef = database.getReference("furniture");

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

    public List<Furniture> getAllFurniture() {
        List<Furniture> furnitureList = FURNITURE_DAO.getAllFurniture();
        if (furnitureList != null && !furnitureList.isEmpty()) {
            return furnitureList;
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "No furniture items found", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public long addAFurniture(Furniture furniture) {
        if (furniture != null) {
            long result = FURNITURE_DAO.addAFurniture(furniture);
            if(result < 1){
                Log.d("AddFurniture", "furniture uploaded failed");
            }else{
                Furniture furnitureFirebase = new Furniture();
                furnitureFirebase.setId(Integer.parseInt(String.valueOf(result)));
                furnitureFirebase.setName(furniture.getName());
                furnitureFirebase.setIsActive(furniture.getIsActive());

                furnitureRef.child(String.valueOf(furnitureFirebase.getId())).setValue(furnitureFirebase);
            }
            return result;
        }else{
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null furniture", Toast.LENGTH_SHORT).show();
            return -1;
        }

    }

    public void deleteAllFurniture() {
        FURNITURE_DAO.deleteAllFurniture();
    }

    public void resetFurnitureAutoIncrement() {
        FURNITURE_DAO.resetFurnitureAutoIncrement();
    }
}
