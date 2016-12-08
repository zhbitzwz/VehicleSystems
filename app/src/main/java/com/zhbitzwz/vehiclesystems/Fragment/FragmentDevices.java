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
 *  设备管理模块
 * Author: ZWZ
 */
public class FragmentDevices extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_devices,container,false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Devices");

        Button buttonChangeText = (Button) view.findViewById(R.id.buttonFragmentdevices);

        final TextView textViewDevicesFragment = (TextView) view.findViewById(R.id.textViewDevicesFragment);
        buttonChangeText.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        textViewDevicesFragment.setText("This is the Devices Fragment");
                        textViewDevicesFragment.setTextColor(getResources().getColor(R.color.md_yellow_800));
                    }
                }
        );
        return view;
    }

}
