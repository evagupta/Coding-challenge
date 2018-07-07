package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.codingchallenge.R;

import java.util.List;

import com.codingchallenge.ContactFilter;
import Models.Contacts;

/**
 * Created by eva on 06/07/18.
 */

public class ContactAdapter extends BaseAdapter implements Filterable{

    private Context context;
    private List<Contacts> contactList;
    private List<Contacts> permanent;
    private ContactFilter mfiltersearch;

    public ContactAdapter(Context context, List<Contacts> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.permanent = contactList;
    }

    public List<Contacts> getContacts() {
        return contactList;
    }


    public void setContacts(List<Contacts> list) {
        this.contactList = list;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_row_item, null, true);
            holder.mTextViewName = convertView.findViewById(R.id.tvname);
            holder.mTextViewPhone = convertView.findViewById(R.id.tvphone);
            holder.mTextViewAddresses = convertView.findViewById(R.id.tvAddresses);
            holder.mTextViewParent = convertView.findViewById(R.id.tvparent);
            holder.mTextViewManager = convertView.findViewById(R.id.tvManager);
            holder.getmTextViewSimpleName = convertView.findViewById(R.id.tvsimplename);
            convertView.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }

        holder.mTextViewName.setText(contactList.get(position).getCompanyName());
        holder.mTextViewPhone.setText(contactList.get(position).getPhone());
        holder.mTextViewParent.setText(contactList.get(position).getParent());
        holder.mTextViewManager.setText(contactList.get(position).getManager());
        holder.mTextViewAddresses.setText(contactList.get(position).getAddresses());
        holder.getmTextViewSimpleName.setText(contactList.get(position).getName());

     /*   Log.e("PHONE????",""+ contactList.get(position).getList());


          for (int i = 0;i<contactList.get(position).getList().size();i++){
              String managers = String.valueOf(contactList.get(i));
              Log.e("ADAPTER::::",managers);
          //    holder.mTextViewPhone.setText(contactList.get(i));
          }
*/
        return convertView;
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }


    @Override
    public Filter getFilter() {
        if (mfiltersearch == null) {
            mfiltersearch = new ContactFilter(this.contactList, this, this.permanent);
        }
        return mfiltersearch;
    }

    private class Holder {
        public TextView mTextViewName, mTextViewPhone,mTextViewParent,mTextViewManager,mTextViewAddresses,getmTextViewSimpleName;
    }
}