package ebk.studoku.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by E.Batuhan Kaynak on 21.2.2017.
 */

public class DailyLesson {

    private ArrayList<Lecture> lecturesList;

    public DailyLesson(){
        Realm realm = Realm.getDefaultInstance();
        lecturesList = new ArrayList<>();
        RealmResults<Lecture> lectures;
        for (int i = 1; i < 9; i++){
            lectures = realm.where(Lecture.class).equalTo("timeslots.slot", (getDateInt() - 1) + (i * 6)).findAll();
            for (int j = 0; j < lectures.size(); j++){
                if (!lecturesList.contains(lectures.get(j))){
                    lecturesList.add(lectures.get(j));
                }
            }
        }
        Log.i("FIRST C", String.valueOf(lecturesList.size()));
        RealmResults<Srs> srsList =realm.where(Srs.class).findAll();
        for (int i = 0; i < lecturesList.size(); i++){
            Lecture lecture = lecturesList.get(i);
            for (int j = 0; j < srsList.size(); j++){
                Srs srs = srsList.get(j);
                if (lecture.getName().equals(srs.getName())){
                    if (srs.getDate().equals(getDate())){
                        lecturesList.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        Log.i("2nd C", String.valueOf(lecturesList.size()));
    }

    public ArrayList<Lecture> getLecturesList(){
        return lecturesList;
    }

    private String getDate() {
        return new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
    }

    private int getDateInt(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
}
