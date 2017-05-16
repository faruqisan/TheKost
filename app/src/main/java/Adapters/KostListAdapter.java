package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import Beans.Kost;
import faruqisan.thekost.R;

/**
 * Created by faruqisan on 02-May-17.
 */

public class KostListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private LinkedList<Kost> kosts;

    public KostListAdapter(Activity activity, LinkedList<Kost> kosts) {
        this.activity = activity;
        this.kosts = kosts;
    }

    @Override
    public int getCount() {
        return kosts.size();
    }

    @Override
    public Object getItem(int position) {
        return kosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_main_kost_list, null);
        }

        TextView tvListKostName = (TextView) convertView.findViewById(R.id.tvListKostName);

        Kost kost = kosts.get(position);
        tvListKostName.setText(kost.getName());

        return convertView;


    }
}
