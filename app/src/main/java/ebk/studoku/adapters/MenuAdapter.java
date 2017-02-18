package ebk.studoku.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ebk.studoku.R;

/**
 * Created by E.Batuhan Kaynak on 12.2.2017.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private Listener listener;
    private String[] menuItems = {"SRS", "Schedule", "Progress", "Assignments", "Help"};
    private int[] menuDrawables = {R.drawable.srs, R.drawable.schedule, R.drawable.progress,
            R.drawable.assignments, R.drawable.help};

    public MenuAdapter(){

    }

    public static interface Listener {
        public void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView cardTextView = (TextView) cardView.findViewById(R.id.card_textView);
        cardTextView.setText(menuItems[position]);

        cardTextView.setCompoundDrawablesWithIntrinsicBounds(0, menuDrawables[position], 0, 0);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.length;
    }
}

