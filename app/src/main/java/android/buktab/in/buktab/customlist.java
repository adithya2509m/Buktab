package android.buktab.in.buktab;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Arunkumar on 12/9/2015.
 */
public class customlist extends ArrayAdapter<String> {

    private final Context context;
    private final String[] books;
    private final String[] depts;
    private final String[] authors ;
    private final String[] prices ;


    public customlist(Context context, String books[], String depts[],String authors[],String prices[]) {

        super(context, R.layout.customlist,books );

        this.books=books;
        this.context = context;
        this.authors = authors;
        this.prices = prices;
        this.depts=depts;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View custom_list = inflater.inflate(R.layout.customlist, parent, false);


        TextView b = (TextView) custom_list.findViewById(R.id.bookname);
        TextView a = (TextView) custom_list.findViewById(R.id.author);
        TextView p = (TextView) custom_list.findViewById(R.id.dept);
        //TextView au = (TextView) custom_list.findViewById(R.id.author);


        b.setText(books[position].trim());
        a.setText(authors[position].trim());
        p.setText(depts[position].trim());
       // au.setText(sems[position]);



        return custom_list;

    }



}
