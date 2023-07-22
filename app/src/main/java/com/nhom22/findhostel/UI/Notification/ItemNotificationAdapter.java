package com.nhom22.findhostel.UI.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.Data.PostsDAO;
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
import java.util.List;

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
        TextView tvDistrict;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemNotificationAdapter.ViewHolder holder;
        if(convertView == null){
            holder = new ItemNotificationAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.tvDistrict = (TextView) convertView.findViewById(R.id.tv_district_posts);


            convertView.setTag(holder);
        }
        else {
            holder = (ItemNotificationAdapter.ViewHolder) convertView.getTag();
        }

        Notification notification = notifications.get(position);

        String districts_posts = notification.getPosts().getAddress().getDistricts().getName().toString();

        holder.tvDistrict.setText(districts_posts);

        return convertView;
    }
}
