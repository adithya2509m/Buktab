package in.buktab.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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
 * Created by Arunkumar on 2/12/2016.
 */
public class Suggest extends AppCompatActivity {

    String suggesturl="http://52.10.251.227:3000/suggestBook";
      EditText sugbname,suganame;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest);


        Toolbar mtoolbar =(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(mtoolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title=(TextView)mtoolbar.findViewById(R.id.text_view_toolbar_title);
        title.setText("Suggest Books");
       getSupportActionBar().setHomeButtonEnabled(true);
      final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.cpb_white), PorterDuff.Mode.SRC_ATOP);
       getSupportActionBar().setHomeAsUpIndicator(upArrow);




        sugbname=(EditText)findViewById(R.id.sugbname);
        suganame=(EditText)findViewById(R.id.suganame);



        Button suggestbook=(Button)findViewById(R.id.suggestbook);

        suggestbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final ConnectionDetector cd = new ConnectionDetector(Suggest.this);
                if (cd.isConnectingToInternet()) {
                   new jsonsuggest().execute();

                }


                else{

                    Toast.makeText(Suggest.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // your code.
        NavUtils.navigateUpFromSameTask(this);
    }



    private class jsonsuggest extends AsyncTask<String,String,Boolean>
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

            params2.add(new BasicNameValuePair("token", Login.token));
            params2.add(new BasicNameValuePair("name",sugbname.getText().toString()));
            params2.add(new BasicNameValuePair("author",suganame.getText().toString()));


            jsonobject = jParser2.makeHttpRequest(suggesturl, "POST", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {
                        //Toast.makeText(Suggest.this, "Your suggestion has been sent", Toast.LENGTH_LONG).show();
                        return true;
                    }





                    else{

                          return false;
                    }



                     }

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
                    Toast.makeText(Suggest.this, "Suggestion failed", Toast.LENGTH_LONG).show();}
                else{

                Toast.makeText(Suggest.this, "Your suggestion has been sent", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Suggest.this, MainMenu.class);
                    //getActivity().finish();
                    startActivity(i);


                }
            }}











}
