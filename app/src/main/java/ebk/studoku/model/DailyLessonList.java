package ebk.studoku.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by E.Batuhan Kaynak on 17.2.2017.
 */

public class DailyLessonList {
    private ArrayList<DailyLesson> lecturesList;
    private String todayStr;
    private SQLiteDatabase db;

    public DailyLessonList(){

    }

    public ArrayList<DailyLesson> getLecturesList(String todayStr){
        if (todayStr.equals("FIRST_TIME_INIT")){
            this.todayStr = new SimpleDateFormat("EEE").format(new Date()).toUpperCase();
        }
        if (this.todayStr.equals(todayStr)){
            return lecturesList;
        }else{
            fillList();
            return lecturesList;
        }
    }

    public void init() {
        this.todayStr = new SimpleDateFormat("EEE").format(new Date()).toUpperCase();
        fillList();
    }

    private void fillList(){
        Cursor cursor = db.query("SCHEDULE", new String[] {todayStr, "_id"}, null, null, null, null, null);

        lecturesList = new ArrayList<>();
        if (cursor.moveToFirst()){
            for (int i = 0; i < cursor.getCount(); i++){
                String value = cursor.getString(0);
                if (value != null){
                    if (!lecturesList.contains(value)){
                        lecturesList.add(new DailyLesson(value));
                    }
                }
            }
        }
    }

    public boolean isEmpty(){
        return lecturesList.isEmpty();
    }
}
