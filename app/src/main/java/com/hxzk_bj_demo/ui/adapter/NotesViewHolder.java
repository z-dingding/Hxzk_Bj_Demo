package com.hxzk_bj_demo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxzk_bj_demo.R;


public class NotesViewHolder extends RecyclerView.ViewHolder {

    public TextView    tvContent;
    public TextView    tvDate;
    public ImageButton imgBtnDelete;


    public NotesViewHolder(View itemView) {
        super(itemView);
        setupViews(itemView);
    }

    private void setupViews(View view) {
        tvContent= (TextView) view.findViewById(R.id.itemtv_content_notebook);
        tvDate= (TextView) view.findViewById(R.id.itemtv_date_notebook);
        imgBtnDelete= (ImageButton) view.findViewById(R.id.itemimgbtn_delete_notebook);
    }


}
