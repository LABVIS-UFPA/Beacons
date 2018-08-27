package com.example.labvis.bluetoothle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BLE extends AppCompatActivity {
    private static final long DELAY = (100);
    private static final long SCAN_PERIODO = (60000);
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private ArrayAdapter<String> arrayAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private boolean mScanning;
    private Handler mHandler;
    private List<Ponto> listaPontos = new ArrayList<Ponto>();
    criarListaTXT criartxt = new criarListaTXT();
    private double ponto[] = new double[2];
    private TextView textView;
    private DrawView drawView;
    private ImageView imageView;
    private int tam = 360;
    private int altura = 360;
    private int largura = 360;

    Ponto ponto1 = new Ponto("DD:81:9F:77:39:74", 0, largura);//jaalee
    Ponto ponto2 = new Ponto("FD:72:A2:07:64:29", 0, 0);//wh1
    Ponto ponto3 = new Ponto("C6:3C:24:FD:11:58", altura, 0);//gr2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        imageView = (ImageView) findViewById(R.id.imageview);

        listaPontos.add(ponto1);
        listaPontos.add(ponto2);
        listaPontos.add(ponto3);

        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        ListView listView = (ListView)findViewById(R.id.listview2);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

        textView = (TextView) findViewById(R.id.textView1);
        drawView = new DrawView(imageView, tam, altura, largura);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                filters = new ArrayList<ScanFilter>();
            }
            metodoDeControle();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            scanLeDevice(false);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            // Cria a lista txt adicionando os elementos do arrayAdapter
            criartxt.addLista(arrayAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                //Bluetooth not enabled.
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void metodoDeControle () {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    scanLeDevice(true);
                    arrayAdapter.add("\n[INICIO-DA-RODADA]");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },DELAY, SCAN_PERIODO);
    }


    private void scanLeDevice (final boolean enable){
        if (enable) {
            // Interrompe a varredura após um período(INTERVAL) pré-definido.
            mHandler.postDelayed (new Runnable () {
                @Override
                public void run () {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan (mLeScanCallback);
                }
            }, SCAN_PERIODO - 10000);
            mScanning = true;
            mBluetoothAdapter.startLeScan (mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan (mLeScanCallback);
        }
    }

    Localization L = new Localization();
    double txpower, distancia;
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //if(device.getName() != null) {
                    if(device.getAddress().equals(ponto1.getMAC()) ||
                            device.getAddress().equals(ponto2.getMAC()) ||
                            device.getAddress().equals(ponto3.getMAC())){
                        txpower = (-70 + rssi)/2;
                        distancia = L.calcularDistancia(txpower,rssi);

                        /* Adiciona os dados dos dispositivos descobertos no arrayAdapter,
                         * que será exibido na no listView.
                         */
                        arrayAdapter.add("\nDevice: " + device.getName() + "\nMAC: " + device.getAddress() +
                                "\nrssi: " + rssi + " dBm" + "\nDistância: " + distancia+" m \n");
                        arrayAdapter.notifyDataSetChanged();

                        for(int i = 0; i < listaPontos.size(); i++) {
                            if (listaPontos.get(i).getMAC().equals(device.getAddress())) {
                                listaPontos.get(i).setdist(distancia);
                            }
                        }

                        /* A condição para que o método decobrirCoodenadas possa ser usado, é que
                         * os valores de distância de cada um dos pontos sejam diferentes de zero.
                         */
                        //if(( ponto2.getdist()!= 0 && ponto3.getdist() != 0)){
                        if((ponto1.getdist()!= 0 && ponto2.getdist()!= 0 && ponto3.getdist() != 0)){
                            ponto = L.descobrirCoordenadas(ponto1,ponto2,ponto3);
                            //ponto = L.coordenadasLinha(ponto2, ponto3);
                            drawView.drawSomething(imageView, (int)(ponto[0]*100), (int)(ponto[1]*100));
                            textView.setText("X = "+L.RCD(ponto[0])+" m    Y = "+L.RCD(ponto[1])+" m");
                        }
                    }
                }
            });
        }
    };

    public void stop(View view) {
        finish();
    }
}
