package pl.net.nowak.hellospringreactive.account;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

interface AccountReactiveRepository extends ReactiveCrudRepository<Account, String> {

	@Query("{ id: { $exists: true }}")
	Flux<Account> findAllPaged(final Pageable page);

}