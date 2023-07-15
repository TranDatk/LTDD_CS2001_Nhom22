    package com.nhom22.findhostel.UI.Extension;

    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.text.Layout;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.nhom22.findhostel.Data.AddressDAO;
    import com.nhom22.findhostel.Data.UserAccountDAO;
    import com.nhom22.findhostel.Model.Address;
    import com.nhom22.findhostel.Model.HostelCollection;
    import com.nhom22.findhostel.Model.PostDecor;
    import com.nhom22.findhostel.Model.UserAccount;
    import com.nhom22.findhostel.R;
    import com.nhom22.findhostel.Service.AddressService;

    import java.util.List;

    public class ItemHotelCollectionAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        private List<HostelCollection> itemHostelCollection;
//        private final AddressService addressService = new AddressService();

        public ItemHotelCollectionAdapter(Context context, int layout, List<HostelCollection> hostelCollections) {
            this.context = context;
            this.layout = layout;
            this.itemHostelCollection = hostelCollections;
        }


        @Override
        public int getCount() {
            return itemHostelCollection.size();
        }

        @Override
        public HostelCollection getItem(int position) {
            return itemHostelCollection.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        private class ViewHolder{
            TextView tvTitle;

//            TextView tvDistrict;
            ImageView imgItemHostelCollection;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHotelCollectionAdapter.ViewHolder holder;
            if(convertView == null){
                holder = new ItemHotelCollectionAdapter.ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout, null);

                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_titelCollectionHotel);
//                holder.tvDistrict = (TextView) convertView.findViewById(R.id.tv_DistrictHotelCollection);
                holder.imgItemHostelCollection = (ImageView) convertView.findViewById(R.id.img_itemCollectionHotel);
                convertView.setTag(holder);
            }
            else {
                holder = (ItemHotelCollectionAdapter.ViewHolder) convertView.getTag();
            }
            HostelCollection item = itemHostelCollection.get(position);
            holder.tvTitle.setText(item.getTitle());

//            int address_id = item.getAddress();
//
//            Address address = addressService.getAddressById(address_id);
//
//            int subDistrictId = address.getSubDistrics().getId();
//            String subDistrictIdString = String.valueOf(subDistrictId);
//            holder.tvDistrict.setText(subDistrictIdString);

            //chuyen byte -> bitmap
            byte[] image = item.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            holder.imgItemHostelCollection.setImageBitmap(bitmap);


            return convertView;
        }

    }
