package nfl.sports.creedglobal.com.forwardpass;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Waiting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Waiting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Waiting extends android.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_waiting,container,false);
    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
