package com.example.ezequieldevalais.retrofitexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by EzequielDeValais on 4/27/15.
 */
public class CardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    public static CardFragment newInstance(int position){
        return new CardFragment();
    };
}