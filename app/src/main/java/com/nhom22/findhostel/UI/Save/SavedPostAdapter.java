package com.nhom22.findhostel.UI.Save;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;


import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.Save_PostService;

import java.text.ParseException;
import java.util.List;

public class SavedPostAdapter extends BaseAdapter {

    private List<Posts> items;
    private SavePageFragment fragment;

    public SavedPostAdapter(SavePageFragment fragment, List<Posts> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = fragment.getLayoutInflater();

        view = inflater.inflate(R.layout.item_saved_post, null);

        ImageView imgMain = view.findViewById(R.id.imgMain);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        Button btnPhoneNumber = view.findViewById(R.id.btnPhoneNumber);
        Button btnUnsave = view.findViewById(R.id.btnUnsave);

        Detail_ImageService detail_imageService = new Detail_ImageService();
        List<Images> images = null;
        try {
            images = detail_imageService.getListImageByPostsId(items.get(i).getId());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (images != null && !images.isEmpty()) {
            byte[] image = images.get(0).getImage();
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgMain.setImageBitmap(bitmap);
            }
        } else {
            // Handle the case when no images are available
            // For example, you can set a default image or hide the ImageView
            imgMain.setImageDrawable(null); // Set a default image or hide the ImageView
        }
//
//        if (!images.isEmpty()) {
//            byte[] image = images.get(0).getImage();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//            imgMain.setImageBitmap(bitmap);
//        }

        tvAddress.setText(items.get(i).getAddress().getHouseNumber() + ", " +
                items.get(i).getAddress().getStreets().getName() + ", " +
                items.get(i).getAddress().getSubDistrics().getName() + ", " +
                items.get(i).getAddress().getDistricts().getName() + ", " +
                items.get(i).getAddress().getCities().getName());
        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ");
        btnPhoneNumber.setText(items.get(i).getUserAccount().getPhone());

        btnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String np = items.get(i).getUserAccount().getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + np));
                fragment.startActivity(intent);
            }
        });

        btnUnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirm Unsave");
                builder.setMessage("Are you sure you want to unsave this post?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa bài đăng đã lưu và thực hiện các hành động khác tùy ý
                        Posts p = items.get(i);
                        int postID = p.getId();
                        Save_PostService save_postService = new Save_PostService();
                        try {
                            save_postService.deleteASavePostByUserIdAndPostId(1, postID);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(view.getContext(), "Unsaved successfully", Toast.LENGTH_SHORT).show();

                        items.remove(i);
                        notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Không thực hiện bất kỳ hành động nào và đóng hộp thoại
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        /*TextView tvItem = view.findViewById(R.id.tvItem);*/

        /*tvItem.setText(items[i]);*/

        return view;
    }
}
