package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.util.List;


public class UserAccountService {
    private final static UserAccountDAO USER_ACCOUNT_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference userAccountRef = database.getReference("user_account");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        USER_ACCOUNT_DAO = new UserAccountDAO(appContext);
    }

    public long addUserAccount(UserAccount userAccount) {
        if (userAccount != null) {
            long result = USER_ACCOUNT_DAO.addUserAccount(userAccount);
            if (result < 1) {
                Log.e("AddUserAccount", "Failed to add UserAccount to Sqlite");
            } else {

                UserAccount userAccountFirebase = new UserAccount(Integer.parseInt(String.valueOf(result)), userAccount.getUsername(),
                        userAccount.getEmail(), userAccount.getPassword(), userAccount.getPhone(),
                        userAccount.getDigital_money(), userAccount.getRoleUser(), null, userAccount.getIsActive(), userAccount.getAddress());

                userAccountRef.child(String.valueOf(userAccountFirebase.getId())).setValue(userAccountFirebase);

                // Tải lên ảnh lên Firebase Storage
                byte[] imageData = userAccount.getImage();
                if (imageData != null) {
                    String fileName = userAccountFirebase.getId() + ".png";
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("image_user_account").child(fileName);

                    storageRef.putBytes(imageData)
                            .addOnSuccessListener(taskSnapshot -> {
                                // Ảnh đã được tải lên thành công
                                Log.d("AddImageUserAccount", "Image uploaded successfully");
                            })
                            .addOnFailureListener(exception -> {
                                // Xảy ra lỗi khi tải lên ảnh
                                String errorMessage = exception.getMessage();
                                Log.e("AddImageUserAccount", "Failed to upload image: " + errorMessage);
                            });
                }
            }
            return result; // -1 Unsuccessful, >0 Successful

        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "User account is null", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public long updateUserAccount(UserAccount userAccount) {
        if (userAccount != null) {
            long result = USER_ACCOUNT_DAO.updateUserAccount(userAccount);
            if (result < 1) {
                Log.e("UpdateUserAccount", "Failed to update UserAccount in Sqlite");
            } else {
                UserAccount userAccountFirebase = new UserAccount(userAccount.getId(), userAccount.getUsername(),
                        userAccount.getEmail(), userAccount.getPassword(), userAccount.getPhone(),
                        userAccount.getDigital_money(), userAccount.getRoleUser(), null, userAccount.getIsActive(), userAccount.getAddress());

                userAccountRef.child(String.valueOf(userAccountFirebase.getId())).setValue(userAccountFirebase);
            }
            return result; // -1 Unsuccessful, >0 Successful
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

    public List<String> getAllEmailUser() {
        return USER_ACCOUNT_DAO.getAllEmailUser();
    }

    public void deleteAllUserAccount() {
        USER_ACCOUNT_DAO.deleteAllUserAccount();
    }

    public void resetUserAccountAutoIncrement() {
        USER_ACCOUNT_DAO.resetUserAccountAutoIncrement();
    }

    public void insertImageUserAccount(int idUserAccount, byte[] image) {
        USER_ACCOUNT_DAO.insertImageUserAccount(idUserAccount, image);
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

    public List<UserAccount> getAllUserAccountByDistrictId(Integer districs_id){
        if (districs_id != null) {
            return USER_ACCOUNT_DAO.getAllUserAccountByDistrictId(districs_id); // -1 Unsuccessful, >0 Successful
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Email or password is null", Toast.LENGTH_SHORT).show();
            return null; // Return null to indicate unsuccessful operation
        }
    }
}
