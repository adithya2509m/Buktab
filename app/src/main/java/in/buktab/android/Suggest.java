package in.buktab.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Arunkumar on 2/12/2016.
 */
public class Suggest extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest);

        EditText sugbanme,suganame,sugdept,sugpub;

        sugbanme=(EditText)findViewById(R.id.sugbname);
        suganame=(EditText)findViewById(R.id.suganame);
        sugdept=(EditText)findViewById(R.id.sugdept);
        sugpub=(EditText)findViewById(R.id.sugpublisher);

        Button suggestbook=(Button)findViewById(R.id.suggestbook);

        suggestbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });



    }


}
