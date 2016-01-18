 package android.buktab.in.buktab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity {


    final String loginurl="http://52.10.251.227:3000/login";
    static String token="";
    static String username="";


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





        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
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
                    params1.add(new BasicNameValuePair("username",user));


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
                                editor.putString("isloggedin","true");
                                editor.commit();

                                circularButton1.setProgress(100);
                                Intent i=new Intent(Login.this,MainMenu.class);
                                startActivity(i);
                            }

                            else{
                                Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                                circularButton1.setProgress(-1);

                            }

                        }
                        else{
                            Toast.makeText(Login.this, "No Response From Server", Toast.LENGTH_LONG).show();
                            circularButton1.setProgress(-1);

                        }



                    }catch (Exception e){
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }






                }else{
                    circularButton1.setProgress(-1);
                    Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
}
