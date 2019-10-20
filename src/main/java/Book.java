import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor

public class Book {

    private String isbnNr;
    private String name;
    private Set<Author> authors;
    private String publicationYear;
    private String publishingHouse;
    private BookCategory bookCategory;

    @Override
    public String toString(){
        return isbnNr+" "+name+" "+publicationYear+" "+publishingHouse
                +" "+bookCategory+" "+authors;
    }


}
