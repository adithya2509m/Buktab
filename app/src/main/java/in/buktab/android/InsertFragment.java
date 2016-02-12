package in.buktab.android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;
import java.util.ArrayList;
/**
 * Created by root on 9/8/15.
 */
public class InsertFragment extends Fragment {
   // RelativeLayout top;
    TextView listheader;
    EditText search ,price;
   // Button searchbutton;
    ListView resultlist ;
    int count =0,pos=0;
    String sem="";
    View rootView;
    int no=0;

    String[] jasonbook,jasonauthor,jasondept,jasonprice,jsonid;





    String searchurl="http://52.10.251.227:3000/listBooksForAdd";
    String posturl="http://52.10.251.227:3000/postBook";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.insert_fagment, container, false);
        search = (EditText) rootView.findViewById(R.id.search);
       // searchbutton = (Button) rootView.findViewById(R.id.searchbutton);
        resultlist = (ListView) rootView.findViewById(R.id.resultlist);

        listheader=(TextView)rootView.findViewById(R.id.message);

      //  top=(RelativeLayout)rootView.findViewById(R.id.top_layout);

       // isFirstTime();

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
       search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(search.getText().length() < 3){


                }
                else{
        //            top.setVisibility(View.INVISIBLE);

                    startAnim();
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


        rootView.findViewById(R.id.suggest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplicationContext(),Suggest.class);
                startActivity(i);
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
        //    Toast.makeText(getActivity().getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();
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
                        no=1;
                        return false;
                      //  Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                    }



                    return true; }

                else{
                  //  Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();
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

                if(no==0)
                Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();
                else {
                    //Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                    stopAnim();
                   // top.setVisibility(View.VISIBLE);
                    //TextView m=(TextView)rootView.findViewById(R.id.message);
                    listheader.setText("No Such Book.You could suggest the book to us.");
                    rootView.findViewById(R.id.suggest).setVisibility(View.VISIBLE);
                }
            }else{

                ListAdapter EventList= new customlist(getActivity(),jasonbook,jasondept,jasonauthor,jasonprice);
                stopAnim();
                rootView.findViewById(R.id.suggest).setVisibility(View.GONE);
                listheader.setText("Select book to set price and post");
                resultlist.setAdapter(EventList);
                resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        //  Toast.makeText(getActivity().getApplicationContext(),"Heloo",Toast.LENGTH_SHORT).show();


                        Intent i = new Intent(getActivity(), insertintent.class);
                        i.putExtra("bookname",jasonbook[position]);
                        i.putExtra("author",jasonauthor[position]);
                        i.putExtra("dept",jasondept[position]);
                        i.putExtra("id",jsonid[pos]);
                        //getActivity().finish();
                        startActivity(i);




            }


        });
    }}}










}
