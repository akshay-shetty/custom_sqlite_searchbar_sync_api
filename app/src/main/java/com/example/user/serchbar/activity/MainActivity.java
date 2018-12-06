package com.example.user.serchbar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.user.serchbar.R;
import com.example.user.serchbar.adapter.ListViewAdapter;
import com.example.user.serchbar.database.DBManager;
import com.example.user.serchbar.network.NetworkResponseHelper;
import com.example.user.serchbar.network.model.DataModelResponse;
import com.example.user.serchbar.network.model.ShowSqliteData;
import com.example.user.serchbar.network.model.SyncResponse;
import com.example.user.serchbar.network.model.ShowListViewModel;
import com.example.user.serchbar.utils.ApplicationConstant;
import com.example.user.serchbar.utils.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView search_bar;
    private LinearLayout search_list;
    private ImageView close;
    private ListView list;
    private ArrayList<ShowListViewModel> arraylist = new ArrayList();
    private ProgressDialog mProgressDialog;
    private RequestQueue mRequestQueue;

    private ListViewAdapter adapter;
    private EditText editsearch;
    private DBManager dbManager;
    private ArrayList<DataModelResponse> storedData;
    private String Count="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        storedData = new ArrayList<>();
    if (new UserSessionManager(this).getCountDetails().get("count").equals(null)){

    }else {
       Count= new UserSessionManager(this).getCountDetails().get("count");
    }
        Toast.makeText(this, ""+Count, Toast.LENGTH_SHORT).show();

        dbManager = new DBManager(this);
        dbManager.open();


        editsearch = findViewById(R.id.search);
        search_bar = findViewById(R.id.search_bar);
        search_list = findViewById(R.id.search_list);
        close = findViewById(R.id.close);
        close.setOnClickListener(this::closeModule);
        search_bar.setOnClickListener(this::searchClick);

        // Locate the ListView in listview_main.xml
        list = findViewById(R.id.listview);


        ArrayValue();
        // Pass results to ListViewAdapter Class


        // Locate the EditText in listview_main.xml
        editsearch = findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void ArrayValue() {
        ArrayList<ShowSqliteData> contacts = dbManager.getAllRecords();


        for (int i = 0; i < contacts.size(); i++) {

            contacts.get(i);

            ShowListViewModel wp = new ShowListViewModel(contacts.get(i).getX(), contacts.get(i).getY(),
                    contacts.get(i).getHeader(), contacts.get(i).getDisplay(), contacts.get(i).getId());
            // Binds all strings into an array
            arraylist.add(wp);

        }
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

    }

    public void closeModule(View view) {
        if (editsearch.getText().toString().equals("")){
            search_list.setVisibility(View.GONE);

        }else {
            editsearch.setText("");

        }
    }

    public void searchClick(View view) {

        Toast.makeText(this, "searchView", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sync:
                getSubmit(this);
                break;
            case R.id.search_icon:


                ArrayValue();
                if (search_list.getVisibility() == View.GONE)
                    search_list.setVisibility(View.VISIBLE);
                break;
        }
        return true;

    }

    private void getSubmit(Context mContext) {

        mProgressDialog = ProgressDialog.show(MainActivity.this, "",
                getResources().getString(R.string.processing), true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setOnCancelListener(dialog -> {
            Toast.makeText(MainActivity.this, R.string.Please_Wait, Toast.LENGTH_SHORT).show();
        });


        String REQUEST_URL = ApplicationConstant.BASE_URL;
        Log.e(TAG, REQUEST_URL);
        Map<String, String> params = new HashMap<>();
        params.put("controller", "simple_search_data");
        params.put("count", Count);

        mRequestQueue = Volley.newRequestQueue(mContext);

        // Request with API parameters
        NetworkResponseHelper<SyncResponse> myReq =
                new NetworkResponseHelper<>(
                        Request.Method.POST,
                        REQUEST_URL,
                        SyncResponse.class,
                        params,
                        createMyReqSuccessListener(),
                        createMyReqErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                ApplicationConstant.SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(myReq);
    }

    private Response.Listener<SyncResponse> createMyReqSuccessListener() {
        return new Response.Listener<SyncResponse>() {
            @Override
            public void onResponse(SyncResponse response) {


                try {
                    responsejsonModule(response);


                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

            }

        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e(TAG, "@@AK ERROR" + error.toString());
            }
        };
    }

    private void responsejsonModule(SyncResponse response) {

        if (response.getSuccess().equals(ApplicationConstant.RESPONSE_SUCCESS)) {

            Log.d(TAG, "@@AK" + response.getCount());

            UserSessionManager sessionManager = new UserSessionManager(MainActivity.this);
            Toast.makeText(this, ""+response.getCount(), Toast.LENGTH_SHORT).show();
            sessionManager.createCountSession(response.getCount());
            dbManager = new DBManager(MainActivity.this);
            dbManager.open();
            for (int i = 0; i <= response.getData().size(); i++) {


                dbManager.insert(response.getData().get(i).getX(), response.getData().get(i).getY(), response.getData().get(i).getHeader(), response.getData().get(i).getDisplay());
                Log.d("@@AK ", String.valueOf(i));
            }

        } else if (response.getSuccess().equals(ApplicationConstant.RESPONSE_FAILURE)) {
            Toast.makeText(this, ""+response.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
