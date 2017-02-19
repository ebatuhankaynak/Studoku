package ebk.studoku;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import ebk.studoku.database.DbConst;
import ebk.studoku.database.StudokuDatabaseHelper;
import ebk.studoku.model.Lecture;
import ebk.studoku.transition.Transition;
import io.realm.Realm;
import io.realm.RealmResults;

public class ManageLecturesFragment extends Fragment {

    public ManageLecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_lectures, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final EditText manageAddLectureEditText = (EditText) view.findViewById(R.id.manageAddLectureEditText);
        final Button manageAddActivitiesButton = (Button) view.findViewById(R.id.manageAddLectureButton);
        manageAddActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String lectureName = manageAddLectureEditText.getText().toString();
                if (!lectureName.equals("")){
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Lecture lecture = realm.createObject(Lecture.class);
                            lecture.setName(lectureName);
                        }
                    });
                    // TODO: 19.2.2017 UPDATE UI VIA LISTENER
                    manageAddLectureEditText.setText("");
                    Transition.getInstance().switchFragment(getFragmentManager(), new ManageLecturesFragment());
                }


            }
        });

        final ListView lecturesListView = (ListView) view.findViewById(R.id.lecturesListView);
        lecturesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, final long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Lecture");

                final EditText input = new EditText(getContext());
                Cursor c = (Cursor) lecturesListView.getItemAtPosition(i);
                input.setText(c.getString(0));
                final String id = c.getString(1);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("LECTURE", input.getText().toString());
                        //db.update("LECTURELIST", updatedValues, "_id = ?", new String[] {String.valueOf(l)});
                        Transition.getInstance().switchFragment(getFragmentManager(), new ManageLecturesFragment());
                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //db.delete(DbConst.TABLE_LECTURELIST, "_id=?", new String[]{String.valueOf(id)});
                        // TODO: 11.2.2017 ALSO DELETE FROM SCHEDULE (AND LIST???) 
                        Transition.getInstance().switchFragment(getFragmentManager(), new ManageLecturesFragment());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.show();
            }
        });
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Lecture> lectureList = realm.where(Lecture.class).findAll();
        ArrayList<String> lectureNames = new ArrayList<>();
        for (Lecture lecture : lectureList){
            lectureNames.add(lecture.getName());
        }
        lecturesListView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                lectureNames));

        //lecturesListView.setAdapter(lecturesListAdapter);
    }
}
