package android.buktab.in.buktab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 12/21/2015.
 */
public class ManageBooks extends Fragment {

    ListView lay;

    String url="http://52.10.251.227:3000/manageBooks";
    ArrayList<String> jasonbook,jasonauthor,jasonsem,jasonprice,jsondept,jsonid;
    android.support.design.widget.FloatingActionButton add;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_fragment, container, false);
        jasonauthor = new ArrayList<String>();
        jasonbook = new ArrayList<String>();
        jasonsem = new ArrayList<String>();
        jasonprice = new ArrayList<String>();
        jsondept = new ArrayList<String>();
        jsonid=new ArrayList<String>();


        jasonauthor=Splash.jasonauthor;
        jasonbook=Splash.jasonbook;
        jasonsem=Splash.jasonsem;
        jasonprice=Splash.jasonprice;
        jsondept=Splash.jsondept;
        jsonid=Splash.jsonid;



        lay = (ListView)rootView.findViewById(R.id.list);

        add=(android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }});


            ListAdapter EventList = new customlist3(getActivity(), jasonbook, jasonsem, jasonauthor, jasonprice, jsondept, jsonid);
            lay.setAdapter(EventList);


            return rootView;

        }


    }
