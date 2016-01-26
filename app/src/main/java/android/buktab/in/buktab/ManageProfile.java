package android.buktab.in.buktab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
 * Created by adithya on 25/1/16.
 */
public class ManageProfile extends Fragment {

    View rootView;
    String url="http://52.10.251.227/:3000/updateProfile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.manageprofile, container, false);


        final TextView mpname,mpphone,mpmail;
        ImageButton ename,ephone,email;
        final EditText mppass;
        Button save;

        mpname=(TextView)rootView.findViewById(R.id.mpname);
        mpphone=(TextView)rootView.findViewById(R.id.mpphone);
        mpmail=(TextView)rootView.findViewById(R.id.mpemail);
        mppass=(EditText)rootView.findViewById(R.id.mppass);
        ename=(ImageButton)rootView.findViewById(R.id.nameeidt);
        ephone=(ImageButton)rootView.findViewById(R.id.phoneedit);
        email=(ImageButton)rootView.findViewById(R.id.mailedit);
        save=(Button)rootView.findViewById(R.id.editprof);



        mpname.setText(Login.username);
        mpphone.setText("9566099932");
        mpmail.setText("Tusnade@naruto.com");










        ename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mppass.getText().equals("password")){


                    new manage().execute();


                }

                else {

                    Toast.makeText(getContext().getApplicationContext(),"fetching results", Toast.LENGTH_SHORT).show();
                }
            }
        });






        return rootView;
    }



private class manage extends AsyncTask<String,String,Boolean>
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





        params2.add(new BasicNameValuePair("token",Login.token));
        params2.add(new BasicNameValuePair("password",Login.token));
        params2.add(new BasicNameValuePair("phonenumber",Login.token));
        params2.add(new BasicNameValuePair("email",Login.token));


        jsonobject = jParser2.makeHttpRequest(url, "POST", params2);

        try{
            if(jsonobject!=null){

                String result=jsonobject.getString("success");
                //String message=jsonobject.getString("message");

                if(result.equals("true"))
                {

                    return true;


                    }

                else{
                    return false;

                }



            }
        }



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

            Toast.makeText(getContext().getApplicationContext(), "Edit Failed", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getContext().getApplicationContext(), "Edit Successfull", Toast.LENGTH_LONG).show();

        }


    }
}}