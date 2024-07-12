/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.classes;

import ec.edu.espol.tools.LinkedList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Alejandro Diez
 */
public class Filtro {
    public static LinkedList<Car> filterAndSortCars(
            LinkedList<Car> cars,
            TipoVehiculo tipo,
            String brand,
            String model,
            Integer kilometersFrom,
            Integer kilometersTo,
            Integer priceFrom,
            Integer priceTo,
            String mantenimientoOption,
            boolean includeDesperfectos,
            Comparator<Car> comparator
    ) {
        PriorityQueue<Car> priorityQueue = new PriorityQueue<>(comparator);
        LocalDate now = LocalDate.now();

        for (Car car : cars) {
            boolean add = true;
            if(tipo != null && car.getTipo().compareTo(tipo) != 0) add = false;
            if(brand != null && car.getBrand().compareTo(brand) != 0) add = false;
            if(model != null && car.getModel().compareTo(model) != 0) add = false;
            if(kilometersFrom != null && car.getKilometers() < kilometersFrom) add = false;
            if(kilometersTo != null && car.getKilometers() > kilometersTo) add = false;
            if(priceFrom != null && car.getPrice() < priceFrom)add = false;
            if(priceTo != null && car.getPrice() > priceTo) add = false;
            if(mantenimientoOption != null){
                if(mantenimientoOption.compareTo("Menos de 6 meses") == 0)
                    if(car.getMantenimiento().isBefore(now.minusMonths(6))) add = false;
                else if(mantenimientoOption.compareTo("Menos de 1 año") == 0)
                    if(car.getMantenimiento().isBefore(now.minusMonths(12))) add = false;
            }
            if(includeDesperfectos == true && !car.getDesperfectos().isBlank()) add = false;
            if(add == true) priorityQueue.offer(car);
        }

        LinkedList<Car> filteredCars = new LinkedList<>();
        while (!priorityQueue.isEmpty()) {
            filteredCars.addLast(priorityQueue.poll());
        }

        return filteredCars;
    }
}

