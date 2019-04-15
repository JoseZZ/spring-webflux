package com.training.java.reactive.webflux_multiplicar;

import com.training.java.reactive.webflux_multiplicar.subscriber.Subscriber;
import java.time.Duration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author <a href="changeme@ext.inditex.com">Jose Gonzalez</a>
 * Si llamamos a http://localhost:8080/numeros veremos por consola los mensajes de los dos subscriptores
 * Los subscriptores van por separado, uno no bloquea al otro, la programacion reactiva es asincrona
 */
@RestController
public class NumerosController {

    @GetMapping(path = "numeros", produces = "text/event-stream")
    public Flux<Integer> all () {
        Flux<Integer> flux = Flux.range(1,30)
            .delayElements(Duration.ofSeconds(1));

        // Indicamos los subscriptores a los que tiene que notificar cuando haya nuevos elementos
        flux.subscribe(System.out::println); // suscriptor 1
        flux.subscribe(Subscriber::multiplicar); // suscriptor 2
        return flux; // retornamos el elemento. Sería como el suscriptor 3
    }
}