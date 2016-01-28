package android.buktab.in.buktab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class ManageIntent extends AppCompatActivity {

    //GPSTracker gps;


    EditText price;
    Spinner spinner;
    String sem,id;
    TextView bn,an,dn,rs;
    String posturl="http://52.10.251.227:3000/update";
    //double latitude = 10,longitude=10;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_intent);

        Toolbar mtoolbar =(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(mtoolbar);

        //gps = new GPSTracker(ManageIntent.this);
         id=getIntent().getExtras().getString("id");
         bn=(TextView)findViewById(R.id.bname);
         an=(TextView)findViewById(R.id.aname);
         dn=(TextView)findViewById(R.id.dept);


        EditText location=(EditText)findViewById(R.id.location);
        location.setVisibility(View.GONE);
        rs=(TextView)findViewById(R.id.rs);
        rs.setText("\u20B9");


/*
        if(gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{

            gps.showSettingsAlert();
        }

*/

        bn.setText(getIntent().getExtras().getString("bookname").trim());
        an.setText(getIntent().getExtras().getString("author").trim());
        dn.setText(getIntent().getExtras().getString("dept").trim());

        //ImageView uicon=(ImageView)findViewById(R.id.uicon);
        //scaleImage(uicon, 125);

        price=(EditText)findViewById(R.id.price);
        spinner=(Spinner)findViewById(R.id.sem_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ManageIntent.this,
                R.array.sem_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(Integer.parseInt(getIntent().getExtras().getString("sem").toString()));
        price.setText(getIntent().getExtras().getString("price").toString());


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                sem = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button insert=(Button)findViewById(R.id.insert);
        insert.setText("Update");

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectionDetector cd = new ConnectionDetector(ManageIntent.this);
                if (cd.isConnectingToInternet()) {

                    if (price.getText().toString().length() == 0) {
                        price.setError("Set Price");
                    } else if (sem.equals("Sem")) {

                        Toast.makeText(ManageIntent.this, "Select Sem", Toast.LENGTH_LONG).show();
                    } else {

                        new Insert().execute();
                    }
                } else {
                    Toast.makeText(ManageIntent.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
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




            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("price", price.getText().toString()));
            params2.add(new BasicNameValuePair("sem",sem));

            params2.add(new BasicNameValuePair("id",id));
          //  params2.add(new BasicNameValuePair("lat", String.valueOf(latitude)));
          //  params2.add(new BasicNameValuePair("long", String.valueOf(longitude)));

            params2.add(new BasicNameValuePair("token", Login.token));

            jsonobject = jParser2.makeHttpRequest(posturl, "POST", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {
                        int pos=getIntent().getExtras().getInt("position");
                        Splash.jasonbook.remove(pos);
                        Splash.jasonauthor.remove(pos);
                        Splash.jasonsem.remove(pos);
                        Splash.jasonprice.remove(pos);
                        Splash.jsondept.remove(pos);
                        //Splash.jsonid.remove(pos);


                        Splash.jasonbook.add(bn.getText().toString());
                        Splash.jasonauthor.add(an.getText().toString());
                        Splash.jasonsem.add(sem);
                        Splash.jasonprice.add(price.getText().toString());
                        Splash.jsondept.add(dn.getText().toString());
                        //Splash.jsonid.add(jsonobject.getJSONObject("post").getString("_id"));
                        return false;
                    }

                    else{
                        return true;
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

                Toast.makeText(ManageIntent.this, "Updated Successful", Toast.LENGTH_LONG).show();


                Intent i=new Intent(ManageIntent.this,MainMenu.class);
                MainMenu.man=1;
                startActivity(i);

            }else{
                Toast.makeText(ManageIntent.this, "Update Failed", Toast.LENGTH_LONG).show();

            }


        }
    }





    private void scaleImage(ImageView view, int boundBoxInDp)
    {
        // Get the ImageView and its bitmap
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

        // Get current dimensions
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }




}
