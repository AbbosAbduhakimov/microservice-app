package uz.abbos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.List;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("LIST INSTANCES");
        List<String> list = discoveryClient.getServices();

        list.forEach(item -> {
            System.out.println("Service: " + item);
            List<ServiceInstance> instances = this.discoveryClient.getInstances(item);
            instances.forEach(instance -> {
                System.out.println("URI: " + instance.getUri());
                System.out.println("HOST: " + instance.getHost());
                System.out.println("PORT: " + instance.getPort());
                System.out.println("INSTANCE ID: " + instance.getInstanceId());
                System.out.println("SERVICE ID: " + instance.getServiceId());
            });
        });
    }

}
