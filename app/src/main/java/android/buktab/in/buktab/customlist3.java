package android.buktab.in.buktab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Arunkumar on 12/9/2015.
 */
public class customlist3 extends ArrayAdapter<String> {

    private final Context context;
    private final String[] books;
    private final String[] sems;
    private final String[] authors ;
    private final String[] prices ;
    private final String[] dept ;

    public customlist3(Context context, String books[], String sems[], String authors[], String prices[],String dept[]) {

        super(context, R.layout.customlist3,books );

        this.books=books;
        this.context = context;
        this.authors = authors;
        this.prices = prices;
        this.sems=sems;
        this.dept=dept;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View custom_list = inflater.inflate(R.layout.customlist3, parent, false);


        TextView b = (TextView) custom_list.findViewById(R.id.bookname);
        TextView a = (TextView) custom_list.findViewById(R.id.semester);
        TextView p = (TextView) custom_list.findViewById(R.id.price);
        TextView au = (TextView) custom_list.findViewById(R.id.author);
        TextView dep=(TextView)custom_list.findViewById(R.id.dept);

        b.setText(books[position]);
        a.setText(sems[position]);
        p.setText(prices[position]);
        dep.setText(dept[position]);
        au.setText(authors[position]);



        return custom_list;

    }



}
