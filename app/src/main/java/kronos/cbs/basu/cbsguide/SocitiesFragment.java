package kronos.cbs.basu.cbsguide;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class SocitiesFragment extends Fragment {
    private ImageView imageView;
    private View view;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private ImageButton buttonLogout;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();

        view = inflater.inflate(R.layout.fragment_soc, container, false);
        context = view.getContext();
//        imageView=(ImageView) view.findViewById(R.id.imageView);
//        Picasso.with(context)
//                .load(R.drawable.app_logo).resize(400,400).centerInside()
//                .into(imageView);
        buttonLogout =(ImageButton) view.findViewById(R.id.logout);
        //Adding click listener
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertMessage();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
//        layoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }

        adapter = new CustomAdapter(context,data);
        recyclerView.setAdapter(adapter);


        return view;
    }
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
