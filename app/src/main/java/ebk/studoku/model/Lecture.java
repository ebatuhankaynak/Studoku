package ebk.studoku.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by E.Batuhan Kaynak on 19.2.2017.
 */

public class Lecture extends RealmObject {

    private String name;
    private RealmList<Timeslot> timeslots;

    public Lecture(){

    }

    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(RealmList<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
}
