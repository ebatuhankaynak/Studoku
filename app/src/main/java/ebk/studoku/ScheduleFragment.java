package ebk.studoku;


import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ebk.studoku.adapters.ScheduleAdapter;
import ebk.studoku.database.StudokuQuery;
import ebk.studoku.model.Lecture;
import ebk.studoku.model.Timeslot;
import ebk.studoku.transition.Transition;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor scheduleCursor;

    private SimpleCursorAdapter scheduleAdapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        scheduleCursor = StudokuQuery.getInstance(getContext()).getScheduleCursor();

        scheduleAdapter = new SimpleCursorAdapter(inflater.getContext(),
                android.R.layout.simple_list_item_1,
                scheduleCursor,
                new String[]{"LECTURE"},
                new int[]{android.R.id.text1},
                0);

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        final ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext());
        gridview.setAdapter(scheduleAdapter);

        // TODO: 8.2.2017 LET USER ENTER ALL HIS/HER ACTIVITIES, THEN PLACE THEM INTO THE SCHEDULE.
        // TODO: 8.2.2017 2)MAKE TIMESLOTS CUSTOMIZEABLE BY CLICKING?
        // TODO: 8.2.2017 3)ADD THE OPTION TO ADD ROWS?(ŞİT GİBİMSİ)

        Button manageLecturesButton = (Button) view.findViewById(R.id.manageLecturesButton);
        manageLecturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transition.getInstance().switchFragment(getFragmentManager(), new ManageLecturesFragment());
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
            if (position % 6 == 0){

            } else if(!(position % 6 == 0 || position < 6)){
                final AlertDialog builder = new AlertDialog.Builder(getContext()).create();
                builder.setTitle("LECTURES");
                View viewOfList = LayoutInflater.from(getContext()).inflate(R.layout.schedule_dialog,null);

                final ListView scheduleDialogListView = (ListView) viewOfList.findViewById(R.id.scheduleDialogListView);

                Realm realm = Realm.getDefaultInstance();
                RealmResults<Lecture> lectureList = realm.where(Lecture.class).findAll();

                scheduleDialogListView.setAdapter(new ArrayAdapter<Lecture>(getContext(), android.R.layout.simple_list_item_1,
                        lectureList));

                scheduleDialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final int day = position % 6;
                        final int slot = position / 6;

                        final Lecture lecture = (Lecture) scheduleDialogListView.getItemAtPosition(i);

                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                int grid = (slot * 6) + day;
                                RealmResults<Timeslot> timeslots = realm.where(Timeslot.class).equalTo("slot", grid).findAll();
                                if (timeslots.size() == 0){
                                    Timeslot timeslot = realm.createObject(Timeslot.class);
                                    timeslot.setSlot(grid);
                                    lecture.getTimeslots().add(timeslot);
                                }

                            }
                        });

                        /*
                        if (slot % 2 == 1){
                            db.update("SCHEDULE", updatedValues, "_id = ?", new String[] {String.valueOf(slot + 1)});
                        }else{
                            db.update("SCHEDULE", updatedValues, "_id = ?", new String[] {String.valueOf(slot - 1)});
                        }
                        */

                        // TODO: 17.2.2017 NO DOUBLE ADD? 
                        builder.dismiss();
                        Transition.getInstance().switchFragment(getFragmentManager(), new ScheduleFragment());
                    }
                });
                builder.setView(viewOfList);
                builder.show();
            }
            }
        });
    }

    private String getDay(int position) {
        String day;
        int dayInt = position % 6;
        switch (dayInt){
            case 1: day = "MON"; break;
            case 2: day = "TUE"; break;
            case 3: day = "WED"; break;
            case 4: day = "THU"; break;
            case 5: day = "FRI"; break;
            default: day = "MON";
        }
        return day;
    }
}
