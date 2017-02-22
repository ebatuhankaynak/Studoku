package ebk.studoku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ebk.studoku.adapters.MenuAdapter;
import ebk.studoku.adapters.PopAdapter;
import ebk.studoku.model.CardViewTypes;
import ebk.studoku.model.DailyLesson;
import ebk.studoku.model.Lecture;
import ebk.studoku.transition.Transition;

public class MenuFragment extends Fragment {
    private MenuAdapter menuAdapter;
    private PopAdapter popAdapter;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.recycler_main, container, false);

        ArrayList<Lecture> lecturesList = new DailyLesson().getLecturesList();

        if(lecturesList.size() != 0){
            //SrsPop Recycler
            final RecyclerView popRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_srsPop);
            StaggeredGridLayoutManager popStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
            popRecyclerView.setLayoutManager(popStaggeredGridLayoutManager);

            popAdapter = new PopAdapter();
            popRecyclerView.setAdapter(popAdapter);

            popAdapter.setListener(new PopAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getContext(), AddSrsActivity.class);
                    startActivity(intent);
                }
            });

            popRecyclerView.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    popRecyclerView.setAlpha(0.1f);
                    ViewCompat.animate(popRecyclerView).alpha(1f).setDuration(1000).setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            view.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationEnd(View view) {
                        }
                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    });
                }
            }, 1000);
        }

        //Menu Recycler
        RecyclerView menuRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_menu);
        StaggeredGridLayoutManager menuStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        menuRecyclerView.setLayoutManager(menuStaggeredGridLayoutManager);

        menuAdapter = new MenuAdapter();
        menuRecyclerView.setAdapter(menuAdapter);

        menuAdapter.setListener(new MenuAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Transition.getInstance().switchFragment(getFragmentManager(), CardViewTypes.MENU, position);
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private String getDayName(){
        return new SimpleDateFormat("EEE").format(new Date());
    }
}
