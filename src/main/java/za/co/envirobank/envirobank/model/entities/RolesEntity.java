package za.co.envirobank.envirobank.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roleId;
    @Enumerated(value = EnumType.STRING)
    private String name;
}
