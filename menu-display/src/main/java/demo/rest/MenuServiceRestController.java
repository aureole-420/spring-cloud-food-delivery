package demo.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.domain.Menu;
import demo.domain.MenuRepository;
import demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class MenuServiceRestController {

    private MenuService menuService;

    @Autowired
    public MenuServiceRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value="/menu/upload", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.ACCEPTED)
    public void upload(@RequestBody List<Menu> menus) {
        menuService.saveMenu(menus);
    }


    @RequestMapping(value = "/menu/getAll", method = RequestMethod.GET)
    public List<Menu> getMenus() {
        return menuService.findAll();
    }

    @RequestMapping(value = "/purge", method = RequestMethod.DELETE)
    public void delete() {
        menuService.deleteAll();

    }
    @RequestMapping(value = "/")
    public String home(){
        return "forward:/menu/getAll";
    }

    // search by menu name.
    @RequestMapping(value = "/menu/search", method = RequestMethod.GET)
    public Menu findMenu(@RequestParam(value="menuName") String menuName) {
        return menuService.findByMenuName(menuName);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuRepository repository;

    @RequestMapping(value = "/init")
    public Menu initializeMenus() {
        InputStream input = this.getClass().getResourceAsStream("/menuInfo.json");
        try {
            Menu m =  objectMapper.readValue(input, Menu.class);
            System.out.println("<InitMenu>" + m.getMenuItems().get(0).getItemName());
            System.out.println("<InitMenu>" + m.getMenuItems().get(0).getItemId());
            System.out.println("<InitMenu>" + m.getMenuItems().get(0).getItemPrice());

            repository.save(m);
            return m;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @RequestMapping(value="/uploadmenuitem", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void upload(@RequestBody  Menu menu) {
        repository.save(menu);
    }

}
