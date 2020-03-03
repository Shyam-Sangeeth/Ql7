package ql.cev.ql9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter{
    private Context context;
    private ArrayList data;
    public ContactAdapter(Context a, ArrayList d) {
        context = a;
        data=d;
    }
    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder{
        TextView Position;
        TextView Name;
        TextView ContactNumber;
    }
    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            vi = inflater.inflate(R.layout.contact_items, null);
            holder = new ViewHolder();
            holder.Position = vi.findViewById(R.id.position);
            holder.Name= vi.findViewById(R.id.name);
            holder.ContactNumber= vi.findViewById(R.id.contact_no);
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();
        if(data.size()<=0)
        {
            holder.Position.setText("No Data");
        }
        else
        {
            ContactListModel tempValues = (ContactListModel) data.get(position);
            holder.Position.setText( tempValues.getPosition() );
            holder.Name.setText( tempValues.getName() );
            holder.ContactNumber.setText(tempValues.getContactNumber());
        }
        return vi;
    }
}