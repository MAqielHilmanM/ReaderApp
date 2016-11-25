package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class InsertStickerPack extends BaseDao<String> {

    public static class PackReqeust{
        private String name;
        private List<ListSticker> list;

        public String getName() {
            return name;
        }

        public List<ListSticker> getList() {
            return list;
        }

        public static class ListSticker{
            private String id;
            private String number;

            public String getId() {
                return id;
            }

            public String getNumber() {
                return number;
            }
        }
    }
}
