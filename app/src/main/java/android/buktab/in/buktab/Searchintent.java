package android.buktab.in.buktab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Arunkumar on 1/21/2016.
 */
public class Searchintent extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);

        Toolbar mtoolbar =(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(mtoolbar);

        String bname,aname,price;

        bname=getIntent().getExtras().getString("book");

        final TextView bookname,authorname,bookprice,oname,ophone,oemail,pub,loc;

        ImageButton call=(ImageButton)findViewById(R.id.call);
        ImageButton mail=(ImageButton)findViewById(R.id.mail);
        bookname=(TextView)findViewById(R.id.boname);
        authorname=(TextView)findViewById(R.id.auname);
        bookprice=(TextView)findViewById(R.id.pri);
        oname=(TextView)findViewById(R.id.name);
        ophone=(TextView)findViewById(R.id.phone);
        oemail=(TextView)findViewById(R.id.email);
        pub=(TextView)findViewById(R.id.pname);
        loc=(TextView)findViewById(R.id.location);

        bookname.setText(getIntent().getExtras().getString("book").trim());
        authorname.setText(getIntent().getExtras().getString("author").trim());
        bookprice.setText("\u20B9"+getIntent().getExtras().getString("price").trim());
        oname.setText(getIntent().getExtras().getString("name").trim());
        ophone.setText(getIntent().getExtras().getString("phone").trim());
        oemail.setText(getIntent().getExtras().getString("email").trim());
        pub.setText(getIntent().getExtras().getString("pub").trim());
        loc.setText(getIntent().getExtras().getString("location").trim());


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String numberToDial = "tel:" + getIntent().getExtras().getString("phone");
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, oemail.getText());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Willing to purchase " + bookname.getText());
                intent.putExtra(Intent.EXTRA_TEXT, "Hi I am " + Login.username + " ,I would like Purchase your book '" + bookname.getText() + "' .");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        TextView title =(TextView)findViewById(R.id.text_view_toolbar_title);
        title.setText("Ad Details");


    }




}
