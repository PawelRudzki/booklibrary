import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Book {

    private String isbnNr;
    private String name;
    //private String author;
    private String publicationYear;
    private String publishingHouse;


}
