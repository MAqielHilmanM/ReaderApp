package gits.helper.nfcapp;

import android.util.Log;

import gits.helper.nfcapp.model.Constant;
import gits.helpers.ApiClient;
import gits.helpers.dao.AddDeleteDao;
import gits.helpers.dao.InsertStickerPack;
import gits.helpers.dao.PackDao;
import gits.helpers.dao.PackStickerDao;
import gits.helpers.dao.PackUsedByCompanyDao;
import gits.helpers.dao.StickerIdByPack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 11/25/16.
 */

public class Tester {

    public static final String TAG = "TESTER";

    ApiClient apiClient = new ApiClient(Constant.BASE_URL);

    public void deleteAStickerFromAPack(String packId, AddDeleteDao.DeleteRequest map){
        Call<AddDeleteDao> call = apiClient.getApiInteface().HttpDeleteStickerFromPack(packId, map);
        call.enqueue(new Callback<AddDeleteDao>() {
            @Override
            public void onResponse(Call<AddDeleteDao> call, Response<AddDeleteDao> response) {
                Log.e(TAG, "onResponse: "+response.message() );
            }

            @Override
            public void onFailure(Call<AddDeleteDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }

    public void insertStickerPack(int dryrun , InsertStickerPack.PackReqeust map){
        Call<InsertStickerPack> call = apiClient.getApiInteface().InsertStickerPack(dryrun, map);
        call.enqueue(new Callback<InsertStickerPack>() {
            @Override
            public void onResponse(Call<InsertStickerPack> call, Response<InsertStickerPack> response) {
                Log.e(TAG, "onResponse: "+response.body().getData() );
            }

            @Override
            public void onFailure(Call<InsertStickerPack> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }

    public void addStickerToPack(String idSticker, InsertStickerPack.PackReqeust.ListSticker listSticker){
//        InsertStickerPack.PackReqeust.ListSticker listSticker = new InsertStickerPack.PackReqeust.ListSticker("id",22);
        Call<AddDeleteDao> call = apiClient.getApiInteface().AddStickerToAPack(idSticker,listSticker);
        call.enqueue(new Callback<AddDeleteDao>() {
            @Override
            public void onResponse(Call<AddDeleteDao> call, Response<AddDeleteDao> response) {
                Log.e(TAG, "onResponse: "+response.message() );
            }

            @Override
            public void onFailure(Call<AddDeleteDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }

    public void getAvailablePack(){
        Call<PackDao> call = apiClient.getApiInteface().GetAvailablePack();
        call.enqueue(new Callback<PackDao>() {
            @Override
            public void onResponse(Call<PackDao> call, Response<PackDao> response) {
                for (PackDao.AllPackDao data:
                        response.body().getData()
                     ) {
                    Log.e(TAG, "onResponse: Id:"+data.getId() );
                    Log.e(TAG, "onResponse: TotalSticker:"+data.getTotal_sticker() );
                    Log.e(TAG, "onResponse: Name:"+data.getName() );
                    Log.e(TAG, "onResponse: Used:"+data.getUsed() );
                }
            }

            @Override
            public void onFailure(Call<PackDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }

    public void getPackSticker(String stickerId){
        Call<PackStickerDao> call = apiClient.getApiInteface().GetPackSticker(stickerId);
        call.enqueue(new Callback<PackStickerDao>() {
            @Override
            public void onResponse(Call<PackStickerDao> call, Response<PackStickerDao> response) {
                for (PackStickerDao.PackSticker pack:
                        response.body().getData()
                        ) {
                    Log.e(TAG, "onResponse: Id:"+pack.getId() );
                    Log.e(TAG, "onResponse: name:"+pack.getName() );
                    Log.e(TAG, "onResponse: number:"+pack.getNumber() );
                }
            }

            @Override
            public void onFailure(Call<PackStickerDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
    public void getPackUsedByCompanies(){
        Call<PackUsedByCompanyDao> call = apiClient.getApiInteface().GetPackByCompanyDao();
        call.enqueue(new Callback<PackUsedByCompanyDao>() {
            @Override
            public void onResponse(Call<PackUsedByCompanyDao> call, Response<PackUsedByCompanyDao> response) {
                for (PackUsedByCompanyDao.packByCompany pack : response.body().getData()){
                    Log.e(TAG, "onResponse: "+"Id: "+pack.getId());
                    Log.e(TAG, "onResponse: "+"used: "+pack.getUsed());
                    Log.e(TAG, "onResponse: "+"name: "+pack.getName());
                    Log.e(TAG, "onResponse: "+"total: "+pack.getTotal_sticker());
                    Log.e(TAG, "onResponse: "+"state: "+pack.getComp_acc_state());
                    Log.e(TAG, "onResponse: "+"status: "+pack.getComp_acc_status());
                    Log.e(TAG, "onResponse: "+"company: id: "+pack.getCompany().getId());
                    Log.e(TAG, "onResponse: "+"company: name: "+pack.getCompany().getName());
                    Log.e(TAG, "onResponse: "+"company: address: "+pack.getCompany().getAddress());
                    Log.e(TAG, "onResponse: "+"company: desc: "+pack.getCompany().getDescription());
                    Log.e(TAG, "onResponse: "+"company: state: "+pack.getCompany().getState());
                    Log.e(TAG, "onResponse: "+"company: status: "+pack.getCompany().getStatus());
                }
            }

            @Override
            public void onFailure(Call<PackUsedByCompanyDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
    public void testStickerByIdPack(String companyId,int id){
        Call<StickerIdByPack> call = apiClient.getApiInteface().GetStickerIdByPack(companyId,id);
        call.enqueue(new Callback<StickerIdByPack>() {
            @Override
            public void onResponse(Call<StickerIdByPack> call, Response<StickerIdByPack> response) {
                for (String pack:
                        response.body().getData().getPacks()
                     ) {
                    Log.e(TAG, "onResponse: "+"pack: "+pack );
                }
                for (StickerIdByPack.StickerId data:
                     response.body().getData().getSticker_ids()) {
                    Log.e(TAG, "onResponse: "+"stickerId: "+data.getId() );
                    Log.e(TAG, "onResponse: "+"stickerNumber: "+data.getNumber() );
                }
            }

            @Override
            public void onFailure(Call<StickerIdByPack> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
    public void testAllPack(){
        Call<PackDao> call = apiClient.getApiInteface().GetAllPack();
        call.enqueue(new Callback<PackDao>() {
            @Override
            public void onResponse(Call<PackDao> call, Response<PackDao> response) {
                for (PackDao.AllPackDao data:
                        response.body().getData()) {
                    Log.e(TAG, "onResponse: Id:"+data.getId() );
                    Log.e(TAG, "onResponse: TotalSticker:"+data.getTotal_sticker() );
                    Log.e(TAG, "onResponse: Name:"+data.getName() );
                    Log.e(TAG, "onResponse: Used:"+data.getUsed() );
                }
            }

            @Override
            public void onFailure(Call<PackDao> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
}
