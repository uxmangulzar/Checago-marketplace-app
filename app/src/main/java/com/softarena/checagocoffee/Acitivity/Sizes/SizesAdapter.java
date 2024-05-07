package com.softarena.checagocoffee.Acitivity.Sizes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.softarena.checagocoffee.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*public class SizesAdapter {
}*/
public class SizesAdapter extends RecyclerView.Adapter<SizesAdapter.SizesViewHolder> {
    private Context context;
    private List<ProductSize> sizesModelList;
    private int lastCheckedPosition = 0;
    private OnItemClick mCallback;
    public SizesAdapter(Context context, List<ProductSize> sizesModelList,OnItemClick listener) {
        this.context = context;
        this.sizesModelList = sizesModelList;
        this.mCallback = listener;
    }


    @NonNull
    @Override
    public SizesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_sizes,parent,false);
        return new SizesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SizesViewHolder holder, final int position) {

        holder.tv_sizePrice.setText("ZAR"+sizesModelList.get(position).getProductPrice());
        holder.radio_btn.setText(sizesModelList.get(position).getProductSizeName());
        holder.radioGroup.clearCheck();
        holder.radio_btn.setChecked(position == lastCheckedPosition);
    }


    @Override
    public int getItemCount() {
        return sizesModelList.size();
    }


    public class SizesViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sizeName, tv_sizePrice;
        RadioButton radio_btn;
        RadioGroup radioGroup;


        public SizesViewHolder(View itemView) {
            super(itemView);
            tv_sizePrice = itemView.findViewById(R.id.tv_sizePrice);
            radio_btn = itemView.findViewById(R.id.radio_button);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            radio_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClick(getAdapterPosition());
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);

                }
            });

        }
    }
    public interface OnItemClick {
        void onClick (int value);
    }
}