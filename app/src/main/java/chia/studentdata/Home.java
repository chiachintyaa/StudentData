package chia.studentdata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    private String TAG = Home.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    public displayData tampil = new displayData();

    // URL to get contacts JSON
  private static String url = "http://indo-event.com/test/getmhs.php";

    ArrayList<HashMap<String, String>> MhsList;

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    public static String dataNIM;
    public static String dataNama;
    public static String dataAlamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_design);

        MhsList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetMahasiswa().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), displayData.class);
                HashMap<String, Object> obj = (HashMap<String, Object>) lv.getItemAtPosition(position);
                dataNIM = (String) obj.get("nim");
                dataNama = (String) obj.get("nama");
                dataAlamat = (String) obj.get("alamat");

                i.putExtra(dataNIM,(String) obj.get("nim"));
                i.putExtra(dataNama,(String) obj.get("nama"));
                i.putExtra(dataAlamat,(String) obj.get("alamat"));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_add:
                addMhs();
                return true;
            case R.id.menu_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout(){
        Intent i = new Intent(this,about.class);
        startActivity(i);
    }

    private void addMhs(){
        Intent i = new Intent(this,newData.class);
        startActivityForResult(i,ACTIVITY_CREATE);
    }

    private class GetMahasiswa extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray data = jsonObj.getJSONArray("data");

                    // looping through All Mhs
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);

                        String nim = c.getString("nim");
                        String nama = c.getString("nama");
                        String alamat = c.getString("alamat");

                        // tmp hash map for single contact
                        HashMap<String, String> hashmhs = new HashMap<>();

                        // adding each child node to HashMap key => value
                        hashmhs.put("nim", nim);
                        hashmhs.put("nama", nama);
                        hashmhs.put("alamat", alamat);

                        // adding mhs to contact list
                        MhsList.add(hashmhs);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Home.this, MhsList,
                    R.layout.list_mhs, new String[]{"nim", "nama"}, new int[]{R.id.nim,
                    R.id.nama}
            );

            lv.setAdapter(adapter);
        }

    }
}
