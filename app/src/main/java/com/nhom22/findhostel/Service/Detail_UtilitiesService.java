package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.Detail_UtilitiesDAO;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Detail_UtilitiesService {
    private final static Detail_UtilitiesDAO DETAIL_UTILITIES_DAO;

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
}
