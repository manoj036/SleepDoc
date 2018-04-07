package com.example.manojkumar.practiceui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.manojkumar.practiceui.ble.Constants;
import com.example.manojkumar.practiceui.ble.DeviceListActivity;
import com.example.manojkumar.practiceui.ble.MyBluetoothService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class LiveMonitoringActivity extends BaseActivity {

    List<Entry> entryList=new ArrayList<>();
    int data_final_element=0;
    LineChart lineChart;
    LineData lineData;
    int xSize=1000;
    XAxis xAxis;
    DatabaseReference myRef;
    Integer data= 0;

    private static final String TAG = "BluetoothChatFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    public static MyBluetoothService myBluetoothService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkBTPermissions();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        handler= new Handler();
        sensorManager.registerListener(SensorDataLogger, sensor,1000);
        entryList.add(new Entry(data_final_element++,0));

        LineDataSet lineDataSet=new LineDataSet(entryList,"Gravity Data");
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(4f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(.2f);
        lineDataSet.setColor(Color.parseColor("#00f4fe"));
        lineDataSet.setDrawHorizontalHighlightIndicator(false);

        lineData=new LineData(lineDataSet);

        lineChart=findViewById(R.id.respiration_chart);
        lineChart.setData(lineData);

        lineChart.setDrawBorders(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setDrawGridBackground(false);
        Legend l = lineChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTextSize(18);
        l.setTextColor(Color.parseColor("#00f4fe"));
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        xAxis=lineChart.getXAxis();
        xAxis.setAxisMaximum(xSize);
        xAxis.setDrawLabels(false);
        YAxis yAxis=lineChart.getAxisLeft();
        yAxis.setDrawLabels(false);
        lineChart.getDescription().setEnabled(false);
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(-10);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.invalidate();
        myRef = FirebaseDatabase.getInstance().getReference();
        handler.post(FireBasePushData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CURRENT_BTM_ACTIVITY =R.id.live_monitoring_button;

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()){
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            mBluetoothAdapter.enable();
            // Otherwise, setup the chat session
        } else if (myBluetoothService == null) {
            setupService();
        }
    }

    private void setupService() {
        Log.d(TAG, "setupService()");

        myBluetoothService = new MyBluetoothService(this, mHandler);
    }


    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler=new MyHandler();

    @SuppressLint("HandlerLeak")
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case MyBluetoothService.STATE_CONNECTED:
                            Toast.makeText(LiveMonitoringActivity.this, "Connected to "+mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                            break;
                        case MyBluetoothService.STATE_CONNECTING:
                            Toast.makeText(LiveMonitoringActivity.this, "Connecting...", Toast.LENGTH_SHORT).show();
                            break;
                        case MyBluetoothService.STATE_LISTEN:
                        case MyBluetoothService.STATE_NONE:
                            Toast.makeText(LiveMonitoringActivity.this, "You are not connected to device", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d(TAG, "handleMessage: "+readMessage);
                    data=Integer.parseInt(readMessage);
                    if(data_final_element>xSize) {
                        xAxis.setAxisMinimum(data_final_element-xSize);
                        xAxis.setAxisMaximum(data_final_element);
                    }
                    entryList.add(new Entry(data_final_element++,data));
                    lineData.notifyDataChanged();
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();

                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupService();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getApplicationContext(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        myBluetoothService.connect(device);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBluetoothService != null) {
            myBluetoothService.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (myBluetoothService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (myBluetoothService.getState() == MyBluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                myBluetoothService.start();
            }
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_live_monitoring;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.live_monitoring_button;
    }

    private Handler handler=new Handler();

    private Runnable FireBasePushData =new Runnable() {
        @Override
        public void run() {
            Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            Date d=calendar.getTime();
            calendar.setTime(d);
            Long samplingTime=calendar.getTimeInMillis()/1000;
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            calendar.set(Calendar.AM_PM,Calendar.AM);

            Long startOfDayTimeStamp=calendar.getTimeInMillis()/1000;

            myRef.child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(startOfDayTimeStamp.toString())
                    .child("Environment Data")
                    .child(samplingTime.toString())
                    .child("Gravity")
                    .setValue(data);
            handler.postDelayed(FireBasePushData,1000*30*60);
        }
    };

    SensorEventListener SensorDataLogger=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void checkBTPermissions() {
        int permissionCheck;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }
    }
}
