package com.example.rx_flux;

import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Zexho
 * @date 2021/8/23 8:53 上午
 */
@RestController
@RequestMapping("/api")
public class SampleController {

    public static void main(String[] args) {
        SampleController sampleController = new SampleController();
        sampleController.subscribe4();
    }

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

    public void subscribe() {
        Flux<String> flux = Flux.just(getVal(), getVal(), getVal(), getVal(), getVal());
        flux.subscribe(System.out::println);
    }

    private String getVal() {
        try {
            System.out.println("执行io操作...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return UUID.randomUUID().toString();
    }


    public void subscribe2() {
        Flux<Integer> flux = Flux.range(1, 5).map(i -> {
            System.out.println("range -> " + i);
            if (i == 3) {
                throw new RuntimeException("get 3 error");
            }
            return i;
        });
        flux.subscribe(
                System.out::println,  //成功的情况
                err -> System.out.println("Err:" + err)  //异常的情况
        );
    }

    public void subscribe3() {
        Flux<Integer> flux2 = Flux.range(1, 5).map(i -> {
            System.out.println("range -> " + i);
            return i;
        });
        flux2.subscribe(
                System.out::println,  //成功的情况
                err -> System.out.println("Err:" + err),  //异常的情况
                () -> System.out.println("done")  //全部成功后进行的任务,异常和此情况都属于终止信号，只能出现一个
        );
    }

    /**
     * 推荐扩展{@link BaseSubscriber}类实现
     *
     * @param <T>
     */
    class Subscriber<T> extends BaseSubscriber<T> {

        /**
         * 至少要重写这两个方法
         */
        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            System.out.println("hook on subscribe");
            request(1);
        }

        @Override
        protected void hookOnNext(T value) {
            System.out.println("hook on next -> " + value);
            request(1);
        }

        @Override
        protected void hookOnComplete() {
            System.out.println("hook on complete");
        }

    }

    public void subscribe4() {
        Flux<Integer> flux = Flux.range(1, 5);
        flux.subscribe(new Subscriber<>());
    }

}
