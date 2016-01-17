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
    ArrayList<String> jasonbook,jasonauthor,jasonsem,jasonprice,jsondept;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_fragment, container, false);
        jasonauthor = new ArrayList<String>();
        jasonbook = new ArrayList<String>();
        jasonsem = new ArrayList<String>();
        jasonprice = new ArrayList<String>();
        jsondept = new ArrayList<String>();

        lay = (ListView)rootView.findViewById(R.id.list);








        JSONObject jsonobject;
        final JSONParser jParser2 = new JSONParser();
        List<NameValuePair> params2 = new ArrayList<NameValuePair>();

        params2.add(new BasicNameValuePair("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.IjU2NmZmMGIwNmQ4YzgxMzU1MWQ1MDdlOSI.ON1liq4QMz7uUuPhHGAQkevfqM4njzdbtzhlN-SORVg"));

        jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

        final ConnectionDetector cd = new ConnectionDetector(getActivity());
        if(cd.isConnectingToInternet()){

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {


                        JSONArray jsonArray=jsonobject.getJSONArray("result");
                        int len=jsonArray.length();


                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                            jasonbook.add(temp2.getString("Name"));
                            jasonauthor.add(temp2.getString("Author"));
                            jasonsem.add(temp.getString("Semester"));
                            jasonprice.add(temp.getString("Price"));
                            jsondept.add(temp2.getString("Department"));

                        }

                        ListAdapter EventList= new customlist3(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept);
                        lay.setAdapter(EventList);

                    }

                    else{
                        Toast.makeText(getActivity(), "Oops no books added in your account", Toast.LENGTH_LONG).show();
                    }

                }

                else{
                    Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();

                }}



            catch (JSONException e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }}

        else{

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }





        return rootView;

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }




}
