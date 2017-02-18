package ebk.studoku;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ebk.studoku.database.StudokuDatabaseHelper;
import ebk.studoku.model.DailyLesson;

public class AddSrsActivity extends AppCompatActivity implements AddSrsFragment.OnDbUpdate{
    private SQLiteDatabase db;
    private Cursor cursor;

    private int curAddNum;
    private int maxAddNum;
    private String addNumStr;

    private String lecNameStr;
    private String notes;

    private ArrayList<DailyLesson> lecturesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_srs);

        SQLiteOpenHelper studokuDatabaseHelper = new StudokuDatabaseHelper(this);
        db = studokuDatabaseHelper.getWritableDatabase();

        // TODO: 18.2.2017 SAT AND SUN???
        String todayStr = new SimpleDateFormat("EEE").format(new Date()).toUpperCase();
        Cursor cursor = db.query("SCHEDULE", new String[] {"FRI", "_id"}, null, null, null, null, null);

        // TODO: 17.2.2017 GET YOUR SHIT TOGETHER AND SOLVE DAILY LIST 
        lecturesList = new ArrayList<>();
        if (cursor.moveToFirst()){
            for (int i = 0; i < cursor.getCount(); i++){
                String value = cursor.getString(0);
                if (value != null){
                    if (!lecturesList.contains(value)){ //PUG
                        lecturesList.add(new DailyLesson(value));
                    }
                }
                cursor.moveToNext();
            }
        }

        curAddNum = 1;
        maxAddNum = lecturesList.size();
        addNumStr = curAddNum + " out of " + maxAddNum + " lessons";

        lecNameStr = lecturesList.get(0).getName();

        if (maxAddNum >= 1){
            nextFragment();
        }
    }

    private void nextFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.addSrs_content_frame, createFragment());
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private Fragment createFragment(){
        Fragment fragment = new AddSrsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("addNumStr", addNumStr);
        bundle.putString("lecNameStr", lecNameStr);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onNextButtonClick(View view) {
        buttonLogic();
        insertLecture();
        lecturesList.get(curAddNum - 1).setProcessed(true);
    }

    public void onSkipButtonClick(View view) {
        buttonLogic();
    }

    private void buttonLogic(){
        if (curAddNum != maxAddNum){
            curAddNum++;
            updateAddNumStr();
            nextFragment();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void updateAddNumStr(){
        addNumStr = curAddNum + " out of " + maxAddNum + " lessons";
    }

    @Override
    public void OnDbUpdate(String notes) {
        this.notes = notes;
    }

    private void insertLecture(){
        ContentValues lectureValues = new ContentValues();
        lectureValues.put("NAME", lecNameStr);
        lectureValues.put("DATE", getDate());
        lectureValues.put("LEVEL", 0);
        lectureValues.put("SRS", 0);
        lectureValues.put("NOTE", notes);
        lectureValues.put("GRACE", 0);

        db.insert("SRS", null, lectureValues);
    }

    private String getDate() {
        return new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
    }

}
