package com.example.user.serchbar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.serchbar.R;
import com.example.user.serchbar.activity.mapActivity;
import com.example.user.serchbar.network.model.ShowListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<ShowListViewModel> SyncDataList = null;
    private ArrayList<ShowListViewModel> arraylist;

    public ListViewAdapter(Context context, List<ShowListViewModel> worldpopulationlist) {
        mContext = context;
        this.SyncDataList = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ShowListViewModel>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView header;
        TextView display;
        TextView id;
        TextView x;
        TextView y;
    }

    @Override
    public int getCount() {
        return SyncDataList.size();
    }

    @Override
    public ShowListViewModel getItem(int position) {
        return SyncDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_main, null);
            // Locate the TextViews in listview_item.xml
            holder.id = (TextView) view.findViewById(R.id.id);
            holder.header = (TextView) view.findViewById(R.id.header);
            holder.display = (TextView) view.findViewById(R.id.display);
            holder.x = (TextView) view.findViewById(R.id.x);
            holder.y = (TextView) view.findViewById(R.id.y);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        try{
            // Set the results into TextViews
            holder.display.setText( SyncDataList.get(position).getDisplay());
            holder.header.setText( SyncDataList.get(position).getHeader());
            holder.id.setText(SyncDataList.get(position).getId());

        }catch (NullPointerException e){e.printStackTrace();}
        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, mapActivity.class);
                intent.putExtra("x",(SyncDataList.get(position).getX()));
                intent.putExtra("y",(SyncDataList.get(position).getY()));
                intent.putExtra("header",(SyncDataList.get(position).getHeader()));


                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        SyncDataList.clear();
        if (charText.length() == 0) {
            SyncDataList.addAll(arraylist);
        }
        else
        {
            for (ShowListViewModel wp : arraylist)
            {
                if (wp.getDisplay().toLowerCase(Locale.getDefault()).contains(charText) || wp.getHeader().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    SyncDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}