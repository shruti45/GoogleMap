package com.example.shru.googlemap;

/**
 * Created by Shru on 2/9/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListActivity extends Activity {

    ListView lv;
    Context context;
    ArrayList<String> array;
    String[] username = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    String[] mobileno = {"198", "121", "198", "121", "198", "121", "198", "121", "121"};
    String[] vehicletype = {"4 wheeler", "2 wheeler", "4 wheeler", "2 wheeler", "4 wheeler", "2 wheeler", "4 wheeler", "2 wheeler", "2 wheeler"};
    Integer[] images = {R.drawable.bikeimage1, R.drawable.carimage1, R.drawable.bikeimage2, R.drawable.carimage2, R.drawable.carimage3, R.drawable.bikeimage3, R.drawable.carimage4, R.drawable.bikeimage4, R.drawable.carimage1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        array = new ArrayList<String>();
        array.add("19");
        array.add("19");
        array.add("19");
        array.add("19");
        context = this;
        lv = (ListView) findViewById(R.id.list);

        lv.setAdapter(new BaseAdapterExample(this, images, username, mobileno, vehicletype));
    }


    class BaseAdapterExample extends BaseAdapter {

        Context context;
        ImageView img, callimg, mapimg;
        TextView tv1, tv2, tv3, tv4;
        Integer[] imageid;
        String[] vehicle;
        String[] number;
        String[] user;
        private  LayoutInflater inflater = null;

        public BaseAdapterExample(ListActivity mainactivity, Integer[] images, String[] username, String[] mobileno, String[] vehicletype) {
            imageid = images;
            context = mainactivity;
            vehicle = vehicletype;
            user = username;
            number = mobileno;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public BaseAdapterExample() {
            super();
        }

        @Override
        public int getCount() {
            return user.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view;
            view = inflater.inflate(R.layout.list_items, null);
            callimg = (ImageView) view.findViewById(R.id.call_image);
            mapimg = (ImageView) view.findViewById(R.id.map_img);
            tv1 = (TextView) view.findViewById(R.id.text_username);
            tv2 = (TextView) view.findViewById(R.id.text_mobile);
            tv3 = (TextView) view.findViewById(R.id.v_type);
           tv4 = (TextView) view.findViewById(R.id.text_distance);

            tv1.setText(user[position]);
            tv2.setText(number[position]);
            tv3.setText(vehicle[position]);
            img = (ImageView) view.findViewById(R.id.image);
            img.setImageResource(imageid[position]);


       /* Bundle gt=getIntent().getExtras();
            if(gt != null) {
                String str = gt.getString("Dis");
                tv4.setText(str);
            }
*/
            callimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Uri uri=Uri.parse(("tel:"+tv2.getText().toString()));
                    //  Intent i=new Intent(android.content.Intent.ACTION_DIAL,uri);

                    // context.startActivity(i);
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:" + ((TextView) view.findViewById(R.id.text_mobile)).getText().toString()));
                    context.startActivity(call);
                }
            });
            mapimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), Mapsexample.class);
                    context.startActivity(i);
                    // Toast.makeText(context, "Find your location   ", Toast.LENGTH_SHORT).show();
                }
            });

            return view;

        }

        private Intent getIntent() {

            return null;
        }
    }

    /*To go back to previous page*/
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ListActivity.this, Mapsexample.class));
        finish();

    }
}
