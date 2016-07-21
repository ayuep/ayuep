package com.ws.apple.ayuep.ui.store;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ws.apple.ayuep.R;

import java.util.ArrayList;
import java.util.List;

public class StoreModify extends AppCompatActivity {

    private List<String> data = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_modify);
        Init();
        TestAdapter adapter = new TestAdapter(StoreModify.this,R.layout.listitem,data);
        ListView listView = (ListView) findViewById(R.id.id_store_list_view);
        listView.setAdapter(adapter);
    }
    private void Init()
    {
        data.add("儿童摄影");
        data.add("婚纱摄影");
        data.add("个人写真");
        data.add("其他");
    }
}
 class TestAdapter extends ArrayAdapter<String>{
     private int resourcesId;
     public TestAdapter(Context context, int textViewResourceId, List<String> data)
     {
         super(context,textViewResourceId,data);
         resourcesId =textViewResourceId;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         String type = getItem(position);
         View view ;
         if(convertView==null) {
             view =  LayoutInflater.from(getContext()).inflate(resourcesId, null);
         }else{
             view =convertView;
         }
         TextView textView = (TextView)view.findViewById(R.id.title_text);
         textView.setText(type);
         return view;
     }
 }
