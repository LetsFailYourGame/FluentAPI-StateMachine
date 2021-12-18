import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InterpreterTest {
    @Test
    void testTurnStyleMachine(){
        String COLLECTED = "Ticket collected.";
        String EJECTED = "Ticket ejected.";
        String ALARM = "Alarm!";

        StateMachine s = (StateMachine) new StateMachine().fsm("test").
                addState("locked")
                    .addTransition("ticket", COLLECTED, "unlocked")
                    .addTransition("pass", ALARM, "exception")
                .addState("unlocked")
                    .addTransition("ticket", EJECTED, "unlocked")
                    .addTransition("pass", "locked")
                .addState("exception")
                    .addTransition("ticket", "Test Autobuild", "exception")
                    .addTransition("pass", "exception")
                    .addTransition("mute", "exception")
                    .addTransition("release", "locked");

        // input
        final List<String> input = List.of("ticket", "ticket", "pass", "pass", "ticket", "mute", "release");
        // expected output
        final List<String> output = List.of(COLLECTED, EJECTED, ALARM, EJECTED );

        // this assertion checks whether the two lists are equal
        Assertions.assertIterableEquals( new Interpreter(s).run(input), output);
    }
}
