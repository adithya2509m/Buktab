package in.buktab.android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    String gender="M";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initToolbar();
        //title=(TextView)findViewById(R.id.text_view_toolbar_title);


        final EditText Runame=(EditText) findViewById(R.id.Runame);
        final EditText Rpword=(EditText) findViewById(R.id.Rpword);
        final EditText Remail=(EditText) findViewById(R.id.Remail);
        TextView terms=(TextView) findViewById(R.id.terms);
        final CheckBox shownum=(CheckBox)findViewById(R.id.shownum);


        final EditText Rphone=(EditText) findViewById(R.id.Rphone);
        final ImageView male,female;
        male=(ImageView)findViewById(R.id.maleimage);
        female=(ImageView)findViewById(R.id.femaleimage);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Register.this,Terms.class);
                startActivity(i);
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundColor(Color.rgb(255, 171, 64));
                female.setBackgroundColor(Color.rgb(255,255, 255));
                gender="M";
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundColor(Color.rgb(255, 171, 64));
                male.setBackgroundColor(Color.rgb(255,255, 255));
                gender="F";
            }
        });

       // Remail.setText(getEmail(Register.this));

      //  TelephonyManager tMgr = (TelephonyManager)Register.this.getSystemService(Context.TELEPHONY_SERVICE);
       // String mPhoneNumber = tMgr.getLine1Number();
        //Rphone.setText(mPhoneNumber);

        final CircularProgressButton circularButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);
        circularButton2.setIndeterminateProgressMode(true);


        Runame.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            @Override
                                            public void onFocusChange(View v, boolean hasFocus) {

                                                if (!hasFocus) {

                                                    if (Runame.getText().toString().equals("")) {
                                                        Runame.setError("Please enter username");

                                                    } else if (Runame.getText().length() < 4) {


                                                        Runame.setError("Username should have more than 4 characters");


                                                    } else {

                                                        final ConnectionDetector cd = new ConnectionDetector(Register.this);
                                                        if (cd.isConnectingToInternet()) {

                                                            JSONObject jsonobject3;
                                                            final JSONParser jParser3 = new JSONParser();
                                                            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

                                                            String url = "http://52.10.251.227:3000/validate/username/" + Runame.getText().toString().replaceAll("\\s", "");

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

                    if(Rpword.getText().toString().equals("")){

                        Rpword.setError("Please enter password");

                    }



                }}});


        Remail.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if (Remail.getText().toString().equals("")) {

                        Remail.setError("Please enter emailid");

                    } else {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Remail.getText()).matches()) {

                            Remail.setError("Invalid emailid");


                        }

                    }}
            }
        });

        Rphone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    circularButton2.performClick();
                    handled = true;
                }
                return handled;
            }
        });



        Rphone.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    if (Rphone.getText().toString().equals("")) {

                        Rphone.setError("Please enter the phone number");

                    } else {

                        String MobilePattern = "[0-9]{10}";
                        String inbuilt="^+91[0-9]{10}$";

                        if (!Rphone.getText().toString().matches(MobilePattern)&&!Rphone.getText().toString().matches(inbuilt)) {

                            Rphone.setError("Enter a valid phone number");

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
                                circularButton2.setProgress(-1);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 5s = 5000ms
                                        circularButton2.setProgress(0);
                                        circularButton2.setIndeterminateProgressMode(true);


                                    }
                                }, 3000);
                                }
                            }
                        }
                    }
                }
            }

            );


            circularButton2.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){

                circularButton2.setProgress(50);

                String ruser = Runame.getText().toString();
                String rpass = Rpword.getText().toString();
                String remail = Remail.getText().toString();
                String rphone = Rphone.getText().toString();
                    String hidden="";
                    boolean sendno=shownum.isChecked();
                    if(sendno)
                        hidden="true";
                        else
                    hidden="false";


                final ConnectionDetector cd = new ConnectionDetector(Register.this);
                if (cd.isConnectingToInternet()) {


                    JSONObject jsonobject;
                    final JSONParser jParser2 = new JSONParser();
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("email", remail));
                    params2.add(new BasicNameValuePair("password", rpass));
                    params2.add(new BasicNameValuePair("username", ruser));
                    params2.add(new BasicNameValuePair("phoneNo", rphone));
                    params2.add(new BasicNameValuePair("sex",gender));
                    params2.add(new BasicNameValuePair("hidden",hidden));


                    jsonobject = jParser2.makeHttpRequest(Regsiterurl, "POST", params2);

                    try {
                        if (jsonobject != null) {

                            String result = jsonobject.getString("success");

                            if (result.equals("true")) {

                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();


                                circularButton2.setProgress(100);

                                Intent i = new Intent(Register.this, Login.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_LONG).show();
                                circularButton2.setProgress(-1);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 5s = 5000ms
                                        circularButton2.setProgress(0);
                                        circularButton2.setIndeterminateProgressMode(true);


                                    }
                                }, 3000);

                            }

                        } else {
                            Toast.makeText(Register.this, "No Response From Server", Toast.LENGTH_LONG).show();
                            circularButton2.setProgress(-1);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    circularButton2.setProgress(0);
                                    circularButton2.setIndeterminateProgressMode(true);


                                }
                            }, 3000);

                        }


                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }


                } else {
                    circularButton2.setProgress(-1);
                    Toast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            circularButton2.setProgress(0);
                            circularButton2.setIndeterminateProgressMode(true);


                        }
                    }, 3000);
                }


            }
            }

            );
        }
/*
    static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null) {
            return null;
        } else {

            return account.name;

        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

*/
    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);




        //scaleImage(uicon, 125);

        setSupportActionBar(mToolbar);
        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolBarTextView.setText("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.cpb_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                Intent i=new Intent(Register.this,Login.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        // your code.
        Intent i= new Intent(Register.this,Login.class);
        this.finish();
        startActivity(i);
    }
    @Override
    protected void onPause() {
        super.onPause();

    }
}
