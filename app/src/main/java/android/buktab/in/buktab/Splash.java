package android.buktab.in.buktab;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Splash extends Activity {

    /** Duration of wait **/
    static ArrayList<String> jasonbook,jasonauthor,jasonsem,jasonprice,jsondept,jsonid,resbook,resauthor,ressem,resprice,resdept,resid,respub,resphone,resname,resemail,reslocation,resgender;
    String url="http://52.10.251.227:3000/manageBooks";
    String resurl="http://52.10.251.227:3000/recent";
    String token;
    int log=0;
    static int  bookcount=0;
    static Spanned[] stringsuper;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        String superscript[] = getResources().getStringArray(R.array.sem_super);
    stringsuper=new Spanned[superscript.length];
        for(int i=0;i<superscript.length;i++)
        stringsuper[i]=Html.fromHtml((i) + "<sup><small>" + superscript[i] + "</small></sup> Sem");



        jasonauthor = new ArrayList<String>();
        jasonbook = new ArrayList<String>();
        reslocation = new ArrayList<String>();
        jasonsem = new ArrayList<String>();
        jasonprice = new ArrayList<String>();
        jsondept = new ArrayList<String>();
        jsonid = new ArrayList<String>();
        resbook=new ArrayList<String>();
        resgender=new ArrayList<String>();

        resauthor=new ArrayList<String>();
        resdept=new ArrayList<String>();
        ressem=new ArrayList<String>();
        resprice=new ArrayList<String>();
        respub=new ArrayList<String>();
        resemail=new ArrayList<String>();
        resphone=new ArrayList<String>();
        resname=new ArrayList<String>();
        resid=new ArrayList<String>();

        startAnim();

        final ConnectionDetector cd = new ConnectionDetector(Splash.this);
        if (cd.isConnectingToInternet()) {
            new splashres().execute();
        } else {
            Toast.makeText(Splash.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    void startAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
        final ImageView iv = (ImageView) findViewById(R.id.splashscreen1);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    void stopAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }



    private class splashres extends AsyncTask<String,String,Boolean>
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
                        resbook.clear();
                        resgender.clear();
                        resauthor.clear();
                        ressem.clear();
                        resprice.clear();
                        resdept.clear();
                        resemail.clear();
                        resphone.clear();
                        resname.clear();
                        resid.clear();

                        for(int i =1;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                            JSONObject temp3=temp.getJSONArray("_creator").getJSONObject(0);
                            resbook.add(temp2.getString("Name"));
                            resauthor.add(temp2.getString("Author"));
                            ressem.add(temp.getString("Semester"));
                            resprice.add(temp.getString("Price"));
                            resdept.add(temp2.getString("Department"));
                            respub.add(temp2.getString("Publisher"));
                            resemail.add(temp3.getString("email"));
                            resphone.add(temp3.getString("phoneNo"));
                            resgender.add(temp3.getString("sex"));
                            reslocation.add(temp.getString("location"));
                            resname.add(temp3.getString("username"));


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

                    Toast.makeText(Splash.this, "No response from server", Toast.LENGTH_LONG).show();

            }else{
                new splashmanage().execute();

            }


        }
    }













































    private class splashmanage extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
         /*   nDialog = new ProgressDialog(Splash.this);
            nDialog.setTitle("Fetching data from server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
            Toast.makeText(Splash.this.getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();*/
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

                return false;
            }

            params2.add(new BasicNameValuePair("token", token));

            jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {


                        JSONArray jsonArray=jsonobject.getJSONArray("result");
                        int len=jsonArray.length();

                        bookcount=jsonArray.length();
                        for(int i =0;i<jsonArray.length();i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            JSONObject temp2=temp.getJSONArray("bookDetails").getJSONObject(0);
                            jasonbook.add(temp2.getString("Name"));
                            jasonauthor.add(temp2.getString("Author"));
                            jasonsem.add(temp.getString("Semester"));
                            jasonprice.add(temp.getString("Price"));
                            jsondept.add(temp2.getString("Department"));

                            jsonid.add(temp.getString("_id"));


                        }

                        return true;

                    }

                    else{
                        return false;
                    }

                }

                else{
                    log=1;
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
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms

                    }
                }, 3000);
                Intent mainIntent = new Intent(Splash.this,Login.class);
                stopAnim();
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();


                }
                else{

                   if(log==1){
                       Toast.makeText(Splash.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    //   nDialog.dismiss();

                   }
                   else{
                    //   nDialog.dismiss();
                       final Handler handler = new Handler();
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               Intent mainIntent = new Intent(Splash.this, Login.class);
                               stopAnim();
                               Splash.this.startActivity(mainIntent);
                               Splash.this.finish();
                               // Do something after 5s = 5000ms


                           }
                       }, 3000);


                   }


                }


        }
    }

}