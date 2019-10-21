import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Customer extends LibraryTypes{

    private int id;
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
        return id +" "+name+" "+lastName+" "+accountBalance+" "+booksBorrowedList;
    }

}
