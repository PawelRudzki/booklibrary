import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor

public class LibraryBook extends LibraryTypes{

    private Book book;
    private int id;
    private int singleBorrowingDuration;
    private Date borrowDate;
    private Customer borrowedBy;


    public LibraryBook(){
        book = new Book();
    }
    @Override
    public String toString(){
        return book+" "+ id +" "+singleBorrowingDuration
                +" "+ borrowDate +" "+borrowedBy;
    }


}
