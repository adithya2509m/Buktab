package android.buktab.in.buktab;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 12/22/2015.
 */
public class DeptSearchFragment  extends Fragment {

    Spinner deptsearch,semsearch;
    String url="http://52.10.251.227:3000/getAds/byDeptAndSem";
    ListView resultlist;
    String sem,dept;
    String[] jasonbook,jasonauthor,jasonsem,jasonprice,jsonname,jsonph,jsonmail,jsondept;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.deptsearch_fragment, container, false);

        deptsearch=(Spinner)rootView.findViewById(R.id.deptsearch);
        semsearch=(Spinner)rootView.findViewById(R.id.semsearch);
        resultlist=(ListView)rootView.findViewById(R.id.resultlist);
        String[] items1= getResources().getStringArray(R.array.dept_array);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items1);
        deptsearch.setAdapter(adapter1);

        String[] items = getResources().getStringArray(R.array.sem_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        semsearch.setAdapter(adapter);

         sem = semsearch.getSelectedItem().toString();
         dept = deptsearch.getSelectedItem().toString();

        deptsearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dept = parent.getItemAtPosition(pos).toString();


                if(!sem.equals("Sem")&&!dept.equals("ALL")){



                    JSONObject jsonobject;
                    final JSONParser jParser2 = new JSONParser();
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("sem",sem));
                    params2.add(new BasicNameValuePair("dept",dept));
                    params2.add(new BasicNameValuePair("token",Login.token));

                    jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

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
                                jsonmail=new String[jsonArray.length()];
                                jsonph=new String[jsonArray.length()];
                                jsonname=new String[jsonArray.length()];
                                jsondept=new String[jsonArray.length()];

                                for(int i =0;i<jsonArray.length();i++) {
                                    JSONObject temp = jsonArray.getJSONObject(i);
                                    JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                                    JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                                    jasonbook[i] = temp2.getString("Name");
                                    jasonauthor[i] = temp2.getString("Author");
                                    jasonsem[i]=temp.getString("Semester");
                                    jasonprice[i]=temp.getString("Price");
                                    jsondept[i]=temp2.getString("Department");
                                    jsonmail[i]=temp3.getString("email");
                                    jsonph[i]=temp3.getString("phoneNo");
                                    jsonname[i]=temp3.getString("username");

                                }}

                            else{
                                Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                            }



                             }

                        else{
                            Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();

                        }}



                    catch (JSONException e){
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }



                    ListAdapter EventList= new customlist2(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept);
                    resultlist.setAdapter(EventList);
                    resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();
                            final Dialog dialog = new Dialog(getActivity(),R.style.NewDialog);

                            dialog.setContentView(R.layout.search_dialog);
                            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setTitle("Get it from...");

                            dialog.show();

                            TextView bname= (TextView) dialog.findViewById(R.id.bname);
                            TextView aname= (TextView) dialog.findViewById(R.id.aname);
                            TextView sname= (TextView) dialog.findViewById(R.id.sname);

                            Button dismiss=(Button)dialog.findViewById(R.id.dismiss);
                            Button insert =(Button)dialog.findViewById(R.id.insert);
                            bname.setText(jsonname[position]);
                            aname.setText(jsonph[position]);
                            sname.setText(jsonmail[position]);
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            insert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.dismiss();
                                    String numberToDial = "tel:"+jsonph[position];
                                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

                                }
                            });


                        }
                    });



                }




            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        semsearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                sem = parent.getItemAtPosition(pos).toString();


                if(!sem.equals("Sem")&&!dept.equals("ALL")){




                    JSONObject jsonobject;
                    final JSONParser jParser2 = new JSONParser();
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("sem", sem));
                    params2.add(new BasicNameValuePair("dept", dept));
                    params2.add(new BasicNameValuePair("token", Login.token));

                    jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

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
                                jsonmail=new String[jsonArray.length()];
                                jsonph=new String[jsonArray.length()];
                                jsonname=new String[jsonArray.length()];
                                jsondept=new String[jsonArray.length()];

                                for(int i =0;i<jsonArray.length();i++) {
                                    JSONObject temp = jsonArray.getJSONObject(i);
                                    JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                                    JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                                    jasonbook[i] = temp2.getString("Name");
                                    jasonauthor[i] = temp2.getString("Author");
                                    jasonsem[i]=temp.getString("Semester");
                                    jasonprice[i]=temp.getString("Price");
                                    jsondept[i]=temp2.getString("Department");
                                    jsonmail[i]=temp3.getString("email");
                                    jsonph[i]=temp3.getString("phoneNo");
                                    jsonname[i]=temp3.getString("username");

                                }}

                            else{
                                Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                            }



                        }

                        else{
                            Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();

                        }}



                    catch (JSONException e){
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }



                    ListAdapter EventList= new customlist2(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept);
                    resultlist.setAdapter(EventList);
                    resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();
                            final Dialog dialog = new Dialog(getActivity(),R.style.NewDialog);

                            dialog.setContentView(R.layout.search_dialog);
                            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setTitle("Get it from...");

                            dialog.show();

                            TextView bname= (TextView) dialog.findViewById(R.id.bname);
                            TextView aname= (TextView) dialog.findViewById(R.id.aname);
                            TextView sname= (TextView) dialog.findViewById(R.id.sname);

                            Button dismiss=(Button)dialog.findViewById(R.id.dismiss);
                            Button insert =(Button)dialog.findViewById(R.id.insert);
                            bname.setText(jsonname[position]);
                            aname.setText(jsonph[position]);
                            sname.setText(jsonmail[position]);
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            insert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.dismiss();
                                    String numberToDial = "tel:"+jsonph[position];
                                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

                                }
                            });


                        }
                    });









                }




            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });










        return rootView;
    }

}
