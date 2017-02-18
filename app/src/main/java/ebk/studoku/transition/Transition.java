package ebk.studoku.transition;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ebk.studoku.AddSrsActivity;
import ebk.studoku.model.CardViewTypes;
import ebk.studoku.R;

/**
 * Created by E.Batuhan Kaynak on 8.2.2017.
 */

public class Transition {

    private static Transition transition;

    private Transition(){

    }

    public static Transition getInstance(){
        if(transition == null){
            transition = new Transition();
        }
        return transition;
    }

    public void switchFragment(FragmentManager fragmentManager, CardViewTypes cardViewType, int position){
        Fragment fragment = FragmentFactory.getInstance().createFragment(cardViewType, position);
        commitTransaction(fragmentManager, fragment);
    }

    public void switchFragment(FragmentManager fragmentManager, Fragment fragment){
        commitTransaction(fragmentManager, fragment);
    }

    private void commitTransaction(FragmentManager fragmentManager, Fragment fragment){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
