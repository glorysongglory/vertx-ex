package com.example.demo;

import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by IntelliJ IDEA.
 * User: sodbvi
 * Date: 2019/5/7
 * Time: 15:34
 **/
public class MessageData {
    String uuid;
    RoutingContext routingContext;
    Future<Object> future;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public Future<Object> getFuture() {
        return future;
    }

    public void setFuture(Future<Object> future) {
        this.future = future;
    }

    public MessageData(String uuid, RoutingContext routingContext, Future<Object> future) {
        this.uuid = uuid;
        this.routingContext = routingContext;
        this.future = future;
    }

}
