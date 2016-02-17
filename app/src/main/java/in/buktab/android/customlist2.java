package in.buktab.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arunkumar on 12/9/2015.
 */
public class customlist2 extends BaseAdapter implements ListAdapter {

    private final Context context;
    private final ArrayList<String> books;
    private final ArrayList<String> sems;
    private final ArrayList<String> authors ;
    private final ArrayList<String> prices ;
    private final ArrayList<String> dept ;
    private final ArrayList<String> location ;

    public customlist2(Context context, ArrayList<String> books, ArrayList<String> sems, ArrayList<String> authors, ArrayList<String> prices,ArrayList<String> dept,ArrayList<String> location) {



        this.books=books;
        this.context = context;
        this.authors = authors;
        this.prices = prices;
        this.sems=sems;
        this.dept=dept;
        this.location=location;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View custom_list = convertView;

        if (custom_list == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            custom_list = inflater.inflate(R.layout.customlist2, null);
        }



        TextView b = (TextView) custom_list.findViewById(R.id.bookname);
        TextView a = (TextView) custom_list.findViewById(R.id.semester);
        TextView p = (TextView) custom_list.findViewById(R.id.price);
        TextView au = (TextView) custom_list.findViewById(R.id.author);
        TextView dep=(TextView)custom_list.findViewById(R.id.dept);
        TextView loc=(TextView)custom_list.findViewById(R.id.location);

        b.setText(books.get(position).trim());
        a.setText(Splash.stringsuper[Integer.parseInt(sems.get(position))]);
      //  p.setText("20"+"\u20B9");
      p.setText("\u20B9"+prices.get(position).trim());
        dep.setText(dept.get(position).trim());
        au.setText(" by "+authors.get(position).trim());
        loc.setText(location.get(position));



        return custom_list;

    }



}
