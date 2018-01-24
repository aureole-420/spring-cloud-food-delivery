package demo.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentInfoRepository extends MongoRepository<PaymentInfo, String> {

}
