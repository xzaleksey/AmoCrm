package com.valyakinaleksey.amocrm.domain;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valyakinaleksey.amocrm.R;
import com.valyakinaleksey.amocrm.models.api.Lead;

import java.util.List;

public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.ViewHolder> {

    private List<Lead> leadList;

    public LeadsAdapter(List<Lead> leadList) {
        this.leadList = leadList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(
                inflater.inflate(R.layout.item_leads, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindLead(leadList.get(position));
    }

    @Override
    public int getItemCount() {
        return leadList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        }

        void bindLead(Lead lead) {
            tvId.setText(String.valueOf(lead.id));
            tvName.setText(lead.name);
            tvPrice.setText(String.valueOf(lead.price));
        }
    }

}
