package com.training.java.reactive.webflux_numeros;

import java.time.Duration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author <a href="changeme@ext.inditex.com">Jose Gonzalez</a>
 */
@RestController
public class NumerosController {

    /*
        - text/event-stream es el Content-Type necesario para poder transmitir el resultado como un flujo de elementos.
        - Flux es una clase que tiene las tareas del Publisher, es decir, se encargará de informar al Susriber los cambios.
     */
    @GetMapping(path = "numeros", produces = "text/event-stream")
    public Flux<Integer> all () {
        return Flux.range(1, 30)
            .delayElements(Duration.ofSeconds(1)).repeat().map(n -> n);
        /*
            Con el código anterior, crearemos una secuencia de elementos de tipo Integer. Esta secuencia serán números
            del 1 al 30 que se transmitirán de forma asíncrona con lapsos de tiempo de 1 segundo entre cada uno de ellos.
         */
    }
}