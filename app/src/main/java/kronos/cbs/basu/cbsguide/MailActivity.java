package kronos.cbs.basu.cbsguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MailActivity extends AppCompatActivity{
    ImageButton buttonLogout;
    Button buttonSend;
    EditText editTextSubject,editTextMessage;
    TextView titleAppBar;
    String subject,message,email,title;
    Intent emailIntent,intent;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mail);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogout =(ImageButton) findViewById(R.id.logout);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        titleAppBar=(TextView) findViewById(R.id.title_app_bar);

        Bundle bundle=getIntent().getExtras();

        email=bundle.getString("email");
        title=bundle.getString("title");
//        titleAppBar.setText(title);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject = editTextSubject.getText().toString().trim();
                message = editTextMessage.getText().toString().trim();

                emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Adding click listener
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertMessage();
            }
        });

    }
    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        { public void onClick(DialogInterface dialog, int which)
        { switch (which)
        { case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
            firebaseAuth.signOut();
            //closing activity
            //starting login activity
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            break;
            case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                break;
        } } };
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()); builder.setMessage("Logout?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show(); }
    }

