package ebk.studoku;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ebk.studoku.adapters.PopAdapter;
import ebk.studoku.adapters.SrsListAdapter;
import ebk.studoku.model.Srs;

public class SrsFragment extends Fragment {

    public SrsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_srs2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView todayList = (ListView) view.findViewById(R.id.srs_todayListView);
        final ListView upcomingList = (ListView) view.findViewById(R.id.srs_upcomingListView);

        todayList.setSelector(android.R.color.transparent);
        todayList.setAdapter(new SrsListAdapter(getContext(), 0));

        upcomingList.setSelector(android.R.color.transparent);
        upcomingList.setAdapter(new SrsListAdapter(getContext(), 1));
        /*
        todayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id = l;
                todayCursor = db.query("SRS", new String[] {"SRS", "LEVEL", "_id"}, "_id = ?", new String[] {Integer.toString((int) id)},
                        null, null, null);

                if (todayCursor.moveToFirst()){
                    if (todayCursor.getInt(0) == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Completed?");
                        final int dbId = (int) id;
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int newLevel = todayCursor.getInt(1) + 1;
                                int newSrs = getNewSrs(newLevel);

                                ContentValues updatedValues = new ContentValues();
                                updatedValues.put("LEVEL", newLevel);
                                updatedValues.put("SRS", newSrs);

                                db.update("SRS", updatedValues, "_id = ?", new String[] {Integer.toString((dbId))});

                                Transition.getInstance().switchFragment(getFragmentManager(), new SrsFragment());
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();
                    }
                }
            }
        });*/

        upcomingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final AlertDialog builder = new AlertDialog.Builder(getContext()).create();
                View viewOfList = LayoutInflater.from(getContext()).inflate(R.layout.srs_dialog,null);

                TextView nameTextView = (TextView) viewOfList.findViewById(R.id.srs_dialog_nameTextView);
                TextView dateTextView = (TextView) viewOfList.findViewById(R.id.srs_dialog_dateTextView);
                TextView notesTextView = (TextView) viewOfList.findViewById(R.id.srs_dialog_notesTextView);

                Srs srs = (Srs) upcomingList.getItemAtPosition(i);
                nameTextView.setText(srs.getName());
                dateTextView.setText(srs.getDate());
                notesTextView.setText(srs.getNote());

                builder.setView(viewOfList);
                builder.show();
            }
        });
    }

    private int getNewSrs(int newLevel) {
        int newSrs;
        switch (newLevel){
            case 1: newSrs = 2; break; //2
            case 2: newSrs = 7; break; //7
            case 3: newSrs = 14; break; // 14
            default: newSrs = 0;
        }
        return newSrs;
    }
}