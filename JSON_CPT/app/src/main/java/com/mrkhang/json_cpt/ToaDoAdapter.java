package com.mrkhang.json_cpt;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by MrKHANG on 3/21/2018.
 */

public class ToaDoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ToaDo> ListToaDo;

    public ToaDoAdapter(Context context, int layout, List<ToaDo> listToaDo) {
        this.context = context;
        this.layout = layout;
        ListToaDo = listToaDo;
    }

    @Override
    public int getCount() {
        return ListToaDo.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtLat, txtLo, txtAl;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtLat   = (TextView) view.findViewById(R.id.textLatitude);
            holder.txtLo    = (TextView) view.findViewById(R.id.textLongitude);
            holder.txtAl    = (TextView) view.findViewById(R.id.textAltitude);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ToaDo toado = ListToaDo.get(i);
        holder.txtLat.setText(toado.getLatitude());
        holder.txtLo.setText(toado.getLongitude());
        holder.txtAl.setText(toado.getAltitude());
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
