package gits.helpers.dao;

import java.util.List;

/**
 * Created by root on 11/24/16.
 */

public class BaseDao<T> {
    private String status;
    private String message;
    private T data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
