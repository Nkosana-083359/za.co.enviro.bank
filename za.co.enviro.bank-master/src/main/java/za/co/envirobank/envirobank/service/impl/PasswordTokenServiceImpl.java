package za.co.envirobank.envirobank.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.PasswordToken;
import za.co.envirobank.envirobank.respository.PasswordTokenRepository;
import za.co.envirobank.envirobank.service.PasswordTokenService;

@Service
@AllArgsConstructor
class PasswordTokenServiceImpl implements PasswordTokenService {
    private final PasswordTokenRepository passwordTokenRepository;

    @Override
    public void savePasswordToken(PasswordToken token) {
        passwordTokenRepository.save(token);
    }

    @Override
    public PasswordTokenService getToken(String token) {
        passwordTokenRepository.findByToken(token);
        return null;
    }

    @Override
    public int passwordModificationDate(String token) {
        return 0;
    }
}