package ebk.studoku.model;

import io.realm.RealmObject;

/**
 * Created by E.Batuhan Kaynak on 19.2.2017.
 */

public class Timeslot extends RealmObject {

    private int slot;

    public Timeslot(){

    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
