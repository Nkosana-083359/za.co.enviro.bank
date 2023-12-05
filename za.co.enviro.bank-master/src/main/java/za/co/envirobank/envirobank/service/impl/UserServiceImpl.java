package za.co.envirobank.envirobank.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.config.BCryptPasswordEncoderConfig;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.respository.UsersRepository;
import za.co.envirobank.envirobank.service.UserService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoderConfig bCryptPasswordEncoderConfig;
    private final EntityManager entityManager;

    public List<UserEntity> getUsersWithAccountDetailsAndWithoutAccounts() {
        String sql = "SELECT c.id AS customer_id, " +
                "COALESCE(COUNT(a.acc_num), 0) AS num_accounts, " +
                "c.name AS name, " +
                "c.surname AS surname, " +
                "c.email AS email, " +
                "c.phone_number AS phoneNumber, " +
                "c.id_number AS idNumber " +
                "FROM users c " +
                "LEFT JOIN bank_accounts a ON c.id = a.customer_id " +
                "GROUP BY c.id";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        List<UserEntity> usersWithAccountDetails = new ArrayList<>();

        for (Object[] result : results) {
            Customer user = new Customer();
            user.setId((UUID) result[0]);
            user.setName((String) result[2]);
            user.setSurname((String) result[3]);
            user.setPhoneNumber((String) result[5]);
            user.setIdNumber((String) result[6]);
            user.setEmail((String) result[4]);
            user.setNumAccounts(BigInteger.valueOf((Long) result[1])); // Change casting to Long
            usersWithAccountDetails.add(user);
        }

        return usersWithAccountDetails;
    }

    @Override
    public Optional<UserEntity> findByUsernameOrEmail(String emailOrUsername) {
        return usersRepository.findByUsernameOrEmail(emailOrUsername, emailOrUsername );
    }

    @Override
    public String getUserEmailBy(String username) {
        return usersRepository.findByUsernameOrEmail( username,username ).get().getEmail();
    }

    @Override
    public Boolean isOldPasswordValid(UserEntity user, String oldPassword) {
        return bCryptPasswordEncoderConfig.passwordEncoder().matches(oldPassword, user.getPassword());
    }

    @Override
    public List<UserEntity> getAllUser() {
        return usersRepository.findAll();
    }
}
