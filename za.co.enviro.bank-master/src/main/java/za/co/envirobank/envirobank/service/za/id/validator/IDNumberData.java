package za.co.envirobank.envirobank.service.za.id.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.enums.Gender;
import za.co.envirobank.envirobank.enums.Nationality;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@Getter
public class IDNumberData {
    private String idNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Nationality citizenship;
    private boolean valid;

    public int getAge(){
        LocalDate today = LocalDate.now();
        Period p = Period.between(this.dateOfBirth,today);
        return p.getYears();
    }

    public boolean isValid(){return this.valid;}
}
