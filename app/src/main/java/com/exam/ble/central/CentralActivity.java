package com.exam.ble.central;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.exam.ble.R;

import static com.exam.ble.Constants.REQUEST_ENABLE_BT;
import static com.exam.ble.Constants.REQUEST_FINE_LOCATION;


public class CentralActivity extends AppCompatActivity {

    //// GUI variables
    // text view for status
    private TextView tvStatus;
    // button for start scan
    private Button btnScan;
    // button for stop connection
    private Button btnStop;
    // button for send data
    private Button btnSend;
    private Button btnReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);

        initView();
        initBle();
        CentralManager.getInstance(this).initBle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // finish app if the BLE is not supported
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        CentralManager.getInstance(this).disconnectGattServer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    /**
     * 안드로이드 권한 설정 결과
     * 블루투스는 위치 권한을 필요로 함.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(CentralActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    /**
     * 화면의 텍스트뷰와 버튼을 바인딩하고 버튼 이벤트를 설정.
     */
    private void initView() {
        //// get instances of gui objects
        // status textview
        tvStatus = findViewById(R.id.tv_status);
        // scan button
        btnScan = findViewById(R.id.btnScan);
        // stop button
        btnStop = findViewById(R.id.btnStop);
        // send button
        btnSend = findViewById(R.id.btnSend);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CentralManager.getInstance(CentralActivity.this).startScan();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CentralManager.getInstance(CentralActivity.this).disconnectGattServer();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                String todayTime = (calendar.get(Calendar.MONTH) + 1)
                        + "월" + calendar.get(Calendar.DAY_OF_MONTH)
                        + "일 " + calendar.get(Calendar.HOUR_OF_DAY)
                        + ":" + calendar.get(Calendar.MINUTE)
                        + ":" + calendar.get(Calendar.SECOND);

                CentralManager.getInstance(CentralActivity.this).sendData(todayTime);
            }
        });
    }

    /**
     * CentralManager 에 콜백을 설정한다.
     */
    private void initBle() {
        CentralManager.getInstance(this).setCallBack(centralCallback);
    }

    /**
     * Request BLE enable
     * 블루투스 기능을 켠다.
     */
    private void requestEnableBLE() {
        Intent ble_enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(ble_enable_intent, REQUEST_ENABLE_BT);
    }

    /**
     * Request Fine Location permission
     * 위치 권한을 안내한다.
     */
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(CentralActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
    }

    /**
     * 화면에 텍스트 정보를 표시한다.
     * @param message
     */
    private void showStatusMsg(final String message) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String oldMsg = tvStatus.getText().toString();
                tvStatus.setText(oldMsg + "\n" + message);

                scrollToBottom();
            }
        };
        handler.sendEmptyMessage(1);
    }

    /**
     * 토스트를 표시한다.
     * @param message
     */
    private void showToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(CentralActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
        handler.sendEmptyMessage(1);
    }

    /**
     * 텍스트뷰에 새로운 내용이 입력된 후 스크롤을 아래로 이동시킨다.
     */
    private void scrollToBottom() {
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollview));
        scrollview.post(new Runnable() {
            @Override public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
    private void requestToServer(){

    }
    CentralCallback centralCallback = new CentralCallback() {
        @Override
        public void requestEnableBLE() {
            CentralActivity.this.requestEnableBLE();
        }

        @Override
        public void requestLocationPermission() {
            CentralActivity.this.requestLocationPermission();
        }

        @Override
        public void onStatusMsg(String message) {
            showStatusMsg(message);
        }

        @Override
        public void onToast(String message) {
            showToast(message);
        }
    };
}