package gits.helper.nfcapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import gits.helper.nfcapp.model.Constant;
import gits.helpers.ApiClient;
import gits.helpers.dao.PackDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    private static final String TAG = "NFC" ;
    private PackAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<PackDao> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rc = (RecyclerView) findViewById(R.id.rc_main);

        adapter = new PackAdapter(mData);
        layoutManager = new LinearLayoutManager(this);

        rc.setLayoutManager(layoutManager);
        rc.setAdapter(adapter);


        ApiClient apiClient = new ApiClient(Constant.BASE_URL);

        Call<PackDao> call = apiClient.getApiInteface().GetAllPack();


        call.enqueue(new Callback<PackDao>() {
            @Override
            public void onResponse(Call<PackDao> call, Response<PackDao> response) {
                mData.add(response.body());
                adapter.notifyDataSetChanged();
                Log.wtf("AqielRetard: ", response.body().getName());
            }

            @Override
            public void onFailure(Call<PackDao> call, Throwable t) {
                Log.wtf("AqielRetard: ", t.getMessage());
            }
        });



        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Log.e(TAG, "onCreate: "+"Nfc Available" );
            Toast.makeText(this,"NfC Available",Toast.LENGTH_SHORT).show();
        }else {
            Log.e(TAG, "onCreate: "+"Nfc Not Available");
        }
    }

    public void Scan(View view) {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this
        );
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a QR Code");
        integrator.setCaptureActivity(ScanBarcode.class);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onResume() {

        Intent intent = new Intent(this,NfcActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        IntentFilter[] intentFilters = new IntentFilter[]{};

        //nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);

        super.onResume();
    }

    @Override
    protected void onPause() {
//        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();

    }

}
