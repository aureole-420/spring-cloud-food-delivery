package demo.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends MongoRepository<Menu, String>{

    Menu findByMenuName(@Param("menuName") String menuName);

}
