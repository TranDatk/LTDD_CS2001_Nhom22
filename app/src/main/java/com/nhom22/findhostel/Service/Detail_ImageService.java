package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.Detail_ImageDAO;
import com.nhom22.findhostel.Model.Detail_Image;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Detail_ImageService {
    private final static Detail_ImageDAO DETAIL_IMAGE_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        DETAIL_IMAGE_DAO = new Detail_ImageDAO(appContext);
    }

    public Detail_Image getDetailImageById(int detailImageId) throws ParseException {
        if(detailImageId >= 0){
            return DETAIL_IMAGE_DAO.getDetailImageById(detailImageId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailImageId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public Detail_Image getDetailImageByImagesIdAndPostsId(int imagesId, int postsId) throws ParseException {
        if(imagesId >= 0 && postsId >=0){
            return DETAIL_IMAGE_DAO.getDetailImageByImagesIdAndPostsId(imagesId, postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null imagesId or postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public long addADetailImage(int imagesId, int postsId) {
        if(imagesId >= 0 && postsId >=0){
            return DETAIL_IMAGE_DAO.addADetailImage(imagesId, postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null imagesId or postsId", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public long deleteADetailImage(int detailImageId) {
        if(detailImageId >= 0){
            return DETAIL_IMAGE_DAO.deleteADetailImage(detailImageId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null detailImageId", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Detail_Image> getListDetailImageByPostsId(int postsId) throws ParseException {
        if(postsId >= 0){
            return DETAIL_IMAGE_DAO.getListDetailImageByPostsId(postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Images> getListImageByPostsId(int postsId) throws ParseException {
        if(postsId >= 0){
            return DETAIL_IMAGE_DAO.getListImageByPostsId(postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
