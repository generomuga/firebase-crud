package generomuga.com.crud;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //views
    private EditText mMessage;
    private Button mSend;
    private ListView mList;

    //firebase
    private DatabaseReference mDatabase;
    private DatabaseReference mMessageRef;

    private ArrayList mListMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //value
        mListMessage = new ArrayList<>();

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

        displayMessage();
    }

    private void displayMessage(){
        final CustomAdapter customAdapter = new CustomAdapter(mListMessage, getApplicationContext());

        mListMessage.clear();
        mMessageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    Message message1 = dataSnapshot.getValue(Message.class);
                    mListMessage.add(message1);
                    mList.setAdapter(customAdapter);
                    mList.deferNotifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mList.invalidateViews();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createMessage(){
        //values
        String message = mMessage.getText().toString().trim();
        String id = mMessageRef.push().getKey();

        //save to object
        Message message1 = new Message();
        message1.setKey(id);
        message1.setMessage(message);

        if (TextUtils.isEmpty(message)){
            Toast.makeText(getApplicationContext(), "Empty message", Toast.LENGTH_LONG).show();
            return;
        }

        mMessageRef.child(id).setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
