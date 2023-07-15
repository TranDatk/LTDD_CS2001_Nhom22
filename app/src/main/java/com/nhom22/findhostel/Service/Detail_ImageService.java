package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.Detail_ImageDAO;
import com.nhom22.findhostel.Model.Detail_Image;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class Detail_ImageService {
    private final static Detail_ImageDAO DETAIL_IMAGE_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference detail_imageRef = database.getReference("detail_image");

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

    public long addADetailImage(int imagesId, int postsId) throws ParseException {
        if(imagesId >= 0 && postsId >=0){
            long result = DETAIL_IMAGE_DAO.addADetailImage(imagesId, postsId);
            if(result < 1){
                Log.d("AddDetail", "DetailImage uploaded failed");
            }
            else {
                ImagesService imagesService = new ImagesService();
                PostsService postsService = new PostsService();

                Detail_Image detail_imageFirebase = new Detail_Image(Integer.parseInt(String.valueOf(result)),
                        imagesService.getImagesById(imagesId), postsService.getPostById(postsId));

                detail_imageRef.child(String.valueOf(detail_imageFirebase.getId())).setValue(detail_imageFirebase);
            }

            return result; // -1 Unsuccessful, >0 Successful
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

    public void deleteAllDetailImage() {
        DETAIL_IMAGE_DAO.deleteAllDetailImage();
    }

    public void resetDetailImageAutoIncrement() {
        DETAIL_IMAGE_DAO.resetDetailImageAutoIncrement();
    }
}
