package gits.helper.nfcapp.main;

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

import gits.helper.nfcapp.NfcActivity;
import gits.helper.nfcapp.PackAdapter;
import gits.helper.nfcapp.R;
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
    private List<PackDao.AllPackDao> mData = new ArrayList<>();
    private boolean isNfcAvailable = false;
    ApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rc = (RecyclerView) findViewById(R.id.rc_main);

        adapter = new PackAdapter(mData);
        layoutManager = new LinearLayoutManager(this);

        rc.setLayoutManager(layoutManager);
        rc.setAdapter(adapter);

        apiClient = new ApiClient(Constant.BASE_URL);

        Call<PackDao> call = apiClient.getApiInteface().GetAllPack();
        call.enqueue(new Callback<PackDao>() {
            @Override
            public void onResponse(Call<PackDao> call, Response<PackDao> response) {
                for (PackDao.AllPackDao data: response.body().getData()) {
                    mData.add(data);
                    Log.wtf("onResponse: ", data.getName());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PackDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Log.e(TAG, "onCreate: "+"Nfc Available" );
            isNfcAvailable = true;
        }else {
            Log.e(TAG, "onCreate: "+"Nfc Not Available");
            isNfcAvailable = false;
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
