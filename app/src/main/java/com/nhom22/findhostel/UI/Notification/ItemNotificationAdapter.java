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
import android.widget.Toast;

import com.nhom22.findhostel.Data.AddressDAO;
import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Notification;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ItemNotificationAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Notification> notifications;
    private Posts posts = null;
    private PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
    private AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
    private UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());

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
        TextView tvDistrict, tvAuthorName, tvNotiTitle, tvTime;
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
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_noti_time);
            holder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_avatar_author);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemNotificationAdapter.ViewHolder) convertView.getTag();
        }


        Notification notification = notifications.get(position);

        if (notification == null) {
            Toast.makeText(YourApplication.getInstance().getApplicationContext(), "Hệ thống đang bị lỗi không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
            return convertView; // Return without processing if notification is null
        }

        try {
            posts = postsDAO.getPostById(notification.getPostsId());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Address address = posts.getAddress();
        UserAccount userAccount = posts.getUserAccount();

        String districts_posts = address.getHouseNumber() + " " + address.getStreets().getName() + ", " +
                address.getSubDistrics().getName() + ", " + address.getDistricts().getName() + ", " + address.getCities().getName();
        String author_name = userAccount.getUsername();
        String posts_type = posts.getType().getName();

        String title = author_name + " " + "đã đăng bài viết cho thuê " + posts_type + " " + "gần địa chỉ của bạn";

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//
//        String createdDate = sdf.format(notification.getCreated_date());

        String timeSince = timeSince(notification.getCreated_date());

        holder.tvTime.setText(" " + timeSince);
        holder.tvNotiTitle.setText(" " + title);
        holder.tvDistrict.setText(" Địa chỉ: " + districts_posts);
        holder.tvAuthorName.setText(author_name);


        byte[] avatar = userAccount.getImage();
        try {
            if (avatar != null && avatar.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(avatar,0,avatar.length);
                holder.imgAvatar.setImageBitmap(bitmap);
            } else {
                // Handle the case where the image array is null or empty
            }
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log the error, show an error message, etc.)
        }



        return convertView;
    }

    private String timeSince(Date date){
        Date now = new Date();
        Date date_since = date;

        double seconds = Math.floor((now.getTime() - date_since.getTime()) / 1000 );
        double timer = seconds / 2678400;

        if(timer > 1)
            return (int) Math.floor(timer) + " tháng trước";

        timer = seconds / 604800;

        if (timer > 1)
            return (int)Math.floor(timer) + " tuần trước";

        timer = seconds / 86400;

        if (timer > 1)
            return (int) Math.floor(timer) + " ngày trước";


        timer = seconds / 3600;

        if (timer > 1)
            return (int)Math.floor(timer) + " giờ trước";

        timer = seconds / 60;

        if (timer > 1)
            return (int)Math.floor(timer) + " phút trước";

        return (int)Math.floor(timer) + " giây trước";
    }
}
