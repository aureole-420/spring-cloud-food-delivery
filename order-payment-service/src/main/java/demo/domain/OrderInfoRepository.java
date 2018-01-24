package demo.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderInfoRepository extends MongoRepository<OrderInfo, String>{

}
