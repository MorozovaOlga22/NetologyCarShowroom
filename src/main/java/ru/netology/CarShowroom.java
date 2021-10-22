package ru.netology;

import java.util.ArrayList;
import java.util.List;

public class CarShowroom {
    private final List<Car> cars = new ArrayList<>();
    private boolean notDeliveryExpected = false;

    public synchronized void receiveGoods(Car newCar, boolean notDeliveryExpected) {
        System.out.println("Производитель " + newCar.getCarBrand() + " выпустил 1 авто");
        cars.add(newCar);
        if (notDeliveryExpected) {
            System.out.println("Машины больше производиться не будут");
            this.notDeliveryExpected = true;
            notifyAll();
        } else {
            notify();
        }
    }

    public synchronized boolean sellCar() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
        while (cars.size() < 1) {
            if (notDeliveryExpected) {
                System.out.println(Thread.currentThread().getName() + " не смог купить машину");
                return false;
            }
            System.out.println("Машин нет");
            wait();
        }

        cars.remove(0);
        System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
        return true;
    }
}
