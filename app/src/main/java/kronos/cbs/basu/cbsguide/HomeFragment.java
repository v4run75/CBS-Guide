package kronos.cbs.basu.cbsguide;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class HomeFragment extends Fragment {
    private ImageView imageView;
    private View view;
    private Context context;
    private ImageButton buttonLogout;
    private FirebaseAuth firebaseAuth;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private TextView home_content;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();


        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        imageView=(ImageView) view.findViewById(R.id.imageView);
        buttonLogout =(ImageButton) view.findViewById(R.id.logout);
        home_content= (TextView) view.findViewById(R.id.home_content);
        home_content.setText(Html.fromHtml("<h2><div style=\"text-align: center\">The Debating Society</h2></div><div style=\"text-align: center\">The CBS 'DebSoc' is a place where people with varying ideologies and beliefs find a common platform to voice what they believe in and engage in discussions relevant to the chaotic world we find ourselves in today. An achievement driven society and a late entrant to the DU debating scene, we have quickly made our presence known and have risen as one of the most competitive societies in the University. Our success hasn't strayed us from our core beliefs and we continue to value each other as do members of a family. Currently 20+ in number, we are on the lookout for people willing to share our platform, our success and our memories! </div><b><h4> Teacher Incharge </h4></b>1. Mr. Tushar Marwaha <br> 2. Dr. Rishi Rajan Sahay <b><h4> Annual Event </h4></b>The CBS Parliamentary Debate is a debating tournament that sees some of the most competitive elements of the debating circuit in DU. Held in the last week of August, this event is the curtain raiser for the rest of the debating sea on, hosting over 250 participants. A relatively new event, the CBS PD, in it's short life of 2 editions, has set standards for punctuality, hospitality and quality of competition. With our vision of expanding each year, each successive edition has seen a higher participation from colleges around the country. <b><h4> Achievements </h4></b>1. Winners - The DCAC Debate, IPCW fresher's <br>2. Finalists - Polemic, Ramjas <br>3. Semi-Finalists - NLIU Bhopal <br>4. Quarter-Finalists - IITD, Miranda House <br>5. Multiple individual accolades for debating and adjudicating over 60 team and individual prizes in 2016-17 <br>"));
//        home_content.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>",Html.FROM_HTML_MODE_COMPACT));
        //Adding click listener
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertMessage();
            }
        });


//        Picasso.with(context)
//                .load(R.drawable.app_logo).resize(400,400).centerInside()
//                .into(imageView);

        viewPager = (ViewPager) view.findViewById(R.id.slideshow);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    return view;
    }



//
//    public class MyTimerTask extends TimerTask {
//
//        @Override
//        public void run() {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    if(viewPager.getCurrentItem() == 0){
//                        viewPager.setCurrentItem(1);
//                    } else if(viewPager.getCurrentItem() == 1){
//                        viewPager.setCurrentItem(2);
//                    } else if(viewPager.getCurrentItem() == 2) {
//                        viewPager.setCurrentItem(3);
//                    }
//                    else {
//                        viewPager.setCurrentItem(0);
//                    }
//
//                }
//            });
//
//        }
//    }

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        { public void onClick(DialogInterface dialog, int which)
        { switch (which)
        { case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
            firebaseAuth.signOut();
            getActivity().finish();
            //closing activity
            //starting login activity
            startActivity(new Intent(context, LoginActivity.class));
            break;
            case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                break;
        } } };
        AlertDialog.Builder builder = new AlertDialog.Builder(context); builder.setMessage("Logout?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show(); }

}
