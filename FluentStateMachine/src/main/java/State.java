import java.util.ArrayList;

public class State{
    private boolean endState;
    private String stateName;
    ArrayList<Transition> leavingTransitions = new ArrayList<>();

    public State(String state) {
        this.stateName = state;
    }
    public State(boolean endState) {
        this.endState = endState;
    }
    public boolean isEndState() {
        return endState;
    }
    public String getStateName() {
        return stateName;
    }
}