package android.buktab.in.buktab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 1/21/2016.
 */
public class insertintent extends AppCompatActivity {



    EditText price;
    Spinner spinner;
    String sem,id;
    TextView bn,an,dn;
    String posturl="http://52.10.251.227:3000/postBook";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_intent);

         id=getIntent().getExtras().getString("id");
         bn=(TextView)findViewById(R.id.bname);
         an=(TextView)findViewById(R.id.aname);
         dn=(TextView)findViewById(R.id.dept);

        bn.setText(getIntent().getExtras().getString("bookname"));
        an.setText(getIntent().getExtras().getString("author"));
        dn.setText(getIntent().getExtras().getString("dept"));

        price=(EditText)findViewById(R.id.price);
        spinner=(Spinner)findViewById(R.id.sem_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(insertintent.this,
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


        Button insert=(Button)findViewById(R.id.insert);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectionDetector cd=new ConnectionDetector(insertintent.this);
                if (cd.isConnectingToInternet()) {

                    if(price.getText().toString().length()==0) {
                        price.setError("Set Price");
                    }
                   else  if(sem.length()==0){

                        Toast.makeText(insertintent.this, "Select Sem", Toast.LENGTH_LONG).show();
                    }else{

                        new Insert().execute();
                    }
                } else {
                    Toast.makeText(insertintent.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }



            }
        });


    }



    private class Insert extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        /*   nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Sending data to server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();*/
            //Toast.makeText(getContext(),"fetching results",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... args){
            GPSTracker gps;
            gps = new GPSTracker(insertintent.this);
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

            params2.add(new BasicNameValuePair("bookid",id));
            params2.add(new BasicNameValuePair("lat", String.valueOf(latitude)));
            params2.add(new BasicNameValuePair("long", String.valueOf(longitude)));

            params2.add(new BasicNameValuePair("token", Login.token));

            jsonobject = jParser2.makeHttpRequest(posturl, "POST", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {
                        Splash.jasonbook.add(bn.getText().toString());
                        Splash.jasonauthor.add(an.getText().toString());
                        Splash.jasonsem.add(sem);
                        Splash.jasonprice.add(price.getText().toString());
                        Splash.jsondept.add(dn.getText().toString());
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
            // nDialog.dismiss();
            if(!th){

                Toast.makeText(insertintent.this, "Insert Failed", Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(insertintent.this, "Insert Successful", Toast.LENGTH_LONG).show();
                Splash.bookcount++;
                MainMenu.bcnt.setText("" + Splash.bookcount);
                Intent i=new Intent(insertintent.this,MainMenu.class);
                MainMenu.man=1;
                startActivity(i);
            }


        }
    }









}
