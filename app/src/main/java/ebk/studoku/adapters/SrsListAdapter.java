package ebk.studoku.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ebk.studoku.R;

/**
 * Created by E.Batuhan Kaynak on 12.2.2017.
 */

public class SrsListAdapter extends SimpleCursorAdapter{
    private LayoutInflater inflater;
    private int layout;

    public SrsListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView listLevelTextView = (TextView) view.findViewById(R.id.listLevelTextView);
        TextView listNameTextView = (TextView) view.findViewById(R.id.listNameTextView);
        TextView listDateTextView = (TextView) view.findViewById(R.id.listDateTextView);
        TextView listSrsTextView = (TextView) view.findViewById(R.id.listSrsTextView);

        listNameTextView.setTypeface(listNameTextView.getTypeface(), Typeface.BOLD);
        //listDateTextView.setTypeface(listDateTextView.getTypeface(), Typeface.BOLD);
        listSrsTextView.setTypeface(listSrsTextView.getTypeface(), Typeface.BOLD);

        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String date = cursor.getString(cursor.getColumnIndex("NOTE"));
        String srs = cursor.getString(cursor.getColumnIndex("SRS"));
        int level = cursor.getInt(cursor.getColumnIndex("LEVEL"));

        if(srs.equals("0")){
            srs = "Today";
        }else{
            srs = srs + " Days";
        }

//        listLevelTextView.setBackground(getLevelDrawable(level, context));
        listNameTextView.setText(name);
        listDateTextView.setText(date);
        listSrsTextView.setText(srs);

        Drawable background = ContextCompat.getDrawable(context, R.drawable.custom_srsitem);
        view.setBackground(background);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    // TODO: 17.2.2017 ONLY LEVEL 0 IS SHOWN
    private Drawable getLevelDrawable(int level, Context context) {
        Drawable levelBg;
        switch (level){
            case 1: levelBg = ContextCompat.getDrawable(context, R.drawable.srs_level1); break;
            case 2: levelBg = ContextCompat.getDrawable(context, R.drawable.srs_level2); break;
            case 3: levelBg = ContextCompat.getDrawable(context, R.drawable.srs_level3); break;
            default: levelBg = ContextCompat.getDrawable(context, R.drawable.srs_level0);
        }
        return levelBg;
    }
}
