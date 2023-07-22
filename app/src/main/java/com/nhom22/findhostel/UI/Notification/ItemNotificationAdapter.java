package com.nhom22.findhostel.UI.Notification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Notification;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.UI.Extension.ItemPostsHostelAdapter;
import com.nhom22.findhostel.UI.Search.ImageSliderAdapter;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItemNotificationAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Notification> notifications;
    private Posts posts = null;
    private PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());

    public ItemNotificationAdapter(Context context, int layout, List<Notification> notifications){
        this.context = context;
        this.layout = layout;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
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
        TextView tvDistrict, tvAuthorName, tvNotiTitle, tvPostsPrice, tvTime;
        ImageView imgAvatar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemNotificationAdapter.ViewHolder holder;
        if(convertView == null){
            holder = new ItemNotificationAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.tvDistrict = (TextView) convertView.findViewById(R.id.tv_noti_district_posts);
            holder.tvAuthorName = (TextView) convertView.findViewById(R.id.tv_author_name);
            holder.tvNotiTitle = (TextView) convertView.findViewById(R.id.tv_noti_title);
            holder.tvPostsPrice = (TextView) convertView.findViewById(R.id.tv_noti_price_post);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_noti_time);
            holder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_avatar_author);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemNotificationAdapter.ViewHolder) convertView.getTag();
        }

        Notification notification = notifications.get(position);
        Address address = notification.getPosts().getAddress();

        String districts_posts = address.getHouseNumber() + " " + address.getStreets().getName() + ", " +
                address.getSubDistrics().getName() + ", " + address.getDistricts().getName() + ", " + address.getCities().getName();
        String author_name = notification.getUserAccount().getUsername();
        String posts_price = String.valueOf(notification.getPosts().getPrice());
        String posts_type = notification.getPosts().getType().getName();

        String title = author_name + " " + "đã đăng bài viết cho thuê " + posts_type + " " + "gần địa chỉ của bạn";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String createdDate = sdf.format(notification.getCreated_date());

        holder.tvTime.setText(createdDate);
        holder.tvNotiTitle.setText(title);
        holder.tvPostsPrice.setText(posts_price);
        holder.tvDistrict.setText(districts_posts);
        holder.tvAuthorName.setText(author_name);

        //chuyen byte -> bitmap
        byte[] image = notification.getUserAccount().getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imgAvatar.setImageBitmap(bitmap);

        return convertView;
    }
}
