package chia.studentdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Chintya on 4/13/2017.
 */

public class displayData extends AppCompatActivity {

    String nim;
    String nama;
    String alamat;
    private TextView dataNIM;
    private TextView dataNama;
    private TextView dataAlamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_design);

        dataNIM = (TextView)findViewById(R.id.dataNIM);
        dataNama = (TextView)findViewById(R.id.dataNama);
        dataAlamat = (TextView)findViewById(R.id.dataAlamat);

//        nim = (savedInstanceState == null) ? null : (String)savedInstanceState.getSerializable(Home.dataNIM);
//        nama = (savedInstanceState == null) ? null : (String)savedInstanceState.getSerializable(Home.dataNama);
//        alamat = (savedInstanceState == null) ? null : (String)savedInstanceState.getSerializable(Home.dataAlamat);

        Bundle extras = getIntent().getExtras();
        nim = extras.getString(Home.dataNIM);
        nama = extras.getString(Home.dataNama);
        alamat = extras.getString(Home.dataAlamat);

        dataNIM.setText(nim);
        dataNama.setText(nama);
        dataAlamat.setText(alamat);
    }
}
