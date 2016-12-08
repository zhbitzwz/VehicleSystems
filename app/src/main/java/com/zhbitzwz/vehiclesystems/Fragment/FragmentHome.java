package com.zhbitzwz.vehiclesystems.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.zhbitzwz.vehiclesystems.Aty.MainActivity;
import com.zhbitzwz.vehiclesystems.Aty.WeatherSearchActivity;
import com.zhbitzwz.vehiclesystems.Aty.WilddogVideoActivity;
import com.zhbitzwz.vehiclesystems.MyView.FilterMenu;
import com.zhbitzwz.vehiclesystems.MyView.FilterMenuLayout;
import com.zhbitzwz.vehiclesystems.R;

/**
 * Home模块
 * Author: ZWZ
 */
public class FragmentHome extends Fragment implements View.OnClickListener,AMap.OnMapClickListener{

    private MapView mapView;
    private AMap aMap;

    private ImageButton btn_layer;
    private Button basicmap;
    private Button rsmap;
    private Button nightmap;
    private Boolean firsttouch = true;

    private EditText etSearchCity;
    private Button btnSearchCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home,container,false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");

        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //TODO 还没有使用下载的离线地图，现使用默认位置存储，屏蔽了自定义设置
        //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        initView(view);

        return view;
    }

    /**
     * 初始化AMap对象
     */
    private void initView(View view) {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setOnMapClickListener(this);
        btn_layer = (ImageButton) view.findViewById(R.id.btn_layer);
        btn_layer.setOnClickListener(this);

        //响应PopWindow对象
        btn_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //响应地图类型模式
                initPopWindow(view);
            }
        });

        //响应FilterMenu对象
        FilterMenuLayout filterMenuLayout = (FilterMenuLayout) view.findViewById(R.id.filter_menu);
        attachMenu(filterMenuLayout);
        //额外控件
        btnSearchCity = (Button) view.findViewById(R.id.bt_city_search);
        etSearchCity = (EditText) view.findViewById(R.id.et_city_search);

        btnSearchCity.setOnClickListener(this);
        etSearchCity.setOnClickListener(this);
//        Button buttonChangeText = (Button) view.findViewById(R.id.buttonFragmentHome);
//        final TextView textViewHomeFragment = (TextView) view.findViewById(R.id.textViewHomeFragment);
//        buttonChangeText.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        textViewHomeFragment.setText("This is the Home Top Fragment");
//                        textViewHomeFragment.setTextColor(getResources().getColor(R.color.md_yellow_800));
//                    }
//                }
//        );
    }

    private FilterMenu attachMenu(FilterMenuLayout filterMenuLayout) {
        return new FilterMenu.Builder(getContext())
                .addItem(R.drawable.ic_action_add)
                .addItem(R.drawable.ic_action_clock)
                .addItem(R.drawable.ic_action_info)
                .addItem(R.drawable.ic_action_io)
                .addItem(R.drawable.ic_action_location_2)
                .attach(filterMenuLayout)
                .withListener(listener)
                .build();
    }
/*
 * //TODO 这里实现FilterMenu 页面跳转
 */
    FilterMenu.OnMenuChangeListener listener = new FilterMenu.OnMenuChangeListener() {

        @Override
        public void onMenuItemClick(View view, int position) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Intent intent = new Intent(getActivity(), WilddogVideoActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    Toast.makeText(getActivity(), "FilterMenu Error:Touched position " + position, Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(getActivity(), "Touched position " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMenuCollapse() {

        }

        @Override
        public void onMenuExpand() {

        }
    };
    private void initPopWindow(View view) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.map_layer_popup , null ,false);

        basicmap = (Button) view.findViewById(R.id.basicmap);
        rsmap = (Button) view.findViewById(R.id.rsmap);
        nightmap = (Button) view.findViewById(R.id.nightmap);

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setAnimationStyle(R.anim.map_layer_show);  //设置加载动画
        //解决PopupWindow后退键不关闭问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // 不能设置true，因为touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(view, 50, 50);

        basicmap.setOnClickListener(this);
        rsmap.setOnClickListener(this);
        nightmap.setOnClickListener(this);
    }

    /**
     * 方法onResum必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    /**
     * 方法onResum必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法onResum必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    /**
     * 方法onResum必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //Home界面所有组件的点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basicmap:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
                break;
            case R.id.rsmap:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                break;
            case R.id.nightmap:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
                break;
            case R.id.bt_city_search:
                String city = etSearchCity.getText().toString();
                Intent intent = new Intent();
                intent.setClass(getActivity(), WeatherSearchActivity.class);
                intent.putExtra("city",city);
                startActivity(intent);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.92463,116.389139),18), 1000, null);
//		aMap.moveCamera(CameraUpdateFactory.changeTilt(60));
        if (firsttouch) {
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(39.92463, 116.389139), 18, 0, 0
            )), 1500, new AMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    aMap.moveCamera(CameraUpdateFactory.changeTilt(60));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    aMap.animateCamera(CameraUpdateFactory.changeBearing(90), 2000, null);
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }
}
