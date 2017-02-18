package ebk.studoku.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
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
public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {
    private Listener listener;
    private String[] popItems = {"Add your lectures to Srs!"};
    private int[] popDrawables = {R.drawable.sun};

    public PopAdapter(){

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
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pop, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;

        TextView cardTextView = (TextView) cardView.findViewById(R.id.card_textView);
        cardTextView.setText(popItems[position]);

        cardTextView.setCompoundDrawablesWithIntrinsicBounds(popDrawables[position], 0, 0, 0);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    ViewCompat.animate(cardView).translationY(cardView.getHeight()).alpha(0.0f).setDuration(250).setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {

                        }
                        @Override
                        public void onAnimationEnd(View view) {
                            listener.onClick(position);
                        }
                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return popItems.length;
    }
}

