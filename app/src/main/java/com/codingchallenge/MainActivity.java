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

    //Initializing the variables

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

        // WebService method called
        fetchContacts();

        // For the search of the elements in the listView through Autocomplete TextView
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

    // HTTP call
    private void fetchContacts() {

        String URL = "https://api.myjson.com/bins/jz6bp";

        StringRequest req = new StringRequest(Request.Method.GET, URL,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                contactList = new ArrayList<Contacts>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray json_contacts = jsonObject.getJSONArray("contacts");
                    for (int m = 0;m<json_contacts.length();m++) {

                        Contacts model = new Contacts();
                        JSONObject object = json_contacts.getJSONObject(m);
                        if (object.has("companyName")) {
                            model.setCompanyName(object.getString("companyName"));
                        }else{
                            model.setCompanyName("No Data");
                        }
                        if (object.has("parent")) {
                            model.setParent(object.getString("parent"));
                        }else{
                            model.setParent("No Data");
                        }
                        if (object.has("name")) {
                            model.setName(object.getString("name"));
                        }else{
                            model.setName("No Data");
                        }

                        if (object.has("managers")) {
                            JSONArray jsonArray_managers = object.getJSONArray("managers");

                            StringBuilder stringBuilder = new StringBuilder();
                            Log.e("managers", "" + jsonArray_managers);
                            for (int n = 0; n < jsonArray_managers.length(); n++) {
                                String value = (String) jsonArray_managers.get(n);

                                stringBuilder.append(value+",");
                                String manager = stringBuilder.toString();
                                model.setManager(manager.substring(0,manager.length()-1));

                            }
                        }else{
                            model.setManager("No Data");
                        }

                        if (object.has("phones")) {
                            JSONArray jsonArray_phones = object.getJSONArray("phones");
                            StringBuilder stringBuilder = new StringBuilder();
                            Log.e("phones", "" + jsonArray_phones);
                            for (int p = 0; p < jsonArray_phones.length(); p++) {

                                String valuePhone = (String) jsonArray_phones.get(p);
                                stringBuilder.append(valuePhone+",");
                                String manager = stringBuilder.toString();
                                model.setPhone(manager.substring(0,manager.length()-1));
                            }
                        }else{
                            model.setPhone("No Data");
                        }
                        if (object.has("addresses")) {
                            JSONArray jsonArray_addresses = object.getJSONArray("addresses");
                            StringBuilder stringBuilder = new StringBuilder();
                            Log.e("address", "" + jsonArray_addresses);
                            for (int q = 0; q < jsonArray_addresses.length(); q++) {
                                String valueAddress = (String) jsonArray_addresses.get(q);
                                stringBuilder.append(valueAddress+",");
                                String manager = stringBuilder.toString();
                                model.setAddresses(manager.substring(0,manager.length()-1));
                            }
                        }else {
                            model.setAddresses("No Data");
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

                }

                catch (JSONException e){

                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(req);

    }


    // onBackPressed method to go out of the class
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
