package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.Detail_FurnitureDAO;
import com.nhom22.findhostel.Data.Save_PostDAO;
import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Detail_FurnitureService {
    private final static Detail_FurnitureDAO DETAIL_FURNITURE_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        DETAIL_FURNITURE_DAO = new Detail_FurnitureDAO(appContext);
    }

    public Detail_Furniture getADetailFurnitureById(int detailFurnitureId) throws ParseException {
        if(detailFurnitureId >= 0){
            return DETAIL_FURNITURE_DAO.getADetailFurnitureById(detailFurnitureId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailFurnitureId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Furniture> getListFurnitureByPostsId(int postsId) throws ParseException {
        if(postsId >= 0){
            return DETAIL_FURNITURE_DAO.getListFurnitureByPostsId(postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
