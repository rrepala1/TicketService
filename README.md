# TicketService

###Steps to run the project from command line:

Make sure Java and maven are downloaded and installed. Also Environmental variables are set.
Navigate to the project location where pom.xml is located in the cmd
Then mvn clean test and enter the project builds successfully along with the testcases.

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

-	As no disk storage strictly required implemented in-memory project
-	Due to in-memory implemented to access one at a time using ConcurrentHashMap.
-	Overloaded numOfSeats() method with empty parameters to identify total seats availale.
-	Similarly, overloaded findAndHoldSeats() method by passing 2 parameters, 3 parameters and 4 parameters which is given.
-	The assumption to overload findAndHoldSeats method is: User can enter specified Level of interest or User can book seats available in any level based on the availability.
-	Did not make any restrictions on mailed as of this project because made it synchronized.
-	timeOutInterval = 5000ms for the expiration of HoldSeat. If time expires made seats to available else reserved the seats.
-	Generated AtomicInteger for SeatHoldId to be unique and incremented by 1.
-	Similarly, generated UUID for Code of confirmation for reserved seats.
-	I gave priority to fill the minimum level first and then next levels if not specified any particular level.
-	Exceptions are not handled as made project as concurrent.

###Steps:

Step 1: Created Maven Project

Step 2: Created pacakages named com.rreapala.bo and com.rrepaal.service

Step 3: Inside bo package created two classes named Seat.Java and SeatHold.Java, Level.Java as Enum

Step 4: Created Level.Java as Enum because an enum type is a special data type that enables for a variable to be a set of predefined constants. The variable must be equal to one of the values that have been predefined for it.   So in our scenario made Levels and its components as enum variables.

Step 5: Created Seat.Java to specify the SeatNumbaer and the Row Number it belongs to.

Step 6: SeatHold.Java is a class to identify which seats are on hold. It consists of SeatHoldId, List of seats it held, and times it holded the seats and level at whaich the seat is held.

Step 7: I made use of interface provided. In addition added few overloaded methods to the existing.

Step 8: Implemented the interface.

Step 8: Written JUnit test cases to check the functionality.

Note: Commented out print () method. It is to just view the results of methods.
