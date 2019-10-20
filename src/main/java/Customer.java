import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
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


    public void borrowBook(Library library, LibraryBook libraryBook){
        library.lendBook(this, libraryBook);
    }

    @Override
    public String toString(){
        return customerID+" "+name+" "+lastName+" "+accountBalance+" "+booksBorrowedList;
    }

}
