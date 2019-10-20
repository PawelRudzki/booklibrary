import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class LibraryBook {

    private Book book;
    private int bookID;
    private int singleBorrowingDuration;
    private Date borrrowDate;
    private Customer borrowedBy;


}
