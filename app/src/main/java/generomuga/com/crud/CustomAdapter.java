package generomuga.com.crud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Message>{

    Context mContext;
    ArrayList<Message> dataSet;
    DatabaseReference mDatabase;

    private static class ViewHolder{
        TextView mMessage;
        Button mDelete;
        Button mUpdate;
    }

    public CustomAdapter(ArrayList<Message> data, Context context) {
        super(context, R.layout.list_view, data);
        this.dataSet = data;
        this.mContext = context;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Message message1 = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view, parent, false);

            viewHolder.mMessage = (TextView) convertView.findViewById(R.id.textViewMessage);
            viewHolder.mDelete = (Button) convertView.findViewById(R.id.buttonDelete);
            viewHolder.mUpdate = (Button) convertView.findViewById(R.id.buttonUpdate);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mMessage.setText(message1.getMessage());

        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(message1.getKey());
                dataSet.remove(dataSet.get(position));
            }
        });

        return convertView;
    }

    private void delete(String key){
        DatabaseReference messageRef = mDatabase.child("messages").child(key);
        messageRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void refreshEvents(ArrayList<Message> data) {
        this.dataSet.clear();
        this.dataSet.addAll(data);
        notifyDataSetChanged();
    }

}
