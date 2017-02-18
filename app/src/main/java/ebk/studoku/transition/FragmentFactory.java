package ebk.studoku.transition;

import android.support.v4.app.Fragment;

import ebk.studoku.AddSrsFragment;
import ebk.studoku.ScheduleFragment;
import ebk.studoku.SrsFragment;
import ebk.studoku.model.CardViewTypes;
import ebk.studoku.MenuFragment;

/**
 * Created by E.Batuhan Kaynak on 12.2.2017.
 */

public class FragmentFactory {

    private static FragmentFactory fragmentFactory;

    private FragmentFactory(){

    }

    public static FragmentFactory getInstance(){
        if(fragmentFactory == null){
            fragmentFactory = new FragmentFactory();
        }
        return fragmentFactory;
    }

    public Fragment createFragment(CardViewTypes fragmentType, int position) {
        Fragment fragment = new MenuFragment();
        if(fragmentType == CardViewTypes.POP){
            switch (position){
            case 0: fragment = new AddSrsFragment(); break;
                default: fragment = new AddSrsFragment();
            }
        }else if(fragmentType == CardViewTypes.MENU){
            switch (position){
            case 0: fragment = new SrsFragment(); break;
            case 1: fragment = new ScheduleFragment(); break;/*
            case 2: fragment = new ProgressFragment(); break;
            case 3: fragment = new AssignmentsFragment(); break;
            case 4: fragment = new HelpFragment(); break;*/
                default: fragment = new MenuFragment();
            }
        }
        return fragment;
    }
}
