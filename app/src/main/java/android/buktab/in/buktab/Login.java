 package android.buktab.in.buktab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity {


    final String loginurl="http://52.10.251.227:3000/login";
    String url="http://52.10.251.227:3000/manageBooks";
    String resurl="http://52.10.251.227:3000/recent";
    static String token="";
    static String username="";
     CircularProgressButton circularButton1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       final EditText Uname=(EditText) findViewById(R.id.Uname);
        final EditText Pword=(EditText) findViewById(R.id.Pword);
        TextView register=(TextView) findViewById(R.id.register);


        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String islogged=pref.getString("isloggedin",null);
        if(islogged!=null && islogged.equals("true")){
            token=pref.getString("Token",null);
            username=pref.getString("User",null);
            Intent i=new Intent(Login.this,MainMenu.class);
            startActivity(i);

        }


        register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i=new Intent(Login.this,Register.class);
                startActivity(i);

            }
        });





         circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 circularButton1.setProgress(50);

                 String user=Uname.getText().toString();
                 String pass=Pword.getText().toString();

                final ConnectionDetector cd = new ConnectionDetector(Login.this);
                if(cd.isConnectingToInternet()){


                    JSONObject jsonobject;
                    final JSONParser jParser1 = new JSONParser();
                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("password",pass));
                    params1.add(new BasicNameValuePair("username", user));


                    jsonobject = jParser1.makeHttpRequest(loginurl, "POST", params1);

                    try{
                        if(jsonobject!=null){

                            String result=jsonobject.getString("success");

                            if(result.equals("true"))
                            {
                                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor  editor = pref.edit();
                                token=jsonobject.getString("token");
                                username=user;
                                editor.putString("Token",token );
                                editor.putString("User",username );
                                editor.putString("isloggedin", "true");
                                editor.commit();



                                final ConnectionDetector cd1 = new ConnectionDetector(Login.this);
                                if (cd.isConnectingToInternet()) {

                                    new loginres().execute();
                                } else {
                                    Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                    circularButton1.setProgress(-1);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            circularButton1.setProgress(0);
                                            circularButton1.setIndeterminateProgressMode(true);


                                        }
                                    }, 3000);
                                }





                                Intent i=new Intent(Login.this,MainMenu.class);
                                startActivity(i);
                            }

                            else{
                                Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                                circularButton1.setProgress(-1);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 5s = 5000ms
                                        circularButton1.setProgress(0);
                                        circularButton1.setIndeterminateProgressMode(true);


                                    }
                                }, 3000);

                            }

                        }
                        else{
                            Toast.makeText(Login.this, "No Response From Server", Toast.LENGTH_LONG).show();
                            circularButton1.setProgress(-1);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    circularButton1.setProgress(0);
                                    circularButton1.setIndeterminateProgressMode(true);

                                }
                            }, 3000);

                        }



                    }catch (Exception e){
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }






                }else{
                    circularButton1.setProgress(-1);
                    Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_LONG).show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            circularButton1.setProgress(0);
                            circularButton1.setIndeterminateProgressMode(true);

                        }
                    }, 3000);
                }








            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private class loginmanage extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        /*    nDialog = new ProgressDialog(Login.this);
            nDialog.setTitle("Fetching data from server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
            Toast.makeText(Login.this.getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();*/
        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String islogged=pref.getString("isloggedin",null);


            params2.add(new BasicNameValuePair("token", Login.token));

            jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {


                        JSONArray jsonArray=jsonobject.getJSONArray("result");
                        int len=jsonArray.length();

                        Splash.bookcount=jsonArray.length();
                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                           Splash.jasonbook.add(temp2.getString("Name"));
                           Splash.jasonauthor.add(temp2.getString("Author"));
                           Splash.jasonsem.add(temp.getString("Semester"));
                           Splash.jasonprice.add(temp.getString("Price"));
                           Splash.jsondept.add(temp2.getString("Department"));


                        }

                        return true;

                    }

                    else{
                        return true;
                    }

                }

                else{

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
              //  nDialog.dismiss();
                circularButton1.setProgress(-1);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        circularButton1.setProgress(0);
                        circularButton1.setIndeterminateProgressMode(true);

                    }
                }, 3000);
                Toast.makeText(Login.this, "No server response", Toast.LENGTH_LONG).show();




            }
            else{
                circularButton1.setProgress(100);
              //  nDialog.dismiss();
                Intent i=new Intent(Login.this,MainMenu.class);
                startActivity(i);



            }


        }
    }





    private class loginres extends AsyncTask<String,String,Boolean>
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
            //  Toast.makeText(getActivity().getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();


            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String islogged=pref.getString("isloggedin",null);
            if(islogged!=null && islogged.equals("true")){
                token=pref.getString("Token",null);}

            else
            {

                return true;
            }


            params2.add(new BasicNameValuePair("token", token));

            jsonobject = jParser2.makeHttpRequest(resurl, "GET", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");
                    //String message=jsonobject.getString("message");

                    if(result.equals("true"))
                    {


                        JSONArray jsonArray=jsonobject.getJSONArray("result");

                        int len=jsonArray.length();
                        Splash.resbook.clear();
                        Splash.resauthor.clear();
                        Splash.ressem.clear();
                        Splash.resprice.clear();
                        Splash.resdept.clear();
                        Splash.resemail.clear();
                        Splash.resphone.clear();
                        Splash.resname.clear();
                        Splash.resid.clear();

                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                            JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                            Splash.resbook.add(temp2.getString("Name"));
                            Splash.resauthor.add(temp2.getString("Author"));
                            Splash.ressem.add(temp.getString("Semester"));
                            Splash.resprice.add(temp.getString("Price"));
                            Splash.resdept.add(temp2.getString("Department"));
                            Splash.respub.add(temp2.getString("Publisher"));
                            Splash.resemail.add(temp3.getString("email"));
                            Splash.resphone.add(temp3.getString("phoneNo"));
                            Splash.reslocation.add(temp.getString("location"));
                            Splash.resname.add(temp3.getString("username"));


                        }}

                    else{
                        return true;
                        // Toast.makeText(getActivity(), "No such book", Toast.LENGTH_LONG).show();
                    }



                    return true; }

                else{
                    // Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();
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

                Toast.makeText(Login.this, "No response from server", Toast.LENGTH_LONG).show();

            }else{
                new loginmanage().execute();

            }


        }
    }









}
