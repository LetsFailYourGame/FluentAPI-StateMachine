import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InterpreterTest {
    private String enter_text(String room) {
        return "entering " + room;
    }

    @Test
    void testTurnStyleMachine(){
        String COLLECTED = "Ticket collected.";
        String EJECTED = "Ticket ejected.";
        String ALARM = "Alarm!";

        StateMachine s1 = (StateMachine) new StateMachine().fsm("test").
                addState("locked")
                    .addTransition("ticket", COLLECTED, "unlocked")
                    .addTransition("pass", ALARM, "exception")
                .addState("unlocked")
                    .addTransition("ticket", EJECTED, "unlocked")
                    .addTransition("pass", "locked")
                .addState("exception")
                    .addTransition("ticket", EJECTED, "exception")
                    .addTransition("pass", "exception")
                    .addTransition("mute", "exception")
                    .addTransition("release", "locked");
        // input
        final List<String> input_s1 = List.of("ticket", "ticket", "pass", "pass", "ticket", "mute", "release");
        // expected output
        final List<String> output_s1 = List.of(COLLECTED, EJECTED, ALARM, EJECTED );

        // this assertion checks whether the two lists are equal
        Assertions.assertIterableEquals( new Interpreter(s1).run(input_s1), output_s1);
    }

    @Test
    void testTurnStyleMachineSecond() {
        String BEDROOM = "bedroom";
        String BASEMENT = "basement";
        String BATHROOM = "bathroom";
        String LIVING_ROOM = "living room";
        String KITCHEN =  "kitchen";
        String STAY = "stay";

        StateMachine s2 = (StateMachine) new StateMachine().fsm("house_tour")
                .addState(BASEMENT)
                .addTransition(LIVING_ROOM, enter_text(LIVING_ROOM), LIVING_ROOM)
                .addTransition(KITCHEN, enter_text(KITCHEN),KITCHEN)
                .addTransition(BATHROOM, enter_text(BATHROOM),BATHROOM)
                .addTransition(BEDROOM, enter_text(BEDROOM),BEDROOM)
                .addTransition(STAY, BASEMENT)
                .addState(BATHROOM)
                .addTransition(BASEMENT, enter_text(BASEMENT), BASEMENT)
                .addTransition(STAY, BATHROOM)
                .addState(LIVING_ROOM)
                .addTransition(BASEMENT, enter_text(BASEMENT), BASEMENT)
                .addTransition(KITCHEN, enter_text(KITCHEN),KITCHEN)
                .addTransition(STAY, LIVING_ROOM)
                .addState(KITCHEN)
                .addTransition(BASEMENT, enter_text(BASEMENT), BASEMENT)
                .addTransition(LIVING_ROOM, enter_text(LIVING_ROOM), LIVING_ROOM)
                .addTransition(STAY, KITCHEN)
                .addState(BEDROOM)
                .addTransition(BASEMENT, enter_text(BASEMENT), BASEMENT)
                .addTransition(STAY, BEDROOM);

        final List<String> input_s2 = List.of(STAY, BATHROOM, BASEMENT, LIVING_ROOM, KITCHEN, STAY, BASEMENT, STAY);
        final List<String> output_s2 = List.of(enter_text(BATHROOM), enter_text(BASEMENT), enter_text(LIVING_ROOM), enter_text(KITCHEN), enter_text(BASEMENT));
        Assertions.assertIterableEquals( new Interpreter(s2).run(input_s2), output_s2);
    }
}
