package JAVA_MP;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Bus {
    private int busNo;
    private boolean ac;
    private int capacity;
    private int availableSeats;

    Bus(int no, boolean ac, int cap) {
        this.busNo = no;
        this.ac = ac;
        this.capacity = cap;
        this.availableSeats = cap;
    }

    public int getBusNo() {
        return busNo;
    }

    public boolean isAc() {
        return ac;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int numSeats) {
        availableSeats -= numSeats;
    }
}

class Booking {
    String passengerName;
    int busNo;
    Date date;
    int numTickets;

    Booking(String passengerName, int busNo, Date date, int numTickets) {
        this.passengerName = passengerName;
        this.busNo = busNo;
        this.date = date;
        this.numTickets = numTickets;
    }
}

class BusDemo {
    private ArrayList<Bus> buses;
    private ArrayList<Booking> bookings;
    private String busInfoFileName = "businfo.txt";

    public BusDemo() {
        buses = new ArrayList<Bus>();
        bookings = new ArrayList<Booking>();
        loadBusInfo();

        // Create or load bus information from a file
        if (buses.isEmpty()) {
            buses.add(new Bus(1, true, 42));
            buses.add(new Bus(2, false, 50));
            buses.add(new Bus(3, true, 48));
            buses.add(new Bus(4, false, 36));
        }
    }

    public synchronized boolean bookBus(String passengerName, int busNo, Date date, int numTickets) {
        Bus selectedBus = null;

        // Find the selected bus
        for (Bus bus : buses) {
            if (bus.getBusNo() == busNo) {
                selectedBus = bus;
                break;
            }
        }

        if (selectedBus == null) {
            return false; // Bus not found
        }

        // Check if enough seats are available
        if (selectedBus.getAvailableSeats() >= numTickets) {
            // Book the seats
            selectedBus.bookSeats(numTickets);
            Booking booking = new Booking(passengerName, busNo, date, numTickets);
            bookings.add(booking);
            saveBusInfo(); // Save updated bus information
            return true;
        } else {
            return false; // Seats not available
        }
    }

    public String getBusInfo() {
        StringBuilder info = new StringBuilder();
        for (Bus bus : buses) {
            info.append("Bus No: ").append(bus.getBusNo()).append(" AC: ").append(bus.isAc())
                .append(" Total Capacity: ").append(bus.getCapacity())
                .append(" Available Seats: ").append(bus.getAvailableSeats()).append("\n");
        }
        return info.toString();
    }

    private void loadBusInfo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(busInfoFileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int busNo = Integer.parseInt(parts[0]);
                boolean ac = Boolean.parseBoolean(parts[1]);
                int capacity = Integer.parseInt(parts[2]);
                int availableSeats = Integer.parseInt(parts[3]);
                buses.add(new Bus(busNo, ac, capacity));
                buses.get(buses.size() - 1).bookSeats(capacity - availableSeats);
            }
            reader.close();
        } catch (IOException e) {
            // File not found, so it's a new run
        }
    }

    private void saveBusInfo() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(busInfoFileName));
            for (Bus bus : buses) {
                writer.write(bus.getBusNo() + "," + bus.isAc() + "," + bus.getCapacity() + "," + bus.getAvailableSeats());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class BusReservationGUI extends JFrame {

    private JTextArea textArea;
    private JButton bookButton;
    private BusDemo busDemo;

    public BusReservationGUI(final BusDemo busDemo) {
        this.busDemo = busDemo;

        setTitle("Bus Reservation System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        bookButton = new JButton("Book a Bus");

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bookButton, BorderLayout.SOUTH);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookingPage();
            }
        });
    }

    public void showBookingPage() {
        String inputName = JOptionPane.showInputDialog("Enter passenger name:");
        if (inputName != null && !inputName.isEmpty()) {
            String inputBusNo = JOptionPane.showInputDialog("Enter bus number:");
            if (inputBusNo != null && !inputBusNo.isEmpty()) {
                try {
                    int busNo = Integer.parseInt(inputBusNo);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String inputDate = JOptionPane.showInputDialog("Enter date (dd-MM-yyyy):");
                    if (inputDate != null && !inputDate.isEmpty()) {
                        Date date = null;
                        try {
                            date = dateFormat.parse(inputDate);
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid date format.");
                            return;
                        }
                        String inputNumTickets = JOptionPane.showInputDialog("Enter number of tickets:");
                        if (inputNumTickets != null && !inputNumTickets.isEmpty()) {
                            try {
                                int numTickets = Integer.parseInt(inputNumTickets);
                                boolean bookingResult = busDemo.bookBus(inputName, busNo, date, numTickets);
                                if (bookingResult) {
                                    JOptionPane.showMessageDialog(null, "Booking confirmed!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Seats not available.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid number of tickets.");
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid bus number.");
                }
            }
        }
    }

    public void updateTextArea() {
        textArea.setText(busDemo.getBusInfo());
    }

    public static void main(String[] args) {
        final BusDemo busDemo = new BusDemo();
        final BusReservationGUI gui = new BusReservationGUI(busDemo);

        Runnable bookingTask = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000); // Sleep for some time before the next update
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                gui.updateTextArea();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread bookingThread = new Thread(bookingTask);
        bookingThread.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
                gui.updateTextArea();
            }
        });
    }
}

