package org.example.service;

import org.example.bean.Table;
import org.example.dao.TableDao;

import java.util.List;

public class TableService {
    private final TableDao tableDao = new TableDao();

    public List<Table> getAllTablesByRestaurantId(Long restaurantId) {
        return tableDao.getAllTablesByRestaurantId(restaurantId);
    }

    public boolean updateTableStatus(Long tableId, Byte status) {
        return tableDao.updateTableStatus(tableId, status);
    }


}
