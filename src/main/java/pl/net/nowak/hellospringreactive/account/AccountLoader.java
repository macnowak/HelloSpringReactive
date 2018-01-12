package pl.net.nowak.hellospringreactive.account;

import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
class AccountLoader {

	private final Logger log = LoggerFactory.getLogger("account-loader");

	private final AccountReactiveRepository accountReactiveRepository;

	public AccountLoader(AccountReactiveRepository accountReactiveRepository) {
		this.accountReactiveRepository = accountReactiveRepository;
	}

	void load(int size, int speed) {
		log.info("Starting to load {} accounts with speed {}", size, speed);

		Flux.fromStream(
			IntStream.range(0, size)
				.mapToObj(id -> new Account(String.valueOf(id), "name_" + id))
				.map(accountReactiveRepository::save)
		).subscribe(mono -> log.info("account created {} ", mono.block()));

		log.info("Repo contains {} accounts", accountReactiveRepository.count().block());

	}
}
