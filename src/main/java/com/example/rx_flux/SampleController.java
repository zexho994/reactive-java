package com.example.rx_flux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * @author Zexho
 * @date 2021/8/23 8:53 上午
 */
@RestController
@RequestMapping("/api")
public class SampleController {

    public void createFlux() {
        // 第一种创建方式
        Flux<String> flux = Flux.just("1", "2", "3");

        // 第二种创建方式
        List<String> list = Arrays.asList("1", "2", "3");
        Flux<String> flux2 = Flux.fromIterable(list);

        // 第三种创建方式
        Flux<String> flux3 = Flux.empty();

        // 第四种创建方式
        Flux<Integer> flux4 = Flux.range(0, 3);
    }
}
