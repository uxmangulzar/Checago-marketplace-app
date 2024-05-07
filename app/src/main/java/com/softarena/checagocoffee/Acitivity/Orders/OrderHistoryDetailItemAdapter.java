package com.softarena.checagocoffee.Acitivity.Orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantItemModel;
import com.softarena.checagocoffee.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryDetailItemAdapter extends RecyclerView.Adapter<OrderHistoryDetailItemAdapter.ManageOrderListDetailItemViewHolder> {
    private Context context;
    private List<AllProduct> allOrderProductList;
    public OrderHistoryDetailItemAdapter(Context context, List<AllProduct> allOrderProductList) {
        this.context = context;
        this.allOrderProductList = allOrderProductList;
    }
    @NonNull
    @Override
    public ManageOrderListDetailItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_orderitemlist,parent,false);
        return new ManageOrderListDetailItemViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(@NonNull ManageOrderListDetailItemViewHolder holder, final int position) {


        holder.tv_itemName.setText(allOrderProductList.get(position).getProductName());
        double ingPrice=0;
        String ingitems="";
        if (allOrderProductList.get(position).getProductIngredients()!=null){
            for (int i=0;i<allOrderProductList.get(position).getProductIngredients().size();i++){
                IngrediantItemModel ingrediantItemModel =allOrderProductList.get(position).getProductIngredients().get(i).getIngredientAll().get(0);
                ingitems=ingrediantItemModel.getIngredientName()+"("+ ingrediantItemModel.getIngredientPrice()+"ZAR)"+"\n"+ingitems;
                ingPrice=ingPrice+Double.parseDouble(ingrediantItemModel.getIngredientPrice());
            }
        }

        double price=Double.parseDouble(allOrderProductList.get(position).getProductQuantity())*(Double.parseDouble(allOrderProductList.get(position).getProductSizes().get(0).getProductPrice())+ingPrice);
        holder.sizeshow.setText(allOrderProductList.get(position).getProductSizes().get(0).getProductSizeName()+"("+allOrderProductList.get(position).getProductSizes().get(0).getProductPrice()+"ZAR)");
        holder.tv_total_price.setText("ZAR"+price);
        holder.ingshow.setText(ingitems);
        holder.tv_quantity.setText(allOrderProductList.get(position).getProductQuantity());

        boolean isExpanded = allOrderProductList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.ln_itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllProduct allProduct = allOrderProductList.get(position);
                allProduct.setExpanded(!allProduct.isExpanded());
                notifyItemChanged(position);


            }
        });


    }


    @Override
    public int getItemCount() {
        return allOrderProductList.size();
    }
    public class ManageOrderListDetailItemViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, tv_quantity;
        public TextView tv_total_price,tv_itemName,sizeshow,ingshow;
        public LinearLayout ln_itemOrder,expandableLayout;

        public ManageOrderListDetailItemViewHolder(View itemView) {
            super(itemView);
            ln_itemOrder = itemView.findViewById(R.id.ln_itemOrder);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_total_price= itemView.findViewById(R.id.tv_total_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            ingshow = itemView.findViewById(R.id.ingshow);
            sizeshow = itemView.findViewById(R.id.sizeshow);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

        }
    }
}
