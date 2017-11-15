package kronos.cbs.basu.cbsguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Basu on 8/2/2017.
 */

public class SocitiesLauncher extends AppCompatActivity{
    private TextView text;
    private EditText editTextSubject;
    private EditText editTextMessage;
    String name,subject,message;
    int id;
    Intent intent,emailIntent;
    //Send button
//    private Button buttonSend;
    Fragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_launcher);
//        text = (TextView) findViewById(R.id.title);
//        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
//        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

//        buttonSend = (Button) findViewById(R.id.buttonSend);
//
//        //Adding click listener
//        buttonSend.setOnClickListener(this);
//        TextView textView=(TextView)findViewById(R.id.textView);
        intent=getIntent();
//        name=intent.getStringExtra("socName");
        id=intent.getIntExtra("Id",0);
//        text.setText("Your query for "+name);

        Bundle bundle=new Bundle();
                bundle.putInt("Id",id);
                fragment=new KronosFragment();
                fragment.setArguments(bundle);


//        switch(name) {
//            case "Kronos":
//                Bundle bundle=new Bundle();
//                bundle.putInt("Id",id);
//                fragment=new KronosFragment();
//                fragment.setArguments(bundle);
//                break;
//            case "Convergence":
//                subject = editTextSubject.getText().toString().trim();
//                message = editTextMessage.getText().toString().trim();
//
//                emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setType("message/rfc822");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"democonverge.cbs@gmail.com"});
////        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                    finish();
//                    Log.i("Finished sending email", "");
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(SocitiesLauncher.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentMail, fragment);
            ft.commit();
        }
    }

//    @Override
//    public void onClick(View view) {
////        String email = editTextEmail.getText().toString().trim();
//        switch(name) {
//            case "Kronos":
//                fragment=new KronosFragment();
//            break;
//            case "Convergence":
//                subject = editTextSubject.getText().toString().trim();
//                message = editTextMessage.getText().toString().trim();
//
//                emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setType("message/rfc822");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"democonverge.cbs@gmail.com"});
////        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                    finish();
//                    Log.i("Finished sending email", "");
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(SocitiesLauncher.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content, fragment);
//            ft.commit();
//        }
//    }

//        Intent intentmail = new Intent(Intent.ACTION_SEND);
//        intentmail.putExtra(Intent.EXTRA_EMAIL, email);
//        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
//        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
//        intentmail.putExtra(Intent.EXTRA_SUBJECT, subject);
//        intentmail.putExtra(Intent.EXTRA_TEXT, message);
//
//        //need this to prompts email client only
//        intentmail.setType("message/rfc822");
//
//        startActivity(Intent.createChooser(intentmail, "Choose an Email client :"));
    }

