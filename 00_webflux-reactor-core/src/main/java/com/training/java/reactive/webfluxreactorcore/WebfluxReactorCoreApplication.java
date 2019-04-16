package com.training.java.reactive.webfluxreactorcore;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootApplication
public class WebfluxReactorCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxReactorCoreApplication.class, args);
        // Log stream data flow
        System.out.println("--- Stream data flow ---");
        streamDataFlow();
        System.out.println("--- Stream data flow with subscriber ---");
        // With subscriber
        streamWithSubscriber();
        System.out.println("--- Stream data flow with subscriber and backpressure ---");
        // Backpressure
        streamWithBackpressure();
        System.out.println("--- Stream concurrent ---");
        concurrent();
    }


    /**
     * onSubscribe() – This is called when we subscribe to our stream
     * request(unbounded) – When we call subscribe, behind the scenes we are creating a Subscription. This subscription
     *      requests elements from the stream. In this case, it defaults to unbounded, meaning it requests every single
     *      element available
     * onNext() – This is called on every single element
     * onComplete() – This is called last, after receiving the last element. There’s actually a onError() as well,
     *      which would be called if there is an exception, but in this case, there isn’t
     */
    public static void streamDataFlow() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
            .log()
            .subscribe(elements::add);

        Assertions.assertThat(elements).containsExactly(1, 2, 3, 4);
    }

    public static void streamWithSubscriber(){

        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
            .log()
            .subscribe(new Subscriber<Integer>() {
                @Override
                public void onSubscribe(Subscription s) {
                    s.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(Integer integer) {
                    elements.add(integer);
                }

                @Override
                public void onError(Throwable t) {}

                @Override
                public void onComplete() {}
            });
    }

    /**
     * Backpressure is when a downstream can tell an upstream to send it fewer data in order to prevent it from being overwhelmed.
     */
    public static void streamWithBackpressure(){

        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
            .log()
            .subscribe(new Subscriber<Integer>() {
                private Subscription s;
                int onNextAmount;

                @Override
                public void onSubscribe(Subscription s) {
                    this.s = s;
                    // Le decimos que envie solo dos
                    s.request(2);
                }

                @Override
                public void onNext(Integer integer) {
                    elements.add(integer);
                    onNextAmount++;
                    if (onNextAmount % 2 == 0) {
                        s.request(2);
                    }
                }

                @Override
                public void onError(Throwable t) {}

                @Override
                public void onComplete() {}
            });
    }

    /**
     * The Parallel scheduler will cause our subscription to be run on a different thread
     */
    public static void concurrent(){
        List<Integer> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
            .log()
            .map(i -> i * 2)
            .subscribeOn(Schedulers.parallel())
            .subscribe(elements::add);
    }
}


