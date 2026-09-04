package com.ridefleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RideFleetApplication {
    public static void main(String[] args) {
        SpringApplication.run(RideFleetApplication.class, args);
        System.out.println("\n========================================================");
        System.out.println("   RIDEFLEET - VEHICLE RENTAL & FLEET MANAGEMENT SYSTEM  ");
        System.out.println("   Status: Operational & Listening on Port 8080        ");
        System.out.println("========================================================\n");
    }
}
