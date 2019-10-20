import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Library {

    List<LibraryBook> libraryWarehouse;
    List<Customer> customerList;

}
