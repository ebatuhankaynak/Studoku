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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ebk.studoku.R;
import ebk.studoku.model.Srs;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by E.Batuhan Kaynak on 12.2.2017.
 */

public class SrsListAdapter extends BaseAdapter{

    private Context context;
    private RealmResults<Srs> srsList;

    public SrsListAdapter(Context context, int srs){
        this.context = context;

        Realm realm = Realm.getDefaultInstance();
        if (srs == 0){
            srsList = realm.where(Srs.class).equalTo("srs", 0).findAll();
        }else{
            srsList = realm.where(Srs.class).equalTo("srs", 0).not().findAll();
        }
    }

    @Override
    public int getCount() {
        return srsList.size();
    }

    @Override
    public Object getItem(int i) {
        return srsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View listItem, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        listItem = layoutInflater.inflate(R.layout.srs_listitem2, null);

        TextView listLevelTextView = (TextView) listItem.findViewById(R.id.listLevelTextView);
        TextView listNameTextView = (TextView) listItem.findViewById(R.id.listNameTextView);
        TextView listDateTextView = (TextView) listItem.findViewById(R.id.listDateTextView);
        TextView listSrsTextView = (TextView) listItem.findViewById(R.id.listSrsTextView);

        listNameTextView.setTypeface(listNameTextView.getTypeface(), Typeface.BOLD);
        //listDateTextView.setTypeface(listDateTextView.getTypeface(), Typeface.BOLD);
        listSrsTextView.setTypeface(listSrsTextView.getTypeface(), Typeface.BOLD);

        String name = srsList.get(position).getName();
        String note = srsList.get(position).getNote();
        String srs = String.valueOf(srsList.get(position).getSrs());
        int level = srsList.get(position).getLevel();

        if(srs.equals("0")){
            srs = "Today";
        }else{
            srs = srs + " Days";
        }

        //listLevelTextView.setBackground(getLevelDrawable(level, context));
        listNameTextView.setText(name);
        listDateTextView.setText(note);
        listSrsTextView.setText(srs);

        Drawable background = ContextCompat.getDrawable(context, R.drawable.custom_srsitem);
        listItem.setBackground(background);

        return listItem;
    }

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
