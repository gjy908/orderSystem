package org.example.service;

import org.example.bean.Menu;
import org.example.dao.MenuDao;

import java.util.List;

public class MenuService {

    private final MenuDao menuDao = new MenuDao();
    public List<Menu> getMenuListByRestaurantId(Long restaurantId) {
        return menuDao.getAllMenusByRestaurantId(restaurantId);
    }
    public boolean updateMenuStatus(Long menuId, Byte status) {
        return menuDao.updateMenuStatus(menuId, status);
    }





}
