package android.buktab.in.buktab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
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
 * Created by root on 9/8/15.
 */
public class SearchFragment extends Fragment {

    EditText search ,price;
   // Button searchbutton;
    ListView resultlist ;
    int count =0,pos=0;

    String[] jasonbook,jasonauthor,jasonsem,jasonprice,jsonname,jsonph,jsonmail,jsondept;





    String searchurl="http://52.10.251.227:3000/getAds/byName/";
    String posturl="http://52.10.251.227:3000/postBook";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fagment, container, false);
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
           String url=searchurl+search.getText().toString();
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
    }







}
