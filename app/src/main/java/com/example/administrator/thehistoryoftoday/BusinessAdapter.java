package com.example.administrator.thehistoryoftoday;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18/018.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    private List<Business> lBusiness;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView businessTitle;

        public ViewHolder(View view){
            super(view);
            businessTitle=(TextView) view.findViewById(R.id.title);
        }
    }

    public BusinessAdapter(List<Business> businessesList){
        lBusiness=businessesList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Business business=lBusiness.get(position);
        holder.businessTitle.setText(business.getTitle());
    }

    @Override
    public int getItemCount(){
        return lBusiness.size();
    }


}
