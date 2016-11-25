package gits.helpers;

import java.util.List;

import gits.helpers.dao.AddDeleteDao;
import gits.helpers.dao.InsertStickerPack;
import gits.helpers.dao.PackDao;
import gits.helpers.dao.PackStickerDao;
import gits.helpers.dao.PackUsedByCompanyDao;
import gits.helpers.dao.StickerIdByPack;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by root on 11/24/16.
 */

public interface ApiInterface {
    @POST("/stroomhead/sticker/batch")
    Call<InsertStickerPack> InsertStickerPack(
        @Query("dryrun") int dryrun,
        @Body InsertStickerPack.PackReqeust map
    );
    @POST("/stroomhead/sticker/pack/{packId}")
    Call<AddDeleteDao> AddStickerToAPack(
            @Path("packId") String id,
            @Body InsertStickerPack.PackReqeust.ListSticker map
            );
    @DELETE("/stroomhead/sticker/pack/{packId}/delete/new-id")
    Call<AddDeleteDao> DeleteStickerFromPack(
            @Path("packId") String packId,
            @Body AddDeleteDao.DeleteRequest map
    );

    @HTTP(method = "DELETE" ,path = "/stroomhead/sticker/pack/{packId}/delete/new-id",hasBody = true)
    Call<AddDeleteDao> HttpDeleteStickerFromPack(
            @Path("packId") String packId,
            @Body AddDeleteDao.DeleteRequest map
    );
        @POST("/stroomhead/sticker/pack/{packId}/delete/new-id")
        Call<AddDeleteDao> PostDeleteStickerFromPack(
                @Path("packId") String packId,
                @Body AddDeleteDao.DeleteRequest map
        );

    @GET("/{company_id}/sticker/batch")
    Call<StickerIdByPack> GetStickerIdByPack(
            @Path("company_id") String company_id,
            @Query("druyun") int druyun
    );

    @GET("/stroomhead/sticker/availablepacks")
    Call<PackDao> GetAvailablePack();

    @GET("/stroomhead/sticker/pack/all")
    Call<PackDao> GetAllPack();

    @GET("/stroomhead/sticker/pack/usedbycompanies")
    Call<PackUsedByCompanyDao> GetPackByCompanyDao();

    @GET("/stroomhead/sticker/pack/{stickerId}")
    Call<PackStickerDao> GetPackSticker(
            @Path("stickerId") String StickerId
    );
}
