package kronos.cbs.basu.cbsguide;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class KronosFragment extends Fragment {
    private TextView aboutTextView,titleAppBar,contactTextView;
    private EditText editTextSubject;
    private EditText editTextMessage;
    String name,subject,message,email,about,pname,vpname,pnumber,vpnumber;
    Intent intent,emailIntent;
    int id;
    String DataUrl="http://sscbskronos.esy.es/SocJson.php?id=";
    String webAddressUrl,photo;
    //Send button
    private Button buttonSend;
    private ImageView societyPhoto;
    private View view;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private ImageButton buttonLogout,buttonBrowser,buttonQuery;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();

        id=getArguments().getInt("Id");
        view = inflater.inflate(R.layout.fragment_kronos, container, false);
        context = view.getContext();

        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) view.findViewById(R.id.editTextMessage);
        titleAppBar=(TextView) view.findViewById(R.id.title_app_bar);
        societyPhoto=(ImageView) view.findViewById(R.id.societyPhoto);

        //content
        aboutTextView = (TextView) view.findViewById(R.id.about);
        contactTextView = (TextView) view.findViewById(R.id.contact);
        buttonLogout =(ImageButton) view.findViewById(R.id.logout);
        buttonQuery=(ImageButton) view.findViewById(R.id.query);

            buttonQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getActivity(), MailActivity.class);
//                Toast.makeText(getActivity(),"Work",Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("title", name);
                    intent.putExtras(bundle);
                    KronosFragment.this.startActivity(intent);

                }
            });

        // /Adding click listener
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertMessage();
            }
        });

//        buttonSend = (Button) view.findViewById(R.id.buttonSend);
        buttonBrowser = (ImageButton) view.findViewById(R.id.website);
        String url=DataUrl+id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject collegeData = result.getJSONObject(0);
                    name = collegeData.getString("name");
                    email = collegeData.getString("email");
                    about = collegeData.getString("about");
                    pname = collegeData.getString("pname");
                    pnumber = collegeData.getString("pnumber");
                    vpname = collegeData.getString("vpname");
                    vpnumber = collegeData.getString("vpnumber");
                    webAddressUrl = collegeData.getString("webaddress");
                    photo= collegeData.getString("photo");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
               titleAppBar.setText(name);
                Picasso.with(context)
                        .load(photo).fit().error(R.drawable.error).placeholder(R.drawable.app_logo)
                        .into(societyPhoto);
//                aboutTextView.setText(about+"\n");
                aboutTextView.setText(Html.fromHtml(about));
                contactTextView.setText(pname+"\t"+pnumber+"\n"+vpname+"\t"+vpnumber);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        if(webAddressUrl=="null")
            buttonBrowser.setVisibility(View.GONE);
        else {
            buttonBrowser.setVisibility(View.VISIBLE);
            buttonBrowser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(context, InAppBrowser.class);
                    intent.putExtra("site_url",webAddressUrl);
                    context.startActivity(intent);
                }
            });
        }
//        buttonSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                subject = editTextSubject.getText().toString().trim();
//                message = editTextMessage.getText().toString().trim();
//
//                emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setType("message/rfc822");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kronos.cbs@gmail.com"});
////        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                    Log.i("Finished sending email", "");
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });




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
