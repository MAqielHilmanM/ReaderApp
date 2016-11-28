package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class StickerIdByPack extends BaseDao<StickerIdByPack.stickerIdbyPack> {
    public class stickerIdbyPack{

        private List<String> packs;
        private List<StickerId> sticker_ids;

        public List<StickerId> getSticker_ids() {
            return sticker_ids;
        }

        public List<String> getPacks() {
            return packs;
        }

        }
    public class StickerId{
        private String id;
        private int number;

        public StickerId(String id, int number) {
            this.id = id;
            this.number = number;
        }

        public String getId() {
            return id;
        }

        public int getNumber() {
            return number;
        }

    }
}
