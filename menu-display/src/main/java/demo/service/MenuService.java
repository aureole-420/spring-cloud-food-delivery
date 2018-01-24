package demo.service;


import demo.domain.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> saveMenu(List<Menu> menus);

    void deleteAll();

    Menu findByMenuName(String menuName);

    List<Menu> findAll();
}
