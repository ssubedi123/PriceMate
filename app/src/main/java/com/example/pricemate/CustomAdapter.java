package com.example.pricemate;

import com.example.pricemate.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
    {
        private Context context;
    private List<Item> list;

    public CustomAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.singleiteam, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = list.get(position);

        holder.textTitle.setText(item.getTitle());
        holder.textPrice.setText(item.getPrice());
        holder.textCondition.setText(item.getCondition());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textPrice, textCondition;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.name);
            textPrice = itemView.findViewById(R.id.price);
            textCondition = itemView.findViewById(R.id.condition);
        }
    }

}