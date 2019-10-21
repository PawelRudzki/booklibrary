import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class LibraryBook extends LibraryTypes{

    private Book book;
    private int id;
    private int singleBorrowingDuration;
    private Date borrrowDate;
    private Customer borrowedBy;

    @Override
    public String toString(){
        return book+" "+ id +" "+singleBorrowingDuration
                +" "+borrrowDate+" "+borrowedBy;
    }


}
