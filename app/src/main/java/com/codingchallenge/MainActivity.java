package com.codingchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.ContactAdapter;
import Models.Contacts;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Contacts> contactList;
    private ContactAdapter adapter;
    private AutoCompleteTextView searchEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        searchEditText = (AutoCompleteTextView) findViewById(R.id.searchEditText);

        fetchContacts();


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (contactList.size() == 0) {
                    Toast.makeText(MainActivity.this, "Please wait while results are loading.", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void fetchContacts() {

        String URL = "https://api.myjson.com/bins/jz6bp";


        StringRequest req = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        contactList = new ArrayList<Contacts>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.e("RESPONSE",response);

                            JSONArray json_contacts = jsonObject.getJSONArray("contacts");

                            for (int m = 0;m<json_contacts.length();m++) {

                                Contacts model = new Contacts();

                                JSONObject object = json_contacts.getJSONObject(m);
                                if (object.has("companyName")) {
                                    model.setCompanyName(object.getString("companyName"));
                                }/*else{
                                    model.setParent(" ");
                                }*/
                                if (object.has("parent")) {
                                    model.setParent(object.getString("parent"));
                                }else{
                                    model.setParent(" ");
                                }
                                if (object.has("name")) {
                                    model.setName(object.getString("name"));
                                }else{
                                    model.setName(" ");
                                }


                                if (object.has("managers")) {
                                    JSONArray jsonArray_managers = object.getJSONArray("managers");
                                  //  List<String> sample = new ArrayList<String>();

                                    Log.e("managers", "" + jsonArray_managers);
                                    for (int n = 0; n < jsonArray_managers.length(); n++) {
                                        String value = (String) jsonArray_managers.get(n);
                                        /*sample.add(value);
                                        model.setList(sample);*/
                                       model.setManager(value);
                                    //    Log.e("managers<><<<<>>>", "" + model.getList());
                                    }
                                }

                                if (object.has("phones")) {
                                    JSONArray jsonArray_phones = object.getJSONArray("phones");
                                    Log.e("phones", "" + jsonArray_phones);
                                    for (int p = 0; p < jsonArray_phones.length(); p++) {

                                        String valuePhone = (String) jsonArray_phones.get(p);
                                        model.setPhone(valuePhone);
                                        Log.e("phones<><<<<>>>", "" + valuePhone);
                                    }
                                }

                                if (object.has("addresses")) {
                                    JSONArray jsonArray_addresses = object.getJSONArray("addresses");
                                    Log.e("address", "" + jsonArray_addresses);
                                    for (int q = 0; q < jsonArray_addresses.length(); q++) {

                                        String valueAddress = (String) jsonArray_addresses.get(q);
                                        model.setAddresses(valueAddress);
                                        Log.e("address<><<<<>>>", "" + valueAddress);
                                    }
                                }

                                contactList.add(model);
                            }
                            if (contactList.size() == 0){
                                searchEditText.setEnabled(false);
                                Toast.makeText(MainActivity.this,"No results found",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                searchEditText.setEnabled(true);
                                adapter = new ContactAdapter(MainActivity.this, contactList);
                                listView.setAdapter(adapter);

                            }
                            Log.e("ListSize", "" + contactList.size());
                        }

                        catch (JSONException e){
                            Log.e("EXCEPTION", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());

                    }
                });
        req.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(req);

    }


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        super.onBackPressed();
    }

}
