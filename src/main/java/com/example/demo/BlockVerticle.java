package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockVerticle extends AbstractVerticle {

    public static Map<String,MessageData> dataMap=new ConcurrentHashMap<>();



  public void start() {


      EventBus();


  }




    public void EventBus(){


        EventBus eb = vertx.eventBus();
        MessageConsumer<String> consumer = eb.consumer("news.uk.sport");
        consumer.handler(message -> {
            String uuid=message.body();
            //异步执行耗时操作
            AsynMethod(uuid);
            //阻塞执行耗时操作
            // BlockMethod();

            System.out.println("BlockVerticle-I have received a message: " + message.body());
            message.reply("BlockVerticle-how interesting!");
        });

        consumer.completionHandler(res -> {
            if (res.succeeded()) {
                System.out.println("BlockVerticle-The handler registration has reached all nodes");
            } else {
                System.out.println("Registration failed!");
            }
        });
    }


    /**
     * 使用 executeBlocking 方法异步处理阻塞。
     * 当阻塞代码执行完毕的时候，handler将会以异步的方式被回调。
     */
    public void  AsynMethod(String uuid){
        long t=System.currentTimeMillis();
        System.out.println("blockingMethod() "+t);

        vertx.executeBlocking(future -> {
            // Call some blocking API that takes a significant amount of time to return

//            BlockMethod();
            String result = "block test";
            MessageData messageData=dataMap.get(uuid);
            messageData.setFuture(future);
//            future.complete(result);
        }, false,res -> {
            MessageData data=(MessageData) res.result();
            System.out.println("The result is: " + data);
            RoutingContext routingContext=data.getRoutingContext();
            routingContext.response().putHeader("content-type", "application/json")
                .end(data.getUuid());

        });
    }

    /**
     * 模拟阻塞方法
     */
    public void BlockMethod(){
        try {
//            Thread.sleep(new Random().nextInt(10000));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟阻塞方法
     */
    public void BlockMethod1(){
        try {
            EventBus eb = vertx.eventBus();
            MessageConsumer<String> consumer = eb.consumer("news.uk.sport3");
            consumer.handler(message -> {
                dataMap.entrySet().parallelStream().forEach(data->{
                    String key=data.getKey();
                    Object value=data.getValue();
                    System.out.println(key + "---------------"+ value);
                    Future future=(Future) value;
                    future.complete(message.body());
                    System.out.println("3333333333333---------------finish");



                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


} 