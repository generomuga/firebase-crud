package generomuga.com.crud;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateDialog {

    //firebase
    private DatabaseReference mDatabase;

    //views
    private Dialog mDialog;
    private EditText mMessage;
    private Button mUpdate;
    private Button mCancel;

    public void showDialog(Activity activity, String message, final String key){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog = new Dialog(activity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.update_view);

        mMessage = (EditText) mDialog.findViewById(R.id.editTextMessage);
        mUpdate = (Button) mDialog.findViewById(R.id.buttonUpdate);
        mCancel = (Button) mDialog.findViewById(R.id.buttonCancel);

        mMessage.setText(message);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(key, mMessage.getText().toString().trim());
            }
        });

        mDialog.show();
    }

    private void update(String key, String newMessage){
        if (TextUtils.isEmpty(newMessage)){
            Toast.makeText(MainActivity.mActivity, "Empty message", Toast.LENGTH_LONG).show();
            return;
        }

        HashMap mapUpdate = new HashMap();
        mapUpdate.put("message", newMessage);

        DatabaseReference messageRef = mDatabase.child("messages").child(key);
        messageRef.updateChildren(mapUpdate).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.mActivity, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    mDialog.dismiss();
                }
            }
        });

    }

}
