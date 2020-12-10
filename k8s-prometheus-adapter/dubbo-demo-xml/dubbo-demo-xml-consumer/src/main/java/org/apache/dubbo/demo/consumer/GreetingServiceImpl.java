package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("greeting")
public class GreetingServiceImpl implements GreetingService {

    @Autowired
    private DemoService demoService;

    @GET
    @Path("hello")
    @Override
    public String hello() {
        return demoService.sayHello("Greeting");
    }
}
