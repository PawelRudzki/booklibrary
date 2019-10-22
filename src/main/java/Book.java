import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
@AllArgsConstructor
public class Book {

    private String isbn;
    private String title;
    private Set<Author> authors;
    private String publicationYear;
    private String publishingHouse;
    private BookCategory bookCategory;

    public Book(){
        isbn="";
        title="";
        authors=new TreeSet<>();
        publicationYear="";
        publishingHouse="";
        bookCategory=BookCategory.OTHER;

    }

    @Override
    public String toString(){
        return isbn +" "+ title +" "+publicationYear+" "+publishingHouse
                +" "+bookCategory+" "+authors;
    }


}
