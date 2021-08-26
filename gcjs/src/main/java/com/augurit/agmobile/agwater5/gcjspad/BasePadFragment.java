package com.augurit.agmobile.agwater5.gcjspad;

import android.support.v4.app.Fragment;

import com.augurit.agmobile.agwater5.R;

import io.reactivex.disposables.CompositeDisposable;

public class BasePadFragment extends Fragment {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();


    protected void addFragment(BasePadFragment fragment,int layoutId){
        if (getActivity()!=null && getActivity().findViewById(layoutId)!=null) {
            getActivity().getSupportFragmentManager().beginTransaction().add(layoutId,fragment).commit();
        }
    }

    protected void addFragmentOnActivity(BasePadFragment fragment){
        addFragment(fragment, R.id.fl_content);
    }

    protected void removeFragment(BasePadFragment fragment){
        if (getActivity()!=null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
