package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.MainActivity;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

public class UserAccountService {
    private final static UserAccountDAO USER_ACCOUNT_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        USER_ACCOUNT_DAO = new UserAccountDAO(appContext);
    }

    public long addUserAccount(UserAccount userAccount){
        if(userAccount != null){
            return USER_ACCOUNT_DAO.addUserAccount(userAccount); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "User account is null", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }
}
