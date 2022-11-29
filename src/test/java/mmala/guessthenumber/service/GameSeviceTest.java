package mmala.guessthenumber.service;

import mmala.guessthenumber.TestApplicationConfiguration;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameSeviceTest {

    @Autowired
    GameService service;

    public GameSeviceTest() {
    }

    @Test
    public void testDetermineResult1() {
        String guess = "1234";
        String answer = "2159";
        String result = service.determineResult(guess, answer);

        assertEquals("e:0:p:2", result);
    }

    @Test
    public void testDetermineResult2() {
        String guess = "1234";
        String answer = "1234";
        String result = service.determineResult(guess, answer);

        assertEquals("e:4:p:0", result);
    }

    @Test
    public void testDetermineResult3() {
        String guess = "1234";
        String answer = "4321";
        String result = service.determineResult(guess, answer);

        assertEquals("e:0:p:4", result);
    }

    @Test
    public void testDetermineResult4() {
        String guess = "1234";
        String answer = "1324";
        String result = service.determineResult(guess, answer);

        assertEquals("e:2:p:2", result);
    }

    @Test
    public void testDetermineResult5() {
        String guess = "1234";
        String answer = "5678";
        String result = service.determineResult(guess, answer);

        assertEquals("e:0:p:0", result);
    }

}
