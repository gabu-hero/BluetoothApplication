package com.example.mad9;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView btstatus, btpairedtxt;
    ImageView bt;
    Button bton, btoff, btdiscover, btpaired;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    BluetoothAdapter btad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btstatus = findViewById(R.id.status);

        bt = findViewById(R.id.bluetoothImage);
        bton = findViewById(R.id.bluetoothButtonOn);
        btoff = findViewById(R.id.bluetoothButtonOff);


        btad = BluetoothAdapter.getDefaultAdapter();

        if (btad == null) {
            btstatus.setText("NO DEVICES AVAILABLE!");
        } else {
            btstatus.setText(" DEVICES AVAILABLE!");
            bt.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.bticon1));
        }
        if (btad.isEnabled()) {
            bt.setImageResource( R.drawable.bticon2);
        } else {
            bt.setImageResource( R.drawable.bticon3);

        }
        bton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btad.isEnabled()) {
                    showToast("Turning on Bluetooth...");
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    }
                    startActivityForResult(i, REQUEST_ENABLE_BT);
                } else {
                    showToast("Bluetooth is already on!");
                }
            }
        });

        btoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btad.isEnabled()) {
                    btad.isEnabled();
                    showToast("Turning Bluetoth off");
                    bt.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bticon3));
                } else {
                    showToast("Bluetooth is already off!");
                }
            }
        });

    }
    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode==0){
                    bt.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.bticon2));
                    showToast("Bluetoth is on");

                }
                else{
                    showToast("Could not turn on Bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}