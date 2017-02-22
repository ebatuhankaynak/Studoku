package ebk.studoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ebk.studoku.model.DailyLesson;
import ebk.studoku.model.Lecture;
import ebk.studoku.model.Srs;
import io.realm.Realm;

public class AddSrsActivity extends AppCompatActivity{

    private int curAddNum;
    private int maxAddNum;
    private String addNumStr;

    private String lecNameStr;
    private String notes;

    private boolean nextPressed;
    private ArrayList<Lecture> lecturesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_srs);

        lecturesList = new DailyLesson().getLecturesList();

        curAddNum = 1;
        maxAddNum = lecturesList.size();
        addNumStr = curAddNum + " out of " + maxAddNum + " lessons";

        lecNameStr = lecturesList.get(0).getName();

        if (maxAddNum >= 1){
            nextFragment();
        }
        nextPressed = false;
    }

    private void nextFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.addSrs_content_frame, createFragment());
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private Fragment createFragment(){
        AddSrsFragment fragment = new AddSrsFragment();
        fragment.setListener(new AddSrsFragment.AddSrsListener() {
            @Override
            public void updateNote(String note) {
                notes = note;
                if (nextPressed){
                    insertSrs();
                    nextPressed = false;
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("addNumStr", addNumStr);
        bundle.putString("lecNameStr", lecNameStr);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onNextButtonClick(View view) {
        nextPressed = true;
        buttonLogic();
    }

    public void onSkipButtonClick(View view) {
        buttonLogic();
    }

    private void buttonLogic(){
        if (curAddNum != maxAddNum){
            lecNameStr = lecturesList.get(curAddNum).getName();
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

    private void insertSrs(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Srs srs = realm.createObject(Srs.class);
                srs.setName(lecNameStr);
                srs.setDate(getDate());
                srs.setLevel(0);
                srs.setSrs(0);
                srs.setNote(notes);
                srs.setGrace(0);
            }
        });
    }

    private String getDate() {
        return new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
    }
}
