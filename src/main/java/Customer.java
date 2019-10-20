import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {

    private String name;
    private String lastName;
    private int booksBorrowed;
    private double accountBalance;

}
