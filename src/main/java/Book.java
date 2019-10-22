import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor

public class Book {

    private String isbn;
    private String title;
    private Set<Author> authors;
    private String publicationYear;
    private String publishingHouse;
    private BookCategory bookCategory;

    @Override
    public String toString(){
        return isbn +" "+ title +" "+publicationYear+" "+publishingHouse
                +" "+bookCategory+" "+authors;
    }


}
