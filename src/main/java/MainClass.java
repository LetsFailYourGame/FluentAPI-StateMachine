public class MainClass {
    public static StateMachine s;
    public static void main(String[] args) {
        StateMachine s = (StateMachine) new StateMachine().fsm("turnstyle").
        addState("locked")
            .addTransition("ticket", "Ticket collected.", "unlocked")
            .addTransition("pass", "Alarm!", "exception")
        .addState("unlocked")
            .addTransition("ticket", "Ticket ejected.", "unlocked")
            .addTransition("pass", "locked")
        .addState("exception")
            .addTransition("ticket", "Ticket ejected.", "exception")
            .addTransition("pass", "exception")
            .addTransition("mute", "exception")
            .addTransition("release", "locked");

        new Interpreter(s).runInteractive();
    }
}
