package demo.service;

import demo.domain.Menu;
import demo.domain.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private MenuRepository repository;

    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Menu> saveMenu(List<Menu> menus) {
        return repository.save(menus);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();

    }

    @Override
    public Menu findByMenuName(String menuName) {
        return repository.findByMenuName(menuName);
    }

    @Override
    public List<Menu> findAll() {
        return repository.findAll();
    }


}
