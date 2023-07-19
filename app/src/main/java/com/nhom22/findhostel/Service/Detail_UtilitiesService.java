package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.Detail_UtilitiesDAO;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Detail_UtilitiesService {
    private final static Detail_UtilitiesDAO DETAIL_UTILITIES_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference detailUtilitiesRef = database.getReference("detail_utilities");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        DETAIL_UTILITIES_DAO = new Detail_UtilitiesDAO(appContext);
    }

    public Detail_Utilities getADetailUtilitiesById(int detailUtilitiesId) throws ParseException {
        if(detailUtilitiesId >= 0){
            return DETAIL_UTILITIES_DAO.getADetailUtilitiesById(detailUtilitiesId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailUtilitiesId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Utilities> getListUtilitiesByPostsId(int postsId) throws ParseException {
        if(postsId >= 0){
            return DETAIL_UTILITIES_DAO.getListUtilitiesByPostsId(postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Utilities> getAllUtilities() {
        return DETAIL_UTILITIES_DAO.getAllUtilities();
    }

    public long addADetailUtilities(Detail_Utilities detail_utilities) {
        PostsService postsService = new PostsService();
        Detail_UtilitiesService detailUtilitiesService = new Detail_UtilitiesService();

        if (detail_utilities == null) {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailUtilities", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        } else {
            long result = DETAIL_UTILITIES_DAO.addADetailUtilities(detail_utilities);
            if (result < 1) {
                Log.d("AddDetailUtilities", "Detail utilities uploaded failed");
            } else {
                Detail_Utilities detail_UtilitiesFirebase = new Detail_Utilities();
                detail_UtilitiesFirebase.setId(Integer.parseInt(String.valueOf(result)));
                detail_UtilitiesFirebase.setPrice(detail_utilities.getPrice());
                detail_UtilitiesFirebase.setUnit(detail_utilities.getUnit());
                detail_UtilitiesFirebase.setPosts(detail_utilities.getPosts());
                detail_UtilitiesFirebase.getPosts().getUserAccount().setImage(null);
                detail_UtilitiesFirebase.setUtilities(detail_utilities.getUtilities());


                detailUtilitiesRef.child(String.valueOf(detail_UtilitiesFirebase.getId()))
                        .setValue(detail_UtilitiesFirebase);
            }
            return result;
        }
    }

    public void deleteAllDetailUtilities() {
        DETAIL_UTILITIES_DAO.deleteAllDetailUtilities();
    }

    public void resetDetailUtilitiesAutoIncrement() {
        DETAIL_UTILITIES_DAO.resetDetailUtilitiesAutoIncrement();
    }
}
