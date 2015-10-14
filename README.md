# TicketService

###Steps to run the project from command line:

Make sure Java and maven are downloaded and installed. Also Environmental variables are set.
Navigate to the project location where pom.xml is located in the cmd prompt

To clean: mvn clean

To compile: mvn compile

To compile and run test cases: mvn test

To create jar/war and install to local repository: 1) mvn package 2) mvn install

To execute this project along with test cases perform: mvn clean test

To run set of methods in single test class is:

Single method of a test class:

mvn -Dtest=TicketServiceTest#getNumberOfSeatsAvailable test

mvn -Dtest=TicketServiceTest#getSeatsNeitherHeldNorReserved test

mvn -Dtest=TicketServiceTest#seatsAvailableInMinLevel_BooksMinLevel test

mvn -Dtest=TicketServiceTest#seatsRequiredNotAvailableInMinLevel_BooksFromNextLevel test

mvn -Dtest=TicketServiceTest#holdSeatsToReservedSeats test

mvn -Dtest=TicketServiceTest#invalidateHoldSeats test

Pattern matching:

mvn -Dtest=TicketServiceTest#getNumberOfSea* test 

###Assumptions:

-	Implemented in-memory project to avoid disk storage.
-	Due to in-memory implemented to access one at a time using ConcurrentHashMap.
-	Overloaded numOfSeats() method with empty parameters to identify total seats available.
-	Similarly, overloaded findAndHoldSeats() method by passing 2 parameters, 3 parameters and 4 parameters (given).
-	The assumption to overload findAndHoldSeats() method is: User can enter specified Level of interest or User can book seats available in any level based on the availability.
-	Did not make any restrictions on mailed as of this project because made it synchronized.
-	timeOutInterval = 5000ms for the expiration of HoldSeat. If time expires made seats to available else reserved the seats.
-	Generated AtomicInteger for SeatHoldId to be unique and incremented by 1.
-	Similarly, generated UUID for Code of confirmation for reserved seats.
-	I gave priority to fill the minimum level first and then next levels if not specified any particular level.

###Steps:

Step 1: Created Maven Project

Step 2: Created packages named com.rreapala.bo and com.rrepala.service

Step 3: Inside bo package created two classes named Seat.Java and SeatHold.Java, Level.Java as Enum

Step 4: Created Level.Java as Enum because an enum type is a special data type that enables for a variable to be a set of predefined constants. The variable must be equal to one of the values that have been predefined for it.   So in our scenario made Levels and its components as enum variables to be specific. To make dynamic need to create a class and populate it from property file

Step 5: Created Seat.Java to specify the Seat Number and the Row Number it belongs to.

Step 6: SeatHold.Java is a class to identify which seats are on hold. It consists of SeatHoldId, List of seats it held, and times it holded the seats and level at which the seat is held.

Step 7: I made use of interface provided. In addition added few overloaded methods to the existing.

Step 8: Implemented the interface.

Step 9: Written JUnit test cases to validate the functionality.

Note: Commented out print () method. It is to just view the results of methods.

