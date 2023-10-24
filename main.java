//Rent a Car
//Get Confirmation Slip
//Using OPPS Onlu

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

}

class Customer {

    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car Returned Successfully. ");
        } else {
            System.out.println("Car Was Not Rented. ");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("*****Car Rental System*****");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.print("Enter your choices: ");

            int choice = sc.nextInt();
            sc.nextLine();// consume newline

            if (choice == 1) {
                System.out.println("\n***Rent a car***\n");
                System.out.print("Enter your name : ");
                String customername = sc.nextLine();

                System.out.println("**Available Cars **");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\n Enter your Car Id you want to rent :");
                String carId = sc.nextLine();

                System.out.println("Enter the no. of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();// consume newLine

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customername);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n***Rental Infomation ***\n");
                    System.out.println("Customer Id: " + newCustomer.getCustomerId());
                    System.out.println("Customer name: " + newCustomer.getName());
                    System.out.println("car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.println("Total Price: Rs." + totalPrice);

                    System.out.print("\nConfirm rental(Y/N): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car rented successfully.");
                    } else {
                        System.out.println("\n Rental canceled");
                    }
                } else {
                    System.out.println("Invaild car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n**Return Car**\n");
                System.out.println("Enter the car ID you want to return");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by: " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or infomation is invalid");
                    }
                } else {
                    System.out.println("Invaild car ID or car was not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invaild choice. Please try again");
            }
        }
        System.out.println("Thank you for using the Car Rental System.");
    }
}

public class main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Fortuner", 5000);// baseprice is for per day
        Car car2 = new Car("C002", "Honda", "Acoord", 2500);
        Car car3 = new Car("C003", "Mahindra", "thar", 3500);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}
