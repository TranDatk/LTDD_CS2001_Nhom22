package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.Detail_FurnitureDAO;
import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Detail_FurnitureService {
    private final static Detail_FurnitureDAO DETAIL_FURNITURE_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference detail_furnitureRef = database.getReference("detail_furniture");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        DETAIL_FURNITURE_DAO = new Detail_FurnitureDAO(appContext);
    }

    public Detail_Furniture getADetailFurnitureById(int detailFurnitureId) throws ParseException {
        if (detailFurnitureId >= 0) {
            return DETAIL_FURNITURE_DAO.getADetailFurnitureById(detailFurnitureId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailFurnitureId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Furniture> getListFurnitureByPostsId(int postsId) throws ParseException {
        if (postsId >= 0) {
            return DETAIL_FURNITURE_DAO.getListFurnitureByPostsId(postsId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public long addADetailFurniture(Detail_Furniture detailFurniture) throws ParseException {
        PostsService postsService = new PostsService();
        FurnitureService furnitureService = new FurnitureService();

        if (detailFurniture == null) {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailFurniture", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        } else if (detailFurniture.getQuantity() < 0) {
            Log.e("addADetailFurniture", "getQuantity() < 0");
            return -1;
        } else if (postsService.getPostById(detailFurniture.getPosts().getId()) == null) {
            Log.e("addADetailFurniture", "detailFurniture.getIdPost() not in Database");
            return -1;
        } else if (furnitureService.getFurnitureById(detailFurniture.getFurniture().getId()) == null) {
            Log.e("addADetailFurniture", "detailFurniture.getIdFurniture() not in Database");
            return -1;
        } else {
            long result = DETAIL_FURNITURE_DAO.addADetailFurniture(detailFurniture);
            if (result < 1) {
                Log.d("AddDetailFurniture", "Detail furniture uploaded failed");
            } else {
                Detail_Furniture detail_furnitureFirebase = new Detail_Furniture();
                detail_furnitureFirebase.setId(Integer.parseInt(String.valueOf(result)));
                detail_furnitureFirebase.setQuantity(detailFurniture.getQuantity());
                detailFurniture.getPosts().getUserAccount().setImage(null);
                detail_furnitureFirebase.setPosts(detailFurniture.getPosts());
                detail_furnitureFirebase.setFurniture(detailFurniture.getFurniture());


                detail_furnitureRef.child(String.valueOf(detail_furnitureFirebase.getId()))
                        .setValue(detail_furnitureFirebase);
            }
            return result;
        }
    }
    public void deleteAllDetailFurniture() {
        DETAIL_FURNITURE_DAO.deleteAllDetailFurniture();
    }



    public void resetDetailFurnitureAutoIncrement() {
        DETAIL_FURNITURE_DAO.resetDetailFurnitureAutoIncrement();
    }

    public List<Detail_Furniture> getAllDetailFurniture() throws ParseException {
        return DETAIL_FURNITURE_DAO.getAllDetailFurniture();
    }

    public List<Detail_Furniture> getListDetailFurnitureByPostId(int postsId) throws ParseException {
        if (postsId >= 0) {
            return DETAIL_FURNITURE_DAO.getListDetailFurnitureByPostId(postsId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
