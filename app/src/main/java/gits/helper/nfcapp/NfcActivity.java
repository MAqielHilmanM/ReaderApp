package gits.helper.nfcapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import gits.helper.nfcapp.databinding.ActivityNfcBinding;

public class NfcActivity extends AppCompatActivity {
    ActivityNfcBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

         binding = DataBindingUtil.setContentView(this,R.layout.activity_nfc);
        binding.setVm(this);


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

    private void readTextFromMessage(NdefMessage parcelable) {
        NdefRecord[] ndefRecords = parcelable.getRecords();
        if(ndefRecords != null && ndefRecords.length > 0){
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            binding.txtNfc.setText(tagContent);
        }else {
            Toast.makeText(this,"No Record",Toast.LENGTH_SHORT).show();
        }
    }
}
