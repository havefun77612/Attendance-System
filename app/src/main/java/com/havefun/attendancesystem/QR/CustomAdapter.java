package com.havefun.attendancesystem.QR;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.havefun.attendancesystem.R;

import java.util.ArrayList;







public class CustomAdapter implements ListAdapter {
    String edit="def";
    ArrayList<UserData> arrayList;
    Context context;
    TextView tittle;
    //ArrayList<TextView> txt;
    public CustomAdapter(Context context, ArrayList<UserData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }



    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //final String Data =arrayList.get(position);

        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.list_row, null);
            /*convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //txt.get(position).setText("hhhh");




                    Toast.makeText(v.getContext(),"hello",Toast.LENGTH_LONG).show();
                }
            });*/

            tittle=convertView.findViewById(R.id.data);
            //txt.add(tittle);


            tittle.setText(arrayList.get(position).getName()+"\n"+arrayList.get(position).getID());
            //txt.get(position).setText(Data);



        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    public void ch(String x){

    }
}
