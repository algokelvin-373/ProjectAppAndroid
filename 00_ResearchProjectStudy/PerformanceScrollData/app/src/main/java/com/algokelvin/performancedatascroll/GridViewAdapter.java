package com.algokelvin.performancedatascroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<KamenRider> {
    private final LayoutInflater inflater;

    public GridViewAdapter(Context context, List<KamenRider> data) {
        super(context, 0, data);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, @NonNull ViewGroup parent) {
        GridItemViewHolder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.item_card_1, parent, false);
            holder = new GridItemViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (GridItemViewHolder) v.getTag();
        }

        System.out.println("Item Card: "+position);

        if (getItem(position) != null) {
            holder.txt_id.setText(getItem(position).getId());
            holder.img_data.setImageResource(getItem(position).getImage());
            holder.code_data.setText(getItem(position).getCode());
            holder.name_data.setText(getItem(position).getName());
            holder.upgrade_data.setText(getItem(position).getForm_update());
            holder.super_data.setText(getItem(position).getForm_super());
            holder.final_data.setText(getItem(position).getForm_final());
            holder.year_data.setText(getItem(position).getYear());
            holder.mc_data.setText(getItem(position).getMc());
            holder.actor_data.setText(getItem(position).getActor());
        }

        return v;
    }

    public static class GridItemViewHolder {
        RelativeLayout icon_code_layout;
        TextView txt_id;
        ImageView img_close;
        RelativeLayout identifier_layout;
        ImageView img_data;
        TextView code_data;
        TextView name_data;
        TableLayout table_layout;
        TextView upgrade_data;
        TextView super_data;
        TextView final_data;
        TextView year_data;
        TextView mc_data;
        TextView actor_data;

        public GridItemViewHolder(View v) {
            icon_code_layout = v.findViewById(R.id.icon_code_layout);
            txt_id = v.findViewById(R.id.id_card);
            img_close = v.findViewById(R.id.img_close);
            identifier_layout = v.findViewById(R.id.identifier_layout);
            img_data = v.findViewById(R.id.data_image);
            code_data = v.findViewById(R.id.data_code);
            name_data = v.findViewById(R.id.data_name);
            table_layout = v.findViewById(R.id.table_layout);
            upgrade_data = v.findViewById(R.id.data_upgrade);
            super_data = v.findViewById(R.id.data_super);
            final_data = v.findViewById(R.id.data_final);
            year_data = v.findViewById(R.id.data_year);
            mc_data = v.findViewById(R.id.data_mc);
            actor_data = v.findViewById(R.id.data_actor);
        }
    }
}
