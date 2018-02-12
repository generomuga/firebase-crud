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

    private static class ViewHolder{
        TextView mMessage;
        Button mDelete;
        Button mUpdate;
    }

    public CustomAdapter(ArrayList<Message> data, Context context) {
        super(context, R.layout.list_view, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message1 = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view, parent, false);

            convertView.setTag(viewHolder);
        }


        return convertView;
    }
}
