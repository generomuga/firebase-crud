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

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Message>{

    Context mContext;
    ArrayList<Message> dataSet;

    private static class ViewHolder{
        TextView mMessage;
        Button mDelete;
        Button mUpdate;
    }

    public CustomAdapter(ArrayList<Message> data, Context context) {
        super(context, R.layout.list_view, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message1 = getItem(position);
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

        return convertView;
    }
}
