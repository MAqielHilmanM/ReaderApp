package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class PackUsedByCompanyDao extends BaseDao<List<PackUsedByCompanyDao.packByCompany>> {
    public class packByCompany{

        private String id;
        private String name;
        private int used;
        private int total_sticker;
        private int comp_acc_status;
        private String comp_acc_state;
        private Company company;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getUsed() {
            return used;
        }

        public int getTotal_sticker() {
            return total_sticker;
        }

        public int getComp_acc_status() {
            return comp_acc_status;
        }

        public String getComp_acc_state() {
            return comp_acc_state;
        }

        public Company getCompany() {
            return company;
        }

    }
    public class Company{
        private String id;
        private String name;
        private String description;
        private String address;
        private int status;
        private String state;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getAddress() {
            return address;
        }

        public int getStatus() {
            return status;
        }

        public String getState() {
            return state;
        }
    }
}
