package ebk.studoku.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by E.Batuhan Kaynak on 19.2.2017.
 */

public class Lecture extends RealmObject {

    private String name;
    public RealmList<Timeslot> timeslots;

    public Lecture(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
