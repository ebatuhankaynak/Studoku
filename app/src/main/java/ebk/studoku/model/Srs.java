package ebk.studoku.model;

import io.realm.RealmObject;

/**
 * Created by E.Batuhan Kaynak on 20.2.2017.
 */

public class Srs extends RealmObject {

    private String name;
    private String date;
    private int level;
    private int srs;
    private String note;
    private int grace;

    public Srs(){

    }

    public Srs(String name, String date, int level, int srs, String note, int grace){
        this.name = name;
        this.date = date;
        this.level = level;
        this.srs = srs;
        this.note = note;
        this.grace = grace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSrs() {
        return srs;
    }

    public void setSrs(int srs) {
        this.srs = srs;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getGrace() {
        return grace;
    }

    public void setGrace(int grace) {
        this.grace = grace;
    }
}
