package com.codingchallenge;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import Adapter.ContactAdapter;
import Models.Contacts;

/**
 * Created by eva on 06/07/18.
 */

public class ContactFilter extends Filter {

    // Intializing the variables

    private List<Contacts> contacts = new ArrayList<Contacts>();
    private ContactAdapter mAdapter;
    private List<Contacts> mylist;

    public ContactFilter(List<Contacts> contacts, ContactAdapter mAdapter, List<Contacts> mpermanent) {
        this.contacts = contacts;
        this.mAdapter = mAdapter;
        this.mylist = mpermanent;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();

        if (constraint == null || constraint.length() == 0) {
            results.values = mylist;
            results.count = mylist.size();
        } else {
            contacts = mylist;

            List<Contacts> cont = new ArrayList<>();

            for (int i = 0; i < contacts.size(); i++) {
                Contacts p = contacts.get(i);
                if (p.getCompanyName() != null){
                    if (p.getCompanyName().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        cont.add(p);
                    }
                }

                results.values = cont;
                results.count = cont.size();
            }
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results.count == 0) {
        } else {
            contacts = (List<Contacts>) results.values;
            mAdapter.setContacts(contacts);
            mAdapter.notifyDataSetChanged();

        }
    }
}
