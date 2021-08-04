package com.antocecere77.redisspring;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class RedisSpringApplicationTests {

	@Autowired
	private ReactiveStringRedisTemplate template;

	@Autowired
	private RedissonReactiveClient client;

	@RepeatedTest(3)
	void springDataRedisTest() {
		ReactiveValueOperations<String, String> valueOperations = this.template.opsForValue();
		Mono<Void> mono = Flux.range(1, 500_000)
				.flatMap(i -> valueOperations.increment("user:1:visit")) //inc
				.then();

		long before = System.currentTimeMillis();

		StepVerifier.create(mono)
				.verifyComplete();

		long after = System.currentTimeMillis();

		System.out.println(after - before + " ms");
	}

	@RepeatedTest(3)
	void redissonTest() {
		RAtomicLongReactive atomicLong = this.client.getAtomicLong("user:2:visit");
		Mono<Void> mono = Flux.range(1, 500_000)
				.flatMap(i -> atomicLong.incrementAndGet()) //inc
				.then();

		long before = System.currentTimeMillis();

		StepVerifier.create(mono)
				.verifyComplete();

		long after = System.currentTimeMillis();

		System.out.println(after - before + " ms");
	}


}
