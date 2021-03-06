package gits.helpers.dao;

/**
 * Created by root on 11/24/16.
 */

public class AddDeleteDao extends BaseDao<Boolean> {
    public static class DeleteRequest{
        private String nama;
        private List list;

        public DeleteRequest(String nama, List list) {
            this.nama = nama;
            this.list = list;
        }


        public List getList() {
            return list;
        }

        public String getNama() {
            return nama;
        }

        public static class List{
            private String name;
            private String description;

            public List(String name, String description) {
                this.name = name;
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }
        }
    }
}
