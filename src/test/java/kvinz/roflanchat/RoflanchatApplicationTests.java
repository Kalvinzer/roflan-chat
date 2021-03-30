package kvinz.roflanchat;

import kvinz.roflanchat.repository.ResetTokenRepository;
import kvinz.roflanchat.service.ResetTokenService;
import kvinz.roflanchat.service.ResetTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class RoflanchatApplicationTests {

    ResetTokenService resetTokenService = new ResetTokenServiceImpl();

    @Test
    void test() {
        resetTokenService.removeTokenByToken("f7e740fc-1535-4a3d-9f40-6e3fc3dac737");
    }

}
