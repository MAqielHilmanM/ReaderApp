package gits.helper.nfcapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import gits.helpers.dao.PackDao;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    private static final String TAG = "NFC" ;
    private PackAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<PackDao> mData = new ArrayList<>();
    private boolean isNfcAvailable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rc = (RecyclerView) findViewById(R.id.rc_main);

        adapter = new PackAdapter(mData);
        layoutManager = new LinearLayoutManager(this);

        rc.setLayoutManager(layoutManager);
        rc.setAdapter(adapter);


        Tester test = new Tester();
//        test.getAvailablePack();
//        test.getPackSticker("a0900fba-28eb-40af-9e3e-6dcca9e29c2a");
//        test.testAllPack();
//        test.getPackUsedByCompanies();
        test.testStickerByIdPack("74c2889b-6c4a-4ceb-97c3-cf5186e6bb6a",1);


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Log.e(TAG, "onCreate: "+"Nfc Available" );
            isNfcAvailable = true;
        }else {
            Log.e(TAG, "onCreate: "+"Nfc Not Available");
            isNfcAvailable = false;
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

        if(isNfcAvailable){
            nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(isNfcAvailable){
            nfcAdapter.disableForegroundDispatch(this);
        }
        super.onPause();

    }
}
