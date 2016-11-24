package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class StickerIdByPack extends BaseDao<StickerIdByPack> {
    private List<String> packs;
    private StickerId sticker_ids;

    public StickerId getSticker_ids() {
        return sticker_ids;
    }

    public List<String> getPacks() {
        return packs;
    }

    class StickerId{
        private String Id;
        private int number;

        public String getId() {
            return Id;
        }

        public int getNumber() {
            return number;
        }
    }
}
