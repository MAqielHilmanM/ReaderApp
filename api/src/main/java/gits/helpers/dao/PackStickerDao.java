package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class PackStickerDao extends BaseDao<List<PackStickerDao.PackSticker>> {
    public class PackSticker{
        private int number;
        private String id;
        private String name;

        public int getNumber() {
            return number;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }
}
