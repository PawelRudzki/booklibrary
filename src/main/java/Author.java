import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Author implements Comparable<Author>{
    private String firstName;
    private String lastName;

    @Override
    public int compareTo(Author author){
        return lastName.compareTo(author.lastName);
    }


}
