import java.util.ArrayList;

public interface IStateMachine {
    String getType();
    ArrayList<State> getAllStates();
    State getCurrentState();
    void setState(State state);
    IStateMachine addState(String name);
    IStateMachine fsm(String name);
    IStateMachine addTransition(String inputOption, String output, String exitState);
    IStateMachine addTransition(String inputOption, String exitState);
    IStateMachine dbg();
}
