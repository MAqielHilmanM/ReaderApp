package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class PackDao extends BaseDao<List<PackDao>>{
    private String id;
    private String name;
    private int used;
    private int total_sticker;

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
}
