# Bus Reservation System

This repository contains a Java-based Bus Reservation System with a graphical user interface (GUI). The system allows users to view bus information, book tickets, and save booking details.

## **Features**

- **Bus Information Display**: View details of available buses including bus number, AC availability, total capacity, and available seats.
- **Ticket Booking**: Book tickets by selecting a bus, entering the date, and specifying the number of tickets.
- **Data Persistence**: Bus and booking information is saved and loaded from a file (`businfo.txt`), allowing data to persist across sessions.
- **Graphical User Interface (GUI)**: User-friendly interface built with Java Swing for ease of use.

## **Project Structure**

- **src/JAVA_MP/BusReservationGUI.java**: Contains the main code for the Bus Reservation System, including the GUI and core functionalities.
- **.settings/**: Eclipse settings directory.
- **.classpath**: Eclipse project classpath file.
- **.gitignore**: Specifies files and directories to be ignored by Git.
- **.project**: Eclipse project file.
- **businfo.txt**: File where bus information is stored and updated with each booking.

## **How to Run**

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/madhesh-hariharran/Bus-Reservation-System.git
2. **Navigate to the project directory**:
3. **Open the project in your IDE** (e.g., Eclipse or IntelliJ IDEA).
4. **Run the project**:
- Locate the `BusReservationGUI.java` file inside the `src/JAVA_MP/` directory.
- Run the `main` method in `BusReservationGUI.java` to start the application.

## Usage

- **Viewing Bus Information**:
- On launching the application, the available buses along with their details will be displayed in the text area.

- **Booking a Bus**:
- Click on the "Book a Bus" button.
- Enter the passenger's name, bus number, date of travel (in `dd-MM-yyyy` format), and the number of tickets.
- The system will check for availability and confirm the booking if seats are available.

## Future Enhancements

- Adding functionality for canceling bookings.
- Enhancing the GUI to include more features and improve usability.
- Implementing user authentication for secure booking management.

## Contributions

Contributions to improve the system or fix bugs are welcome. Please fork the repository and submit a pull request with your changes.

## License

This project is open-source and available under the MIT License.
