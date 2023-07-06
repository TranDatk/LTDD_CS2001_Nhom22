package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.UtilitiesDAO;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.YourApplication;

public class UtilitiesService {
    private final static UtilitiesDAO UTILITIES_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        UTILITIES_DAO = new UtilitiesDAO(appContext);
    }

    public Utilities getUtilitiesById(int utilitiesId) {
        if(utilitiesId >= 0){
            return UTILITIES_DAO.getUtilitiesById(utilitiesId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null utilitiesId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

}
