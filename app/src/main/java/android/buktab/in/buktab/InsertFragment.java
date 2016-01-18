package android.buktab.in.buktab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;
import java.util.ArrayList;
/**
 * Created by root on 9/8/15.
 */
public class InsertFragment extends Fragment {

    EditText search ,price;
   // Button searchbutton;
    ListView resultlist ;
    int count =0,pos=0;
    String sem="";

    String[] jasonbook,jasonauthor,jasondept,jasonprice,jsonid;





    String searchurl="http://52.10.251.227:3000/listBooksForAdd";
    String posturl="http://52.10.251.227:3000/postBook";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.insert_fagment, container, false);
        search = (EditText) rootView.findViewById(R.id.search);
       // searchbutton = (Button) rootView.findViewById(R.id.searchbutton);
        resultlist = (ListView) rootView.findViewById(R.id.resultlist);



       search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(count<3){
                    count++;

                }
                else{
                    final ConnectionDetector cd = new ConnectionDetector(getActivity());
                    if (cd.isConnectingToInternet()) {
                        new jasonsearch().execute();
                    } else {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }

                // you can call or do what you want with your EditText here

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // Toast.makeText(getContext(),"on change",Toast.LENGTH_SHORT).show();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });






        return rootView;
    }

    private class jasonsearch extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
           /* nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Fetching data from server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();*/
            Toast.makeText(getActivity().getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("query", search.getText().toString()));
            params2.add(new BasicNameValuePair("token", Login.token));

            jsonobject = jParser2.makeHttpRequest(searchurl, "GET", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {


                        JSONArray jsonArray=jsonobject.getJSONArray("result");
                        int len=jsonArray.length();
                        jasonbook=new String[jsonArray.length()];
                        jasonauthor=new String[jsonArray.length()];
                        jasondept=new String[jsonArray.length()];
                       // jasonprice=new String[jsonArray.length()];
                        jsonid=new String[jsonArray.length()];
                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            jasonbook[i] = temp.getString("Name");
                            jasonauthor[i] = temp.getString("Author");
                            jasondept[i]=temp.getString("Department");
                            //jasonprice[i]="lol";
                            jsonid[i]=temp.getString("_id");


                        }}

                    else{
                        Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                    }



                    return true; }

                else{
                    Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();
                    return false;
                }}



            catch (JSONException e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){
           // nDialog.dismiss();
            if(!th){
                Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();

            }else{

                ListAdapter EventList= new customlist(getActivity(),jasonbook,jasondept,jasonauthor,jasonprice);
                resultlist.setAdapter(EventList);
                resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                      //  Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(getActivity(),R.style.NewDialog);

                        dialog.setContentView(R.layout.insert_dialog);
                       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setTitle("Insert");

                        dialog.show();
                        final Spinner spinner = (Spinner) dialog.findViewById(R.id.sem_spinner);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                                R.array.sem_array, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);



                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                sem = parent.getItemAtPosition(pos).toString();
                            }
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        TextView bname= (TextView) dialog.findViewById(R.id.bname);
                        TextView aname= (TextView) dialog.findViewById(R.id.aname);
                        TextView sname= (TextView) dialog.findViewById(R.id.sname);
                         price=(EditText)dialog.findViewById(R.id.price);
                        Button dismiss=(Button)dialog.findViewById(R.id.dismiss);
                        Button insert =(Button)dialog.findViewById(R.id.insert);
                        bname.setText(jasonbook[position]);
                        aname.setText(jasonauthor[position]);
                        sname.setText(jasondept[position]);
                        dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        insert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ConnectionDetector cd=new ConnectionDetector(getActivity().getApplicationContext());
                                if (cd.isConnectingToInternet()) {
                                    pos=position;
                                    if(price.getText().toString().length()==0) {
                                        price.setError("Set Price");
                                    }
                                    if(sem.length()==0){

                                        Toast.makeText(getActivity(), "Select Sem", Toast.LENGTH_LONG).show();
                                    }else{

                                        new Insert().execute();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            }
                        });


                    }
                });

            }


        }
    }

    private class Insert extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
           nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Sending data to server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
            //Toast.makeText(getContext(),"fetching results",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... args){
            GPSTracker gps;
            gps = new GPSTracker(getActivity().getApplicationContext());
            double latitude = 10,longitude=10;
            if(gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            }else{

                gps.showSettingsAlert();
            }

                JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("price", price.getText().toString()));
            params2.add(new BasicNameValuePair("sem",sem));

            params2.add(new BasicNameValuePair("bookid", jsonid[pos]));
            params2.add(new BasicNameValuePair("lat", String.valueOf(latitude)));
            params2.add(new BasicNameValuePair("long", String.valueOf(longitude)));

            params2.add(new BasicNameValuePair("token", Login.token));

            jsonobject = jParser2.makeHttpRequest(posturl, "POST", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {
                        Splash.jasonbook.add(jasonbook[pos]);
                        Splash.jasonauthor.add(jasonauthor[pos]);
                        Splash.jasonsem.add(sem);
                        Splash.jasonprice.add(price.getText().toString());
                        Splash.jsondept.add(jasondept[pos]);
                        Splash.jsonid.add(jsonobject.getJSONObject("post").getString("_id"));
                        return true;
                        }

                    else{
                     return false;
                    }



                     }

                else{
                    //Toast.makeText(getContext(), "No response from server", Toast.LENGTH_LONG).show();
                    return false;
                }}



            catch (JSONException e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){
            nDialog.dismiss();
            if(!th){
                Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getActivity(), "Insert Successful", Toast.LENGTH_LONG).show();

            }


        }
    }









}
