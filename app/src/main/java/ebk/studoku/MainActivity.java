package ebk.studoku;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import ebk.studoku.database.AndroidDatabaseManager;
import ebk.studoku.database.StudokuDatabaseHelper;
import ebk.studoku.database.StudokuQuery;
import ebk.studoku.transition.Transition;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private StudokuQuery studokuQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studokuQuery = StudokuQuery.getInstance(this);
        studokuQuery.init();

        Transition.getInstance().switchFragment(getSupportFragmentManager(), new MenuFragment());
    }

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

    public SQLiteDatabase getDb() {
        return db;
    }
}
