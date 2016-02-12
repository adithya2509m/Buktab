package in.buktab.android;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.cpb_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // your code.
        NavUtils.navigateUpFromSameTask(this);
    }
}
