package ebk.studoku.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import ebk.studoku.R;
import ebk.studoku.model.Lecture;
import ebk.studoku.model.Timeslot;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by E.Batuhan Kaynak on 19.2.2017.
 */

public class ScheduleAdapter extends BaseAdapter{

    private Context context;
    private String[] schedule;

    public ScheduleAdapter(Context context) {
        this.context = context;

        ArrayList<String> scheduleList = new ArrayList<String>();
        scheduleList.add("");
        scheduleList.add("MON");
        scheduleList.add("TUE");
        scheduleList.add("WED");
        scheduleList.add("THU");
        scheduleList.add("FRI");

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Timeslot> timeslots = realm.where(Timeslot.class).findAll();
        timeslots = timeslots.sort("slot");

        if (timeslots.size() != 0){
            int index = 0;
            for(int i = 1; i < 9; i++){
                scheduleList.add("" + i);
                for (int j = 1; j < 6; j++){
                    if (index != timeslots.size()){
                        int timeToFind = timeslots.get(index).getSlot();
                        if(((6 * i) + j) == timeToFind){
                            Lecture lecture = realm.where(Lecture.class).equalTo("timeslots.slot", ((6 * i) + j)).findFirst();
                            scheduleList.add(lecture.getName());
                            index++;
                        }else{
                            scheduleList.add("");
                        }
                    }else{
                        scheduleList.add("");
                    }
                }
            }
        }else{
            for(int i = 1; i < 9; i++) {
                scheduleList.add("" + i);
                for (int j = 0; j < 5; j++){
                    scheduleList.add("");

                }
            }
        }

        schedule = new String[scheduleList.size()];
        schedule = scheduleList.toArray(schedule);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        TextView text = new TextView(this.context);
        text.setText(schedule[position]);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.parseColor("#fbdcbb"));
        text.setLayoutParams(new GridView.LayoutParams(165, 220));
        text.setBackgroundResource(R.drawable.grid_items_border);

        return text;
    }

    @Override
    public int getCount() {
        return schedule.length;
    }

    @Override
    public Object getItem(int position) {
        return schedule[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}