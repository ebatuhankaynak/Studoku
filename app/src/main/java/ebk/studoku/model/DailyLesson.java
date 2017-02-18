package ebk.studoku.model;

/**
 * Created by E.Batuhan Kaynak on 17.2.2017.
 */

public class DailyLesson {
    private String name;
    private boolean isProcessed;

    public DailyLesson(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
