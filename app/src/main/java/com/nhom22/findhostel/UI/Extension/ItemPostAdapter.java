package com.nhom22.findhostel.UI.Extension;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;

import java.util.List;

public class ItemPostAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PostDecor> itemPost;

    private UserAccount user;

    public static UserAccountDAO accountDAO;


    public ItemPostAdapter(Context context, int layout, List<PostDecor> itemPost) {
        this.context = context;
        this.layout = layout;
        this.itemPost = itemPost;
    }

    @Override
    public int getCount() {
        return itemPost.size();
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
        TextView tvContent, tvAuthor, tvCreatedPost, tvMoreContentPost;
        ImageView imgItemPost, imgAvatar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_contentPost);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_authorPost);
            holder.imgItemPost = (ImageView) convertView.findViewById(R.id.img_itemPostDecor);
            holder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_avatarPost);
            holder.tvCreatedPost = (TextView) convertView.findViewById(R.id.tv_createdDatePost) ;
            holder.tvMoreContentPost = (TextView) convertView.findViewById(R.id.viewMoreContentPost);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        PostDecor item = itemPost.get(position);
        holder.tvContent.setText(item.getText());
        holder.tvCreatedPost.setText(item.getCreatedDate());
        int id = item.getUserId();

        UserAccountDAO dao = new UserAccountDAO(context);
        UserAccount user = dao.getUserAccountById(id);

        holder.tvAuthor.setText(user.getUsername());


        //chuyen byte -> bitmap
        byte[] hinhAnh = item.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        holder.imgItemPost.setImageBitmap(bitmap);

        //chuyen byte -> bitmap
        byte[] avatar = user.getImage();
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(avatar,0,avatar.length);
        holder.imgAvatar.setImageBitmap(bitmap2);

        holder.tvContent.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = holder.tvContent.getLayout();

                if (layout != null && layout.getEllipsisCount(layout.getLineCount() - 1) > 0) {
                    // Show "View more" TextView and set the truncated text
                    holder.tvMoreContentPost.setVisibility(View.VISIBLE);
                    holder.tvMoreContentPost.setText("Thêm");

                    holder.tvMoreContentPost.setOnClickListener(new View.OnClickListener() {
                        boolean isExpanded = false;
                        @Override
                        public void onClick(View v) {
                            isExpanded = !isExpanded;
                            if (isExpanded) {
                                holder.tvContent.setMaxLines(Integer.MAX_VALUE);
                                holder.tvMoreContentPost.setText("Ẩn bớt");
                            } else {
                                holder.tvContent.setMaxLines(2); // Số dòng tối đa
                                holder.tvMoreContentPost.setText("Thêm");
                            }
                        }
                        });

                } else {
                    // Hide "View more" TextView
                    holder.tvMoreContentPost.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}
