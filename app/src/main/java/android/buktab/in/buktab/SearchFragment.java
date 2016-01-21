package android.buktab.in.buktab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 9/8/15.
 */
public class SearchFragment extends Fragment {

    EditText search ,price;
    String[] jsonname1,jsonph1,jsonmail1;
   // Button searchbutton;
    ListView resultlist ;
    int count =0,pos=0,objectcount;
    View rootView;
    private jasonsearch searchquery;

    android.support.design.widget.FloatingActionButton filter;

    ArrayList<String> jasonbook,jasonauthor,jasonsem,jasonprice,jsonname,jsonph,jsonmail,jsondept;





    String searchurl="http://52.10.251.227:3000/getAds/byName/";
    String posturl="http://52.10.251.227:3000/postBook";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.search_fagment, container, false);
        search = (EditText) rootView.findViewById(R.id.search);

        jasonbook= new ArrayList<String>();
        jasonauthor= new ArrayList<String>();
        jasonsem= new ArrayList<String>();
        jasonprice= new ArrayList<String>();
        jsonname= new ArrayList<String>();
        jsonph= new ArrayList<String>();
        jsonmail= new ArrayList<String>();
        jsondept= new ArrayList<String>();

       // searchbutton = (Button) rootView.findViewById(R.id.searchbutton);
        resultlist = (ListView) rootView.findViewById(R.id.resultlist);
        filter=(android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab);



       search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(count<3){
                    count++;

                }
                else{
                    startAnim();
                    final ConnectionDetector cd = new ConnectionDetector(getActivity());
                    if (cd.isConnectingToInternet()) {

                        searchquery= new jasonsearch();
                        searchquery.execute();

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



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(getActivity(), R.style.NewDialog);

                dialog.setContentView(R.layout.fab_dialog);
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setTitle("Filter");


                final Spinner dropdownsem = (Spinner) dialog.findViewById(R.id.semdrop);
                String[] items = getResources().getStringArray(R.array.sem_array);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
                dropdownsem.setAdapter(adapter);


                final Spinner dropdowndept = (Spinner) dialog.findViewById(R.id.deptdrop);
                String[] items1 = getResources().getStringArray(R.array.dept_array);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items1);
                dropdowndept.setAdapter(adapter1);

                dialog.show();

                Button fil = (Button) dialog.findViewById(R.id.filter);


                fil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startAnim();
                        ArrayList<String> jasonbookf,jasonauthorf,jasonsemf,jasonpricef,jsonnamef,jsonphf,jsonmailf,jsondeptf;
                        jasonbookf= new ArrayList<String>();
                        jasonauthorf= new ArrayList<String>();
                        jasonsemf= new ArrayList<String>();
                        jasonpricef= new ArrayList<String>();
                        jsonnamef= new ArrayList<String>();
                        jsonphf= new ArrayList<String>();
                        jsonmailf= new ArrayList<String>();
                        jsondeptf= new ArrayList<String>();

                        String filtersem = dropdownsem.getSelectedItem().toString();
                        String filterdept = dropdowndept.getSelectedItem().toString();
                        int  check = -1;



                        int[] index = new int[objectcount];

                        if (filtersem.equals("Sem") && filterdept.equals("ALL")) {
                            check = 0;

                        }


                        if (!filtersem.equals("Sem") && filterdept.equals("ALL")) {
                            Iterator<String> iterator1 = jasonbook.iterator();
                            Iterator<String> iterator2 = jasonauthor.iterator();
                            Iterator<String> iterator3 = jasonsem.iterator();
                            Iterator<String> iterator4 = jasonprice.iterator();
                            Iterator<String> iterator5 = jsonname.iterator();
                            Iterator<String> iterator6 = jsonph.iterator();
                            Iterator<String> iterator7 = jsonmail.iterator();
                            Iterator<String> iterator8 = jsondept.iterator();

                            while(iterator3.hasNext()) {
                                String string = iterator3.next();










                                if (string.equals(filtersem)) {
                                    jasonbookf.add(iterator1.next());
                                    jasonauthorf.add( iterator2.next());
                                    jasonsemf.add(string);
                                    jasonpricef.add(iterator4.next());
                                    jsonnamef.add(iterator5.next());
                                    jsonphf.add(iterator6.next());
                                    jsonmailf.add(iterator7.next());
                                    jsondeptf.add(iterator8.next());



                                 /*   iterator1.remove();

                                    iterator2.remove();
                                    iterator3.remove();
                                    iterator4.remove();
                                    iterator5.remove();
                                    iterator6.remove();
                                    iterator7.remove();
                                    iterator8.remove();
                                */




                                }else{

                                    iterator1.next();
                                    iterator2.next();
                                    iterator4.next();
                                    iterator5.next();
                                    iterator6.next();
                                    iterator7.next();
                                    iterator8.next();

                                }
                            }


                        } else if (filtersem.equals("Sem") && !filterdept.equals("ALL")) {

                            Iterator<String> iterator1 = jasonbook.iterator();
                            Iterator<String> iterator2 = jasonauthor.iterator();
                            Iterator<String> iterator3 = jasonsem.iterator();
                            Iterator<String> iterator4 = jasonprice.iterator();
                            Iterator<String> iterator5 = jsonname.iterator();
                            Iterator<String> iterator6 = jsonph.iterator();
                            Iterator<String> iterator7 = jsonmail.iterator();
                            Iterator<String> iterator8 = jsondept.iterator();

                            while(iterator8.hasNext()) {
                                String string = iterator8.next();







                                if (string.equals(filterdept)) {


                                    jasonbookf.add(iterator1.next());
                                    jasonauthorf.add( iterator2.next());
                                    jasonsemf.add(iterator3.next());
                                    jasonpricef.add(iterator4.next());
                                    jsonnamef.add(iterator5.next());
                                    jsonphf.add(iterator6.next());
                                    jsonmailf.add(iterator7.next());
                                    jsondeptf.add(string);




                                }else{

                                    iterator1.next();
                                    iterator2.next();
                                    iterator4.next();
                                    iterator5.next();
                                    iterator6.next();
                                    iterator7.next();
                                    iterator3.next();

                                }
                            }


                        } else if (!filtersem.equals("Sem") && !filterdept.equals("ALL")) {

                            Iterator<String> iterator1 = jasonbook.iterator();
                            Iterator<String> iterator2 = jasonauthor.iterator();
                            Iterator<String> iterator3 = jasonsem.iterator();
                            Iterator<String> iterator4 = jasonprice.iterator();
                            Iterator<String> iterator5 = jsonname.iterator();
                            Iterator<String> iterator6 = jsonph.iterator();
                            Iterator<String> iterator7 = jsonmail.iterator();
                            Iterator<String> iterator8 = jsondept.iterator();

                            while(iterator3.hasNext()) {
                                String string1 = iterator3.next();
                                String string2=iterator8.next();








                                if (string1.equals(filtersem) && string2.equals(filterdept)) {


                                    jasonbookf.add(iterator1.next());
                                    jasonauthorf.add( iterator2.next());
                                    jasonsemf.add(string1);
                                    jasonpricef.add(iterator4.next());
                                    jsonnamef.add(iterator5.next());
                                    jsonphf.add(iterator6.next());
                                    jsonmailf.add(iterator7.next());
                                    jsondeptf.add(string2);





                                }else{

                                    iterator1.next();
                                    iterator2.next();
                                    iterator4.next();
                                    iterator5.next();
                                    iterator6.next();
                                    iterator7.next();


                                }


                        }
                        }

                        if (check == 0) {
                            stopAnim();
                            dialog.dismiss();
                        } else {


                            ListAdapter EventList = new customlist2(getActivity(), jasonbookf, jasonsemf, jasonauthorf, jasonpricef, jsondeptf);
                            stopAnim();
                            resultlist.setAdapter(EventList);

                            dialog.dismiss();

                            resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Heloo", Toast.LENGTH_SHORT).show();
                                    final Dialog dialog1 = new Dialog(getActivity(), R.style.NewDialog);

                                    dialog1.setContentView(R.layout.search_dialog);
                                    // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog1.setTitle("Get it from...");

                                    dialog1.show();

                                    TextView bname = (TextView) dialog1.findViewById(R.id.bname);
                                    TextView aname = (TextView) dialog1.findViewById(R.id.aname);
                                    TextView sname = (TextView) dialog1.findViewById(R.id.sname);

                                    Button dismiss = (Button) dialog1.findViewById(R.id.dismiss);
                                    Button insert = (Button) dialog1.findViewById(R.id.insert);
                                    bname.setText(jsonname1[position]);
                                    aname.setText(jsonph1[position]);
                                    sname.setText(jsonmail1[position]);
                                    dismiss.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog1.dismiss();
                                        }
                                    });

                                    insert.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            dialog1.dismiss();
                                            String numberToDial = "tel:" + jsonph.get(position);
                                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

                                        }
                                    });


                                }
                            });
                        }


                    }
                });
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


                        JSONArray jsonArray=jsonobject.getJSONArray("result");objectcount=jsonArray.length();
                        int len=jsonArray.length();
                        jasonbook.clear();
                        jasonauthor.clear();
                        jasonsem.clear();
                        jasonprice.clear();
                        jsondept.clear();
                        jsonmail.clear();
                        jsonph.clear();
                        jsonname.clear();

                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                            JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                            jasonbook.add(temp2.getString("Name"));
                            jasonauthor.add(temp2.getString("Author"));
                            jasonsem.add(temp.getString("Semester"));
                            jasonprice.add(temp.getString("Price"));
                            jsondept.add(temp2.getString("Department"));
                            jsonmail.add(temp3.getString("email"));
                            jsonph.add(temp3.getString("phoneNo"));
                            jsonname.add(temp3.getString("username"));

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
                stopAnim();
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
                stopAnim();
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
                        bname.setText(jsonname.get(position));
                        aname.setText(jsonph.get(position));
                        sname.setText(jsonmail.get(position));
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
                                String numberToDial = "tel:"+jsonph.get(position);
                                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

                            }
                        });


                    }
                });

            }


        }
    }







}
