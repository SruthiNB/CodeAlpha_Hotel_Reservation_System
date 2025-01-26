import java.util.Scanner;
import java.util.ArrayList;

class Room {
    String type;
    double price;
    boolean isAvailable;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }
    public void bookRoom() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Room booked successfully!");
        } else {
            System.out.println("Sorry, the room is already booked.");
        }
    }

    public void checkAvailability() {
        if (isAvailable) {
            System.out.println(type + " - Available - Price: $" + price);
        } else {
            System.out.println(type + " - Not Available");
        }
    }
}

class Reservation {
    String customerName;
    Room room;
    boolean isPaid;

    public Reservation(String customerName, Room room) {
        this.customerName = customerName;
        this.room = room;
        this.isPaid = false;
    }

    public void processPayment(double amount) {
        if (amount >= room.price) {
            isPaid = true;
            System.out.println("Payment of $" + amount + " successful. Reservation confirmed.");
        } else {
            System.out.println("Insufficient payment. Please pay the full amount.");
        }
    }

    public void displayDetails() {
        System.out.println("Reservation Details for " + customerName);
        System.out.println("Room Type: " + room.type);
        System.out.println("Price: $" + room.price);
        System.out.println("Payment Status: " + (isPaid ? "Paid" : "Not Paid"));
    }
}

class Hotel {
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();

    public void addRoom(String type, double price) {
        rooms.add(new Room(type, price));
    }

    public void searchAvailableRooms() {
        System.out.println("Available rooms:");
        boolean found = false;
        for (Room room : rooms) {
            if (room.isAvailable) {
                room.checkAvailability();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms.");
        }
    }

    public Reservation makeReservation(String customerName, Room room) {
        if (room.isAvailable) {
            room.bookRoom();
            Reservation reservation = new Reservation(customerName, room);
            reservations.add(reservation);
            return reservation;
        } else {
            System.out.println("Room is not available.");
            return null;
        }
    }

    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations made yet.");
        } else {
            for (Reservation res : reservations) {
                res.displayDetails();
            }
        }
    }
}

public class HotelReservationSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        hotel.addRoom("Standard", 100);
        hotel.addRoom("Deluxe", 200);
        hotel.addRoom("Suite", 300);

        int choice;

        do {

            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    hotel.searchAvailableRooms();
                    break;

                case 2:

                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    hotel.searchAvailableRooms();
                    System.out.print("Enter room type to book (Standard/Deluxe/Suite): ");
                    String roomType = scanner.nextLine();
                    Room selectedRoom = null;

                    for (Room room : hotel.rooms) {
                        if (room.type.equalsIgnoreCase(roomType) && room.isAvailable) {
                            selectedRoom = room;
                            break;
                        }
                    }

                    if (selectedRoom != null) {
                        Reservation reservation = hotel.makeReservation(name, selectedRoom);
                        if (reservation != null) {
                            System.out.print("Enter payment amount: $");
                            double payment = scanner.nextDouble();
                            reservation.processPayment(payment);
                        }
                    } else {
                        System.out.println("Room not available.");
                    }
                    break;

                case 3:

                    hotel.displayAllReservations();
                    break;

                case 4:

                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
