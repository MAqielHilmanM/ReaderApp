package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class InsertStickerPack extends BaseDao<String> {

    public static class PackReqeust{
        private String name;
        private List<ListSticker> list;

        public PackReqeust(String name, List<ListSticker> list) {
            this.name = name;
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public List<ListSticker> getList() {
            return list;
        }

        public static class ListSticker{
            private String id;
            private int number;

            public ListSticker(String id, int number) {
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
}
