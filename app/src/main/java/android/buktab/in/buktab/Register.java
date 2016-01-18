package android.buktab.in.buktab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 12/6/2015.
 */
public class Register extends AppCompatActivity {

    String Regsiterurl="http://52.10.251.227:3000/save";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        final EditText Runame=(EditText) findViewById(R.id.Runame);
        final EditText Rpword=(EditText) findViewById(R.id.Rpword);
        final EditText Remail=(EditText) findViewById(R.id.Remail);
        final EditText Rphone=(EditText) findViewById(R.id.Rphone);

        final CircularProgressButton circularButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);
        circularButton2.setIndeterminateProgressMode(true);


        Runame.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            @Override
                                            public void onFocusChange(View v, boolean hasFocus) {

                                                if (!hasFocus) {

                                                    if (Runame.getText().equals("")) {
                                                        Runame.setError("Please enter username");

                                                    } else {

                                                        if (Runame.getText().length() < 4) {

                                                            Runame.setError("Username should have more than 4 characters");


                                                        }

                                                        final ConnectionDetector cd = new ConnectionDetector(Register.this);
                                                        if (cd.isConnectingToInternet()) {

                                                            JSONObject jsonobject3;
                                                            final JSONParser jParser3 = new JSONParser();
                                                            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

                                                            String url = "http://52.10.251.227:3000/validate/username/" + Runame.getText();

                                                            jsonobject3 = jParser3.makeHttpRequest(url, "GET", params3);

                                                            try {
                                                                if (jsonobject3 != null) {

                                                                    String result1 = jsonobject3.getString("success");

                                                                    if (result1.equals("false")) {

                                                                        Runame.setError("Username already taken");


                                                                    }

                                                                } else {
                                                                    Toast.makeText(Register.this, "No Response From Server", Toast.LENGTH_LONG).show();


                                                                }


                                                            } catch (Exception e) {
                                                                Log.e("Error", e.getMessage());
                                                                e.printStackTrace();
                                                            }

                                                        } else {
                                                            Toast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            }
                                        }

        );





        Rpword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if(Rpword.getText().equals("")){

                        Rpword.setError("Please enter password");

                    }



                }}});


        Remail.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if (Remail.getText().equals("")) {

                        Remail.setError("Please enter emailid");

                    } else {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Remail.getText()).matches()) {

                            Remail.setError("Invalid emailid");

                        }

                    }}
            }
        });


        Rphone.setOnFocusChangeListener(new View.OnFocusChangeListener()

                                        {
                                            @Override
                                            public void onFocusChange(View v, boolean hasFocus) {

                                                if (!hasFocus) {

                                                    if(Rphone.getText().equals("")){

                                                        Rphone.setError("Please enter the phone number");

                                                    }

                                                    else{

                                                    String MobilePattern = "[0-9]{10}";

                                                    if (!Rphone.getText().toString().matches(MobilePattern)) {

                                                        Rphone.setError("Enter a valid phone number with 10 digits");

                                                    } else {

                                                        final ConnectionDetector cd = new ConnectionDetector(Register.this);
                                                        if (cd.isConnectingToInternet()) {

                                                            JSONObject jsonobject4;
                                                            final JSONParser jParser4 = new JSONParser();
                                                            List<NameValuePair> params4 = new ArrayList<NameValuePair>();

                                                            String url = "http://52.10.251.227:3000/validate/phoneNo/" + Rphone.getText();

                                                            jsonobject4 = jParser4.makeHttpRequest(url, "GET", params4);

                                                            try {
                                                                if (jsonobject4 != null) {

                                                                    String result2 = jsonobject4.getString("success");

                                                                    if (result2.equals("false")) {

                                                                        Rphone.setError("Phone number already registered");


                                                                    }

                                                                } else {
                                                                    Toast.makeText(Register.this, "No Response From Server", Toast.LENGTH_LONG).show();


                                                                }


                                                            } catch (Exception e) {
                                                                Log.e("Error", e.getMessage());
                                                                e.printStackTrace();
                                                            }

                                                        } else {

                                                            Toast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }}
                                            }
                                        }

        );


        circularButton2.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View v) {

                                                   circularButton2.setProgress(50);

                                                   String ruser = Runame.getText().toString();
                                                   String rpass = Rpword.getText().toString();
                                                   String remail = Remail.getText().toString();
                                                   String rphone = Rphone.getText().toString();


                                                   final ConnectionDetector cd = new ConnectionDetector(Register.this);
                                                   if (cd.isConnectingToInternet()) {


                                                       JSONObject jsonobject;
                                                       final JSONParser jParser2 = new JSONParser();
                                                       List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                                                       params2.add(new BasicNameValuePair("email", remail));
                                                       params2.add(new BasicNameValuePair("password", rpass));
                                                       params2.add(new BasicNameValuePair("username", ruser));
                                                       params2.add(new BasicNameValuePair("phoneNo", rphone));


                                                       jsonobject = jParser2.makeHttpRequest(Regsiterurl, "POST", params2);

                                                       try {
                                                           if (jsonobject != null) {

                                                               String result = jsonobject.getString("success");

                                                               if (result.equals("true")) {

                                                                   Toast.makeText(Register.this, "Registeration Successful", Toast.LENGTH_LONG).show();


                                                                   circularButton2.setProgress(100);

                                                                   Intent i = new Intent(Register.this, Login.class);
                                                                   startActivity(i);

                                                               } else {
                                                                   Toast.makeText(Register.this, "Registeration failed", Toast.LENGTH_LONG).show();
                                                                   circularButton2.setProgress(-1);

                                                               }

                                                           } else {
                                                               Toast.makeText(Register.this, "No Response From Server", Toast.LENGTH_LONG).show();
                                                               circularButton2.setProgress(-1);

                                                           }


                                                       } catch (Exception e) {
                                                           Log.e("Error", e.getMessage());
                                                           e.printStackTrace();
                                                       }


                                                   } else {
                                                       circularButton2.setProgress(-1);
                                                       Toast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                                   }


                                               }
                                           }

        );
        }


    }
