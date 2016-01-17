package android.buktab.in.buktab;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    public customlist3(Context context, ArrayList<String> books, ArrayList<String> sems, ArrayList<String> authors, ArrayList<String> prices,ArrayList<String> dept) {



        this.books=books;
        this.context = context;
        this.authors = authors;
        this.prices = prices;
        this.sems=sems;
        this.dept=dept;
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

        b.setText(books.get(position));
        a.setText(sems.get(position));
        p.setText(prices.get(position));
        dep.setText(dept.get(position));
        au.setText(authors.get(position));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "delete " + position, Toast.LENGTH_LONG).show();
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



}
