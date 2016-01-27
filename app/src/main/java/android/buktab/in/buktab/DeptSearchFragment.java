package android.buktab.in.buktab;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    ArrayList<String> jasonbook,jasonauthor,jasonsem,jasonprice,jsonname,jsonph,jsonmail,jsondept,jsonpub,jsonlocation,jsongender;
    View rootView;
    RelativeLayout top;
    TextView listheader;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.deptsearch_fragment, container, false);
        jasonbook= new ArrayList<String>();
        jasonauthor= new ArrayList<String>();
        jasonsem= new ArrayList<String>();
        jasonprice= new ArrayList<String>();
        jsonname= new ArrayList<String>();
        jsonph= new ArrayList<String>();
        jsonmail= new ArrayList<String>();
        jsondept= new ArrayList<String>();
        jsonpub= new ArrayList<String>();
        jsonlocation=new ArrayList<String>();
        jsongender=new ArrayList<String>();

      //  top=(RelativeLayout)rootView.findViewById(R.id.top_layout);


        deptsearch=(Spinner)rootView.findViewById(R.id.deptsearch);
        semsearch=(Spinner)rootView.findViewById(R.id.semsearch);
        resultlist=(ListView)rootView.findViewById(R.id.resultlist);

        listheader=(TextView)rootView.findViewById(R.id.message);

        String[] items1= getResources().getStringArray(R.array.dept_array);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptsearch.setAdapter(adapter1);

        String[] items = getResources().getStringArray(R.array.sem_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semsearch.setAdapter(adapter);

         sem = semsearch.getSelectedItem().toString();
         dept = deptsearch.getSelectedItem().toString();

     //   isFirstTime();
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ListAdapter EventList = new customlist2(getActivity(), Splash.resbook, Splash.ressem, Splash.resauthor, Splash.resprice, Splash.resdept,Splash.reslocation);
       // top.setVisibility(View.INVISIBLE);
        resultlist.setAdapter(EventList);
        listheader.setText("Recent Posts");




        resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //   Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getActivity(), Searchintent.class);
                i.putExtra("name", Splash.resname.get(position));
                i.putExtra("phone", Splash.resphone.get(position));
                i.putExtra("email", Splash.resemail.get(position));
                i.putExtra("book", Splash.resbook.get(position));
                i.putExtra("author", Splash.resauthor.get(position));
                i.putExtra("sem", Splash.ressem.get(position));
                i.putExtra("dept", Splash.resdept.get(position));
                i.putExtra("price", Splash.resprice.get(position));
                i.putExtra("pub", Splash.respub.get(position));
                i.putExtra("location",Splash.reslocation.get(position));
                startActivity(i);    }
        });



























        deptsearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dept = parent.getItemAtPosition(pos).toString();

                jasonbook.clear();
                jasonauthor.clear();
                jasonsem.clear();
                jasonprice.clear();
                jsondept.clear();
                jsonmail.clear();
                jsonph.clear();
                jsonname.clear();
                jsonlocation.clear();
                jsongender.clear();


                ListAdapter EventList= new customlist2(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept,jsonlocation);
                stopAnim();
                resultlist.setAdapter(EventList);
                listheader.setText("No Result");





                if(!sem.equals("Sem")&&!dept.equals("ALL")){


                    startAnim();
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
                           // String message=jsonobject.getString("message");

                            if(result.equals("true"))
                            {


                                JSONArray jsonArray=jsonobject.getJSONArray("result");
                                int len=jsonArray.length();
                                jasonbook.clear();
                                jasonauthor.clear();
                                jasonsem.clear();
                                jasonprice.clear();
                                jsondept.clear();
                                jsonmail.clear();
                                jsonph.clear();
                                jsonname.clear();
                                jsonlocation.clear();
                                jsongender.clear();

                                for(int i =0;i<jsonArray.length();i++) {
                                    JSONObject temp = jsonArray.getJSONObject(i);
                                    JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                                    JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                                    jasonbook.add(temp2.getString("Name"));
                                    jasonauthor.add(temp2.getString("Author"));
                                    jasonsem.add(temp.getString("Semester"));
                                    jasonprice.add(temp.getString("Price"));
                                    jsondept.add(temp2.getString("Department"));
                                    jsonpub.add(temp2.getString("Publisher"));
                                    jsonmail.add(temp3.getString("email"));
                                    jsonph.add(temp3.getString("phoneNo"));
                                    jsonname.add(temp3.getString("username"));
                                    jsonlocation.add(temp.getString("location"));
                                    jsongender.add(temp3.getString("sex"));

                                }}

                            else{
                                stopAnim();
                             //   Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                             //   top.setVisibility(View.VISIBLE);
                              //  TextView m=(TextView)rootView.findViewById(R.id.message);
                                listheader.setText("No Books Available");
                                //m.setText(message);
                            }



                             }

                        else{
                            Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();

                        }}



                    catch (JSONException e){
                        stopAnim();
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }



                     EventList= new customlist2(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept,jsonlocation);
                    stopAnim();
                 resultlist.setAdapter(EventList);
                    listheader.setText("Search Result");
                    resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                         //   Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();


                            Intent i=new Intent(getActivity(),Searchintent.class);
                            i.putExtra("name",jsonname.get(position));
                            i.putExtra("phone",jsonph.get(position));
                            i.putExtra("email",jsonmail.get(position));
                            i.putExtra("book",jasonbook.get(position));
                            i.putExtra("author",jasonauthor.get(position));
                            i.putExtra("sem",jasonsem.get(position));
                            i.putExtra("dept",jsondept.get(position));
                            i.putExtra("price",jasonprice.get(position));
                            i.putExtra("pub",jsonpub.get(position));
                            i.putExtra("location",jsonlocation.get(position));
                            i.putExtra("gender",jsongender.get(position));
                            startActivity(i);




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

                jasonbook.clear();
                jasonauthor.clear();
                jasonsem.clear();
                jasonprice.clear();
                jsondept.clear();
                jsonmail.clear();
                jsonph.clear();
                jsonname.clear();
                jsonlocation.clear();
                jsongender.clear();


                ListAdapter EventList= new customlist2(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept,jsonlocation);
                stopAnim();
                resultlist.setAdapter(EventList);
                listheader.setText("No Result");


                if(!sem.equals("Sem")&&!dept.equals("ALL")){



                     startAnim();
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
                            //String message=jsonobject.getString("message");

                            if(result.equals("true"))
                            {


                                JSONArray jsonArray=jsonobject.getJSONArray("result");
                                int len=jsonArray.length();
                                jasonbook.clear();
                                jasonauthor.clear();
                                jasonsem.clear();
                                jasonprice.clear();
                                jsondept.clear();
                                jsonmail.clear();
                                jsonph.clear();
                                jsonname.clear();
                                jsonlocation.clear();
                                jsongender.clear();

                                for(int i =0;i<jsonArray.length();i++) {
                                    JSONObject temp = jsonArray.getJSONObject(i);
                                    JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                                    JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                                    jasonbook.add(temp2.getString("Name"));
                                    jasonauthor.add(temp2.getString("Author"));
                                    jasonsem.add(temp.getString("Semester"));
                                    jasonprice.add(temp.getString("Price"));
                                    jsondept.add(temp2.getString("Department"));
                                    jsonpub.add(temp2.getString("Publisher"));
                                    jsonmail.add(temp3.getString("email"));
                                    jsonph.add(temp3.getString("phoneNo"));
                                    jsonname.add(temp3.getString("username"));
                                    jsonlocation.add(temp.getString("location"));
                                    jsongender.add(temp3.getString("sex"));

                                }}

                            else{
                                stopAnim();
                             //   Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                            //    top.setVisibility(View.VISIBLE);
                                TextView m=(TextView)rootView.findViewById(R.id.message);
                                //m.setText(message);
                            }



                        }

                        else{
                            Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();

                        }}



                    catch (JSONException e){
                        stopAnim();
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }



                     EventList= new customlist2(getActivity(),jasonbook,jasonsem,jasonauthor,jasonprice,jsondept,jsonlocation);
                    stopAnim();
                    resultlist.setAdapter(EventList);
                    listheader.setText("Search Result");
                    resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                         //  Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();


                            Intent i=new Intent(getActivity(),Searchintent.class);
                            i.putExtra("name",jsonname.get(position));
                            i.putExtra("phone",jsonph.get(position));
                            i.putExtra("email",jsonmail.get(position));
                            i.putExtra("book",jasonbook.get(position));
                            i.putExtra("author",jasonauthor.get(position));
                            i.putExtra("sem",jasonsem.get(position));
                            i.putExtra("dept",jsondept.get(position));
                            i.putExtra("price",jasonprice.get(position));
                            i.putExtra("pub",jsonpub.get(position));
                            i.putExtra("location",jsonlocation.get(position));
                            i.putExtra("gender",jsongender.get(position));
                            startActivity(i);








                        }
                    });









                }




            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });










        return rootView;
    }

    void startAnim(){
        rootView. findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        rootView.findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }






}
