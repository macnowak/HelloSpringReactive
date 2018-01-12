package pl.net.nowak.hellospringreactive.account;

import java.time.Duration;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("account")
class AccountEndpoint {

	private static final int SOME_NETWORK_LATENCY_IN_MS = 100;

	private final AccountReactiveRepository accountReactiveRepository;

	private final AccountBlockingRepository accountBlockingRepository;

	private final AccountLoader accountLoader;

	public AccountEndpoint(final AccountReactiveRepository quoteMongoReactiveRepository, AccountBlockingRepository accountBlockingRepository, AccountLoader accountLoader) {
		this.accountReactiveRepository = quoteMongoReactiveRepository;
		this.accountBlockingRepository = accountBlockingRepository;
		this.accountLoader = accountLoader;
	}

	@GetMapping("/reactive")
	public Flux<Account> getAccountFlux() {
		// If you want to use a shorter version of the Flux, use take(100) at the end of the statement below
		return accountReactiveRepository.findAll().delayElements(Duration.ofMillis(SOME_NETWORK_LATENCY_IN_MS));
	}

	@GetMapping("/reactive/paged")
	public Flux<Account> getAccountFlux(final @RequestParam(name = "page") int page,
										final @RequestParam(name = "size") int size) {
		return accountReactiveRepository.findAllPaged(PageRequest.of(page, size))
			.delayElements(Duration.ofMillis(SOME_NETWORK_LATENCY_IN_MS));
	}

	@GetMapping("/blocking")
	public Iterable<Account> getAccountsBlocking() throws Exception {
		Thread.sleep(SOME_NETWORK_LATENCY_IN_MS * accountBlockingRepository.count());
		return accountBlockingRepository.findAll();
	}

	@GetMapping("/blocking/paged")
	public Iterable<Account> getAccountsBlocking(final @RequestParam(name = "page") int page,
												 final @RequestParam(name = "size") int size) throws Exception {
		Thread.sleep(SOME_NETWORK_LATENCY_IN_MS * size);
		return accountBlockingRepository.findAll(PageRequest.of(page, size));
	}

	@PostMapping("/load")
	public String load(final @RequestParam(name = "size") int size) {
		accountLoader.load(size, 0);
		return "OK";
	}

}