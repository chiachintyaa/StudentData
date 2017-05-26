package chia.studentdata;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static android.R.attr.value;

/**
 * Created by Chintya on 4/10/2017.
 */

public class newData extends AppCompatActivity implements View.OnClickListener{

    private EditText txtNIM;
    private EditText txtNama;
    private EditText txtAlamat;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newinput);

        txtNIM = (EditText) findViewById(R.id.txtNIM);
        txtNama = (EditText) findViewById(R.id.txtNama);
        txtAlamat = (EditText) findViewById(R.id.txtAlamat);
        btn = (Button) findViewById(R.id.cmdSimpan);
        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (txtNIM.getText().toString().length() < 1) {
            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        }else if (txtNama.getText().toString().length() < 1) {
            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        }else if (txtAlamat.getText().toString().length() < 1) {
            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        }else {
            new MyAsyncTask().execute();
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        String nim = txtNIM.getText().toString();
        String nama = txtNama.getText().toString();
        String alamat = txtAlamat.getText().toString();

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            //nanti ganti ke url dosennya
            HttpPost httppost = new HttpPost("http://demo.achmatim.com/mobprog/newmhs.php?key=1511500868");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("key", "1511500868"));
                nameValuePairs.add(new BasicNameValuePair("nim", nim));
                nameValuePairs.add(new BasicNameValuePair("nama", nama));
                nameValuePairs.add(new BasicNameValuePair("alamat", alamat));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();

        }

    }

    // handler untuk tombol simpan
//    public void buttonHandler(View view){
//        saveMhs();
//    }

}
