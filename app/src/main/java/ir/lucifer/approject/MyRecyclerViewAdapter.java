package ir.lucifer.approject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Product> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_item_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mData.get(position);
        holder.PriceTextViewRVA.setText(product.price);
        holder.SubjectTextViewRVA.setText(product.subject);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product pro = mData.get(position);
                Intent intent = new Intent(SearchViewPage.activity ,ProductPage.class);
                intent.putExtra("PRO_SUBJECT" ,pro.subject);
                intent.putExtra("PRO_PRICE" ,pro.price);
                intent.putExtra("PRO_DES" ,pro.description);
                intent.putExtra("PRO_PHONE" ,pro.sellerID);
                intent.putExtra("PRO_ID" , pro.ID);

                SearchViewPage.activity.startActivity(intent);

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView PriceTextViewRVA;
        TextView SubjectTextViewRVA;
        ImageView PhotoImageViewRVA;
        ViewHolder(View itemView) {
            super(itemView);
            PriceTextViewRVA = itemView.findViewById(R.id.PricePIV);
            SubjectTextViewRVA = itemView.findViewById(R.id.SubjectPIV);
            PhotoImageViewRVA = itemView.findViewById(R.id.ImageViewPIV);
//            PriceTextViewRVA.setOnClickListener(this);
//            SubjectTextViewRVA.setOnClickListener(this);
//            PhotoImageViewRVA.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    Product getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}