import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Customer {

    private int customerID;
    private String name;
    private String lastName;
    private List<LibraryBook> booksBorrowedList;
    private double accountBalance;

    public void giveBackBook(Library library, LibraryBook libraryBook){
        library.acceptBook(libraryBook);
    }


}
