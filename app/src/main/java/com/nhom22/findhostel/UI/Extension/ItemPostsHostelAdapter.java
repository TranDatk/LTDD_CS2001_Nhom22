package com.nhom22.findhostel.UI.Extension;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;

import java.text.ParseException;
import java.util.List;

public class ItemPostsHostelAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Posts> itemHostelPosts;

    private UserAccount user;

    public static UserAccountDAO accountDAO;

    public ItemPostsHostelAdapter(Context context, int layout, List<Posts> itemPost) {
        this.context = context;
        this.layout = layout;
        this.itemHostelPosts = itemPost;
    }

    @Override
    public int getCount() {
        return itemHostelPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView tvPrice, tvType, tvAddress;
        ImageView imgItemHostelPost;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemPostsHostelAdapter.ViewHolder holder;
        if(convertView == null){
            holder = new ItemPostsHostelAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress) ;
            holder.imgItemHostelPost = (ImageView) convertView.findViewById(R.id.imgMain);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemPostsHostelAdapter.ViewHolder) convertView.getTag();
        }

        Posts posts = itemHostelPosts.get(position);

        holder.tvPrice.setText(String.valueOf(posts.getPrice()));
        holder.tvType.setText(posts.getType().getName());
        holder.tvAddress.setText(posts.getAddress().getHouseNumber() + ", " +
                posts.getAddress().getStreets().getName() + ", " +
                posts.getAddress().getSubDistrics().getName() + ", " +
                posts.getAddress().getDistricts().getName() + ", " +
                posts.getAddress().getCities().getName());

        Detail_ImageService detail_imageService = new Detail_ImageService();
        List<Images> images = null;
        try {
            images = detail_imageService.getListImageByPostsId(1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (images != null && !images.isEmpty()) {
            byte[] image = images.get(0).getImage();
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                holder.imgItemHostelPost.setImageBitmap(bitmap);
            }
        } else {
            // Handle the case when no images are available
            // For example, you can set a default image or hide the ImageView
            holder.imgItemHostelPost.setImageDrawable(null); // Set a default image or hide the ImageView
        }

        return convertView;
    }
}
