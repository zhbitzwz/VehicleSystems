package com.zhbitzwz.vehiclesystems.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhbitzwz.vehiclesystems.Aty.MainActivity;
import com.zhbitzwz.vehiclesystems.R;
/**
 *  车管模块
 * Author: ZWZ
 */
public class FragmentCar extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_car,container,false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Car");

        Button buttonChangeText = (Button) view.findViewById(R.id.buttonFragmentCar);

        final TextView textViewCarFragment = (TextView) view.findViewById(R.id.textViewCarFragment);
        buttonChangeText.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        textViewCarFragment.setText("This is the Car Fragment");
                        textViewCarFragment.setTextColor(getResources().getColor(R.color.md_yellow_800));
                    }
                }
        );
        return view;
    }

}
