package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;


public class UserAccountService {
    private final static UserAccountDAO USER_ACCOUNT_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference userAccountRef = database.getReference("user_account");
    DatabaseReference imageUserAccountRef = database.getReference("image_user_account");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        USER_ACCOUNT_DAO = new UserAccountDAO(appContext);
    }

    public long addUserAccount(UserAccount userAccount) {
        if (userAccount != null) {
            UserAccount userAccountFirebase = new UserAccount(userAccount.getId(),userAccount.getUsername(),
                    userAccount.getEmail(),userAccount.getPassword(),userAccount.getPhone(),
                    userAccount.getDigital_money(),userAccount.getRoleUser(), null, userAccount.getIsActive(), userAccount.getAddress());
            userAccountFirebase.setImage(null);

            return USER_ACCOUNT_DAO.addUserAccount(userAccount); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "User account is null", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public UserAccount getUserAccountById(Integer userAccountId) {
        if (userAccountId != null) {
            return USER_ACCOUNT_DAO.getUserAccountById(userAccountId); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "userAccountId is null", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllUserAccount() {
        USER_ACCOUNT_DAO.deleteAllUserAccount();
    }

    public void resetUserAccountAutoIncrement() {
        USER_ACCOUNT_DAO.resetUserAccountAutoIncrement();
    }

    public void insertImageUserAccount(int idUserAccount, byte[] image) {
        USER_ACCOUNT_DAO.insertImageUserAccount(idUserAccount,image);
    }


    public UserAccount checkLoginUser(String email, String password) {
        if (email != null && password != null) {
            return USER_ACCOUNT_DAO.checkUserLogin(email, password); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Email or password is null", Toast.LENGTH_SHORT).show();
            return null; // Return null to indicate unsuccessful operation
        }
    }
}
