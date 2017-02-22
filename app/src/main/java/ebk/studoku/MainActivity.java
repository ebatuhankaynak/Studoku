package ebk.studoku;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import ebk.studoku.database.AndroidDatabaseManager;
import ebk.studoku.database.StudokuQuery;
import ebk.studoku.model.Srs;
import ebk.studoku.model.Timeslot;
import ebk.studoku.transition.Transition;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();
        /*
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Srs srs = realm.createObject(Srs.class);
                srs.setName("testsrs");
                srs.setNote("selambennote");
                srs.setSrs(0);
            }
        });*/

        /*
        final RealmResults realmResults = realm.where(Srs.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.deleteAllFromRealm();
            }
        });
        */
        Transition.getInstance().switchFragment(getSupportFragmentManager(), new MenuFragment());
    }

    /*
    private float x1,x2;
    static final int MIN_DISTANCE = 50;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                    }else {
                        Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                        Intent dbmanager = new Intent(this ,AndroidDatabaseManager.class);
                        startActivity(dbmanager);
                    }
                }else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    */

}
