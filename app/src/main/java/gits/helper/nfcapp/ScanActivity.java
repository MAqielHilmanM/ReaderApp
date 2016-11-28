package gits.helper.nfcapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import gits.helper.nfcapp.model.Constant;
import gits.helpers.ApiClient;
import gits.helpers.dao.AddDeleteDao;
import gits.helpers.dao.InsertStickerPack;
import gits.helpers.dao.PackStickerDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {

    //Button editText;
    RecyclerView recyclerView;
    AlertDialog.Builder builder;
    private String result = "";
    LinearLayoutManager layoutManager;
    ScanAdapter scanAdapter;
    List<PackStickerDao.PackSticker> list = new ArrayList<>();
    ApiClient apiClient;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("id"),Toast.LENGTH_SHORT).show();
        builder = new AlertDialog.Builder(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_scan);

        recyclerView = (RecyclerView)findViewById(R.id.rc_scan);

        layoutManager = new LinearLayoutManager(this);
        scanAdapter = new ScanAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scanAdapter);

        apiClient = new ApiClient(Constant.BASE_URL);
        final Call<PackStickerDao> call = apiClient.getApiInteface().GetPackSticker(getIntent().getStringExtra("id"));

        call.enqueue(new Callback<PackStickerDao>() {
            @Override
            public void onResponse(Call<PackStickerDao> call, Response<PackStickerDao> response) {
                for (PackStickerDao.PackSticker packSticker : response.body().getData()) {
                    list.add(packSticker);
                }
                scanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PackStickerDao> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call.clone().enqueue(new Callback<PackStickerDao>() {
                    @Override
                    public void onResponse(Call<PackStickerDao> call, Response<PackStickerDao> response) {
                        list.clear();
                        for (PackStickerDao.PackSticker packSticker : response.body().getData()) {
                            list.add(packSticker);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        scanAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<PackStickerDao> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        builder.setTitle("Konfrimasi");
        final EditText input = new EditText(this);
        //id = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.wtf("onClick: ", "Hasil Scan "+result+", dari et "+input.getText().toString());

                Call<AddDeleteDao> call = apiClient.getApiInteface().AddStickerToAPack(getIntent().getStringExtra("id"),
                        new InsertStickerPack.PackReqeust.ListSticker(result.replace("stroomhead://code/",""), Integer.parseInt(input.getText().toString())));

                call.enqueue(new Callback<AddDeleteDao>() {
                    @Override
                    public void onResponse(Call<AddDeleteDao> call, Response<AddDeleteDao> response) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        result ="";
                        input.setText("");
                    }

                    @Override
                    public void onFailure(Call<AddDeleteDao> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        result ="";
                        input.setText("");
                    }
                });
                //Toast.makeText(this, "Scanned: " + result, Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                result = "";
            }
        });

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getIntent().getStringExtra("nama"));

//        editText = (Button) findViewById(R.id.et_number);
//
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                builder.show();
//            }
//        });




    }

    public void Scan(View view) {
        IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
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
                this.result = result.getContents();
                builder.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
