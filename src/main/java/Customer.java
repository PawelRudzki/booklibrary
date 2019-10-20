import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Customer {

    private int customerID;
    private String name;
    private String lastName;
    private int booksBorrowed;
    private double accountBalance;

}
