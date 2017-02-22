package ebk.studoku;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AddSrsFragment extends Fragment {

    private String addNumStr;
    private String lecNameStr;

    private EditText notesEditView;

    private AddSrsListener addSrsListener;

    public static interface AddSrsListener {
        public void updateNote(String note);
    }

    public void setListener(AddSrsListener addSrsListener){
        this.addSrsListener = addSrsListener;
    }

    public AddSrsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        addNumStr = bundle.getString("addNumStr");
        lecNameStr = bundle.getString("lecNameStr");

        return inflater.inflate(R.layout.fragment_add_srs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView numTextView = (TextView) view.findViewById(R.id.add_num_textView);
        TextView nameTextView = (TextView) view.findViewById(R.id.add_name_textView);

        //EditText dateEditView = (EditText) view.findViewById(R.id.add_date_editView);
        notesEditView = (EditText) view.findViewById(R.id.add_notes_editText);

        numTextView.setText(addNumStr);
        nameTextView.setText(lecNameStr);
    }

    @Override
    public void onPause(){
        super.onPause();
        addSrsListener.updateNote(notesEditView.getText().toString());
    }
}
