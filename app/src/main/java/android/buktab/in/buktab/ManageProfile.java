package android.buktab.in.buktab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

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
    EditText mpphone,mpmail,mppass,edmail,edphone;
    boolean e=false,p=false;
    String url="http://52.10.251.227/:3000/updateProfile";

    ViewAnimator viewAnimator1,viewAnimator2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.manageprofile, container, false);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        viewAnimator1 = (ViewAnimator) rootView.findViewById(R.id.viewAnimator1);
        viewAnimator2=(ViewAnimator)rootView.findViewById(R.id.viewAnimator2);

        final Animation inAnim = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);
        final Animation outAnim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        viewAnimator1.setInAnimation(inAnim);
        viewAnimator1.setOutAnimation(outAnim);

        viewAnimator2.setInAnimation(inAnim);
        viewAnimator2.setOutAnimation(outAnim);




        TextView mpname;
        final ImageButton ename,ephone,email;

        Button save;

        mpname=(TextView)rootView.findViewById(R.id.mpname);
        mpphone=(EditText)rootView.findViewById(R.id.mpphone);
        mpmail=(EditText)rootView.findViewById(R.id.mpemail);
        mppass=(EditText)rootView.findViewById(R.id.mppass);
        //ename=(ImageButton)rootView.findViewById(R.id.nameeidt);
        ephone=(ImageButton)rootView.findViewById(R.id.phoneedit);
        email=(ImageButton)rootView.findViewById(R.id.mailedit);
        save=(Button)rootView.findViewById(R.id.editprof);

        edmail=(EditText)rootView.findViewById(R.id.empemail);
        edphone=(EditText)rootView.findViewById(R.id.empphone);



        mpname.setText(Login.username);
        mpphone.setText(Login.phone);
        mpmail.setText(Login.mail);












        ephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!p) {
                    viewAnimator1.showNext();
                    ephone.setImageResource(R.drawable.ic_check_black_24dp);
                    p=true;
                } else {
                    //viewAnimator1.showPrevious();
                    //ephone.setImageResource(R.drawable.ic_edit_black_24dp);
                    String MobilePattern = "[0-9]{10}";
                    String inbuilt = "^+91[0-9]{10}$";
                    if (edphone.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Enter Valid Phone Number", Toast.LENGTH_LONG).show();

                    } else if (!edphone.getText().toString().matches(MobilePattern) && !edphone.getText().toString().matches(inbuilt))

                        Toast.makeText(getActivity(), "Enter Valid Phone Number", Toast.LENGTH_LONG).show();


                    else {

                        final ConnectionDetector cd = new ConnectionDetector(getActivity());
                        if (cd.isConnectingToInternet()) {

                            JSONObject jsonobject4;
                            final JSONParser jParser4 = new JSONParser();
                            List<NameValuePair> params4 = new ArrayList<NameValuePair>();

                            String url = "http://52.10.251.227:3000/validate/phoneNo/" + edphone.getText();
                           // startAnim();
                            jsonobject4 = jParser4.makeHttpRequest(url, "GET", params4);

                            try {
                                if (jsonobject4 != null) {

                                    String result2 = jsonobject4.getString("success");

                                    if (result2.equals("false")) {

                                        Toast.makeText(getActivity(), "Phone Number Taken", Toast.LENGTH_LONG).show();



                                    } else {
                                        viewAnimator1.showPrevious();
                                        ephone.setImageResource(R.drawable.ic_edit_black_24dp);
                                        mpphone.setText(edphone.getText().toString());
                                        p=false;

                                        View v = getActivity().getCurrentFocus();
                                        if (v != null) {
                                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                        }

                                    }

                                } else {
                                    Toast.makeText(getActivity(), "No Response From Server", Toast.LENGTH_LONG).show();


                                }


                            } catch (Exception e) {
                                Log.e("Error", e.getMessage());
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                            //  circularButton2.setProgress(-1);

                        }


                    }


                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!e) {
                    viewAnimator2.showNext();
                    email.setImageResource(R.drawable.ic_check_black_24dp);
                    e=true;
                } else {
                    if (edmail.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Enter Mail ID", Toast.LENGTH_LONG).show();

                    } else {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edmail.getText()).matches()) {

                            Toast.makeText(getActivity(), "Invalid Mail", Toast.LENGTH_LONG).show();

                        }


                    }
                    viewAnimator2.showPrevious();
                    email.setImageResource(R.drawable.ic_edit_black_24dp);
                    mpmail.setText(edmail.getText().toString());

                    e=false;

                    View v = getActivity().getCurrentFocus();
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }


                }
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mppass.getText().equals("")) {


                    new manage().execute();


                } else {

                    Toast.makeText(getContext().getApplicationContext(), "Enter Your Password to Update", Toast.LENGTH_SHORT).show();
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
        params2.add(new BasicNameValuePair("password",mppass.getText().toString()));
        params2.add(new BasicNameValuePair("phonenumber",mpphone.getText().toString()));
        params2.add(new BasicNameValuePair("email",mpmail.getText().toString()));


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
            Toast.makeText(getContext().getApplicationContext(), "Edit Successful", Toast.LENGTH_LONG).show();
            Login.mail=mpmail.getText().toString();
            Login.phone=mpphone.getText().toString();



            SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();



            editor.putString("mail", Login.mail);
            editor.putString("phone", Login.phone);
            editor.commit();



        }


    }
}



}