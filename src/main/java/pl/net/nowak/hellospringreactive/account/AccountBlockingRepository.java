package pl.net.nowak.hellospringreactive.account;

import org.springframework.data.mongodb.repository.MongoRepository;

interface AccountBlockingRepository extends MongoRepository<Account, String> {

}