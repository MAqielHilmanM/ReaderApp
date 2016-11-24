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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] intentFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Toast.makeText(this,"NfC Available",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"NfC not Available",Toast.LENGTH_SHORT).show();
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            Toast.makeText(this,"Nfc Intent received!",Toast.LENGTH_SHORT).show();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(parcelables != null && parcelables.length > 0){
                readTextFromMessage((NdefMessage)parcelables[0]);
            }else {
                Toast.makeText(this,"No Message",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readTextFromMessage(NdefMessage parcelable) {
        NdefRecord[] ndefRecords = parcelable.getRecords();
        if(ndefRecords != null && ndefRecords.length > 0){
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            Toast.makeText(this,"RECORD = "+tagContent,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"No Record",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        intentFilters = new IntentFilter[]{};

        //nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);
//        getAdapter();
    }

    private void getAdapter() {
//        if (nfcAdapter == null) {
////            NfcManager manager = (NfcManager) getSystemService(NFC_SERVICE);
////            nfcAdapter = manager.getDefaultAdapter();
//            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        }
//        return nfcAdapter;
        if(nfcAdapter==null)
        {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        }
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }


    @Override
    protected void onPause() {
        //nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord){
        String tagContent = null;
        try{
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0)?"UTF-8":"UTF-16";
            int langangueSize = payload[0] & 0063;
            tagContent = new String(payload,langangueSize + 1,payload.length - langangueSize - 1,textEncoding);
        }catch (UnsupportedEncodingException e){
            Log.e("getTextFromNdefRecord: ",e.getMessage(),e );
        }
        return tagContent;
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
}
