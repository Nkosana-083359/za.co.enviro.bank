package za.co.envirobank.envirobank.transfereobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.envirobank.envirobank.model.entities.Account;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsResponse {
    private List<Account> content;
    private  int pageNo;
    private int pageSize;
    private long totalElements;

    private int totalPages;

    private boolean last;

}
