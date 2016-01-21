package android.buktab.in.buktab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Arunkumar on 1/21/2016.
 */
public class Searchintent extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);



        String bname,aname,price;

        bname=getIntent().getExtras().getString("book");

        TextView bookname,authorname,bookprice,oname,ophone,oemail;

        ImageButton call=(ImageButton)findViewById(R.id.call);

        bookname=(TextView)findViewById(R.id.boname);
        authorname=(TextView)findViewById(R.id.auname);
        bookprice=(TextView)findViewById(R.id.pri);
        oname=(TextView)findViewById(R.id.name);
        ophone=(TextView)findViewById(R.id.phone);
        oemail=(TextView)findViewById(R.id.email);

        bookname.setText(getIntent().getExtras().getString("book").trim());
        authorname.setText(getIntent().getExtras().getString("author").trim());
        bookprice.setText(getIntent().getExtras().getString("price").trim());
        oname.setText(getIntent().getExtras().getString("name").trim());
        ophone.setText(getIntent().getExtras().getString("phone").trim());
        oemail.setText(getIntent().getExtras().getString("email").trim());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String numberToDial = "tel:" + getIntent().getExtras().getString("phone");
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

            }
        });


    }




}
