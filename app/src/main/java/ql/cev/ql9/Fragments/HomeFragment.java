package ql.cev.ql9.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import ql.cev.ql9.CevCornerActivity;
import ql.cev.ql9.Common;
import ql.cev.ql9.CompetitionActivity;
import ql.cev.ql9.CostumGridAdapter;
import ql.cev.ql9.ProshowActivity;
import ql.cev.ql9.R;
import ql.cev.ql9.ResultActivity;
import ql.cev.ql9.ScheduleActivity;
import ql.cev.ql9.WorkshopActivity;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class HomeFragment extends Fragment {
    FlipperLayout flpr;
    private TextView counDown;
    private String[] web = {
            "COMPETITIONS",
            "WORKSHOPS",
            "PROSHOWS",
            "SCHEDULE",
            "CEV CORNER",
            "RESULTS"
    } ;
    private int[] imageId = {
            R.drawable.competetion,
            R.drawable.workshops,
            R.drawable.proshows,
            R.drawable.schedule,
            R.drawable.cev_corner,
            R.drawable.results
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);
        GridView grid = view.findViewById(R.id.grid);
        flpr= view.findViewById(R.id.flp);
        if(!Common.tech){
            web=new String[]{
                    "COMPETITIONS",
                    "ARTS RESULT",
                    "PROSHOWS",
                    "SCHEDULE",
                    "CEV CORNER",
                    "RESULTS"
            };
            imageId=new int[]{
                    R.drawable.competetion,
                    R.drawable.artscompetition,
                    R.drawable.proshows,
                    R.drawable.schedule,
                    R.drawable.cev_corner,
                    R.drawable.results
            };
        }
        setLayout();
        counDown= view.findViewById(R.id.counterv);
        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();
        //Shyam : Note that in calender fn month starts with 0
        if(Common.tech)
            end_calendar.set(2020, 2, 22);
        else
            end_calendar.set(2019, 2, 21);
        long start_millis = start_calendar.getTimeInMillis();
        long end_millis = end_calendar.getTimeInMillis();
        long total_millis = (end_millis - start_millis);
        CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                counDown.setText(days + " Days : "+ hours + " Hours : " + minutes + " Minutes : " + seconds+" Seconds");
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() { counDown.setText("Break Free....CEV!");
            }
        };
        cdt.start();
       grid.setAdapter(new CostumGridAdapter(getActivity(),web,imageId));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position==0){
                    Intent intent=new Intent(getActivity(), CompetitionActivity.class);
                    startActivity(intent);
                }
                if (position==1){
                    Intent intent=new Intent(getActivity(), WorkshopActivity.class);
                    startActivity(intent);
                }
                if (position==2){
                    Intent intent=new Intent(getActivity(), ProshowActivity.class);
                    startActivity(intent);
                }
                if (position==3){
                    Intent intent=new Intent(getActivity(), ScheduleActivity.class);
                    startActivity(intent);
                }
                if(position==4){
                    Intent intent=new Intent(getActivity(), CevCornerActivity.class);
                    startActivity(intent);
                }
                if (position==5){
                    Intent intent=new Intent(getActivity(), ResultActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
    private void setLayout() {
        String[] url =new String[]{
                "https://drive.google.com/uc?id=1ukJWen2V4Ort0lZhjqXPPeokUPpC7dCG",
                "https://drive.google.com/uc?id=1qJl6LplIufvO1D5eeDonCQwUdI97TH_V",
                "https://drive.google.com/uc?id=1gPHKV4evVTaJHpa9VhK-1DEbOlY2KfXd",
                "https://drive.google.com/uc?id=11X-5M6CQZx_SIAhqoS-ty8oPjp_wOo34",
                "https://drive.google.com/uc?id=11kCph49vIB3N1gLCi40Hq_kXxJqoKbEd"
        };
        flpr.setScrollTimeInSec(2);
        for (String s : url) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                FlipperView view = new FlipperView(getContext());
                view.setImageUrl(s).setDescription(" ");
                flpr.addFlipperView(view);
            }
        }
    }
}