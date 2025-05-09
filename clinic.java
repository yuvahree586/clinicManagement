package clinicalsys;

import java.util.*;

public class clinic {

    static class Patient {
        String patientId;
        String name;

        Patient(String patientId, String name) {
            this.patientId = patientId;
            this.name = name;
        }
    }

    static class Slot {
        int slotId;
        String time;
        boolean isBooked;

        Slot(int slotId, String time) {
            this.slotId = slotId;
            this.time = time;
            this.isBooked = false;
        }

        @Override
        public String toString() {
            return "Slot ID: " + slotId + ", Time: " + time + ", Booked: " + (isBooked ? "Yes" : "No");
        }
    }

    static class Doctor {
        String doctorId;
        String name;
        List<Slot> slots;

        Doctor(String doctorId, String name, List<Slot> slots) {
            this.doctorId = doctorId;
            this.name = name;
            this.slots = slots;
        }

        void showSlots() {
            for (Slot slot : slots) {
                System.out.println(slot);
            }
        }

        Slot getAvailableSlotById(int slotId) {
            for (Slot slot : slots) {
                if (slot.slotId == slotId && !slot.isBooked) {
                    return slot;
                }
            }
            return null;
        }
    }

    static class Appointment {
        String appointmentId;
        String patientId;
        String doctorId;
        String time;

        Appointment(String appointmentId, String patientId, String doctorId, String time) {
            this.appointmentId = appointmentId;
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.time = time;
        }
    }

    static Patient[] patients = new Patient[10];
    static Appointment[] appointments = new Appointment[20];
    static int patientCount = 0;
    static int appointmentCount = 0;

    static Doctor[] doctors = {
        new Doctor("D101", "Dr. Smith", Arrays.asList(
            new Slot(1, "10:00 AM"),
            new Slot(2, "11:00 AM")
        )),
        new Doctor("D102", "Dr. Alice", Arrays.asList(
            new Slot(3, "12:00 PM"),
            new Slot(4, "01:00 PM")
        )),
        new Doctor("D103", "Dr. Anand", Arrays.asList(
            new Slot(5, "06:00 PM"),
            new Slot(6, "08:00 PM")
        )),
        new Doctor("D104", "Dr. Priya", Arrays.asList(
            new Slot(7, "07:00 PM"),
            new Slot(8, "08:00 PM")
        ))
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Clinic Management System");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Enter Patient Name: ");
        String patientName = scanner.nextLine();

        Patient patient = findPatientById(patientId);
        if (patient == null) {
            patient = new Patient(patientId, patientName);
            patients[patientCount++] = patient;
            System.out.println("New patient registered: " + patientName);
        } else {
            System.out.println("Welcome back, " + patientName);
        }

        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View Appointments");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewDoctors();
                    break;
                case 2:
                    bookAppointment(patientId, scanner);
                    break;
                case 3:
                    viewAppointments(patientId);
                    break;
                case 4:
                    System.out.println("Exiting.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    private static void viewDoctors() {
        for (Doctor doctor : doctors) {
            System.out.println("\nDoctor ID: " + doctor.doctorId + " - " + doctor.name);
            doctor.showSlots();
        }
    }

    private static void bookAppointment(String patientId, Scanner scanner) {
        System.out.print("Enter Doctor ID: ");
        String docId = scanner.nextLine();
        Doctor doctor = findDoctorById(docId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        doctor.showSlots();
        System.out.print("Enter Slot ID to book: ");
        int slotId = scanner.nextInt();
        scanner.nextLine();

        Slot chosenSlot = doctor.getAvailableSlotById(slotId);
        if (chosenSlot == null) {
            System.out.println("Slot not available.");
            return;
        }

        chosenSlot.isBooked = true;
        String appointmentId = "A" + (appointmentCount + 1);
        appointments[appointmentCount++] = new Appointment(appointmentId, patientId, docId, chosenSlot.time);
        System.out.println("Appointment booked! ID: " + appointmentId);
    }

    private static void viewAppointments(String patientId) {
        boolean found = false;
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].patientId.equals(patientId)) {
                System.out.println("Appointment ID: " + appointments[i].appointmentId +
                        ", Doctor: " + appointments[i].doctorId +
                        ", Time: " + appointments[i].time);
                found = true;
            }
        }
        if (!found) System.out.println("No appointments found.");
    }

    private static Patient findPatientById(String id) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].patientId.equals(id)) return patients[i];
        }
        return null;
    }

    private static Doctor findDoctorById(String id) {
        for (Doctor d : doctors) {
            if (d.doctorId.equals(id)) return d;
        }
        return null;
    }
}





