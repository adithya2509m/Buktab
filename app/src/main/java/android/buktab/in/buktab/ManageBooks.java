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
import android.widget.ListAdapter;
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

    SwipeMenuListView lay;
    String url="http://52.10.251.227:3000/manageBooks";
    String[] jasonbook,jasonauthor,jasonsem,jasonprice,jsondept;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_fragment, container, false);


        lay = (SwipeMenuListView)rootView.findViewById(R.id.list);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Update");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_cancel_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lay.setMenuCreator(creator);

        lay.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {


                switch (index) {
                    case 0:
                        Toast.makeText(getActivity(), "delete", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "update", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });


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
                        jasonbook=new String[jsonArray.length()];
                        jasonauthor=new String[jsonArray.length()];
                        jasonsem=new String[jsonArray.length()];
                        jasonprice=new String[jsonArray.length()];
                        jsondept=new String[jsonArray.length()];

                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                            jasonbook[i] = temp2.getString("Name");
                            jasonauthor[i] = temp2.getString("Author");
                            jasonsem[i]=temp.getString("Semester");
                            jasonprice[i]=temp.getString("Price");
                            jsondept[i]=temp2.getString("Department");

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
