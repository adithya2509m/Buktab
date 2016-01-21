package android.buktab.in.buktab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Spinner;
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
 * Created by Arunkumar on 12/9/2015.
 */
public class customlist3 extends BaseAdapter implements ListAdapter {

    private final Context context;
    private final ArrayList<String> books;
    private final ArrayList<String> sems;
    private final ArrayList<String> authors ;
    private final ArrayList<String> prices ;
    private final ArrayList<String> dept ;
    private final ArrayList<String> id;
    int pos,check;
    String deleteurl="http://52.10.251.227:3000/delete";

    public customlist3(Context context, ArrayList<String> books, ArrayList<String> sems, ArrayList<String> authors, ArrayList<String> prices,ArrayList<String> dept,ArrayList<String> id) {



        this.books=books;
        this.context = context;
        this.authors = authors;
        this.prices = prices;
        this.sems=sems;
        this.dept=dept;
        this.id=id;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlist3, null);
        }


        TextView b = (TextView) view.findViewById(R.id.bookname);
        TextView a = (TextView) view.findViewById(R.id.semester);
        TextView p = (TextView) view.findViewById(R.id.price);
        TextView au = (TextView) view.findViewById(R.id.author);
        TextView dep=(TextView)view.findViewById(R.id.dept);

        Button delete=(Button) view.findViewById(R.id.delete);
        Button manage=(Button)view.findViewById(R.id.update);

        b.setText(books.get(position).trim());
        a.setText(sems.get(position).trim());
        p.setText(prices.get(position).trim());
        dep.setText(dept.get(position).trim());
        au.setText(authors.get(position).trim());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "delete " + position, Toast.LENGTH_LONG).show();

                     pos=position;

                final ConnectionDetector cd = new ConnectionDetector(context);
                if (cd.isConnectingToInternet()) {
                    new jsondelete().execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
                }







               // notifyDataSetChanged();
            }
        });

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "manage " + position, Toast.LENGTH_LONG).show();
                //notifyDataSetChanged();
            }
        });


        return view;

    }

    private class jsondelete extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(context);
            nDialog.setTitle("Fetching data from server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
            Toast.makeText(context.getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            params2.add(new BasicNameValuePair("token", Login.token));
            params2.add(new BasicNameValuePair("id", id.get(pos)));

            jsonobject = jParser2.makeHttpRequest(deleteurl, "POST", params2);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {
                        books.remove(pos);
                        authors.remove(pos);
                        sems.remove(pos);
                        prices.remove(pos);
                        dept.remove(pos);
                        id.remove(pos);

                        return true;
                    }

                    else{
                        check=1;
                        return false;
                    }



                     }
                else{
                    check=0;
                    return false;}
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

                if(check==0){
                Toast.makeText(context, "No response from server", Toast.LENGTH_LONG).show();
                nDialog.dismiss();}
                else{
                    Toast.makeText(context, "Success failed", Toast.LENGTH_LONG).show();
                    nDialog.dismiss();

                }

            }else{
                notifyDataSetChanged();
                Toast.makeText(context, "Delete Successful", Toast.LENGTH_LONG).show();

                nDialog.dismiss();

                Splash.bookcount--;
                MainMenu.bcnt.setText("" + Splash.bookcount);
                MainMenu.navDrawerItems.get(1).setCount("" + Splash.bookcount);
            }
        }
            }











}



