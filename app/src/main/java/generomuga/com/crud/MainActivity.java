package generomuga.com.crud;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //views
    private EditText mMessage;
    private Button mSend;
    private ListView mList;

    //firebase
    private DatabaseReference mDatabase;
    private DatabaseReference mMessageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebae
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMessageRef = mDatabase.child("messages");

        //init views
        mMessage = (EditText) findViewById(R.id.editTextMessage);
        mSend = (Button) findViewById(R.id.buttonSend);
        mList = (ListView) findViewById(R.id.listViewMessage);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMessage();
            }
        });

        //display message
    }

    private void createMessage(){
        //values
        String message = mMessage.getText().toString().trim();
        String id = mMessageRef.push().getKey();

        //save to object
        Message message1 = new Message();
        message1.setKey(id);
        message1.setMessage(message);

        mMessageRef.child(id).setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
