import java.util.ArrayList;

public class StateMachine implements IStateMachine{
    private String type;
    private final ArrayList<State> allStates = new ArrayList<>();
    private State currentState;
    private State initState;
    private String savedStateToAdd;
    private int index;

    private State get(String name) {
        for(State s : allStates) {
            if(s.getStateName().equals(name)) {
                return s;
            }
        }
        return null;
    }
    private int isState(String name) {
        for (int i = 0; i < allStates.size(); i++) {
            if(allStates.get(i).getStateName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setState(State s) {
        currentState = s;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public ArrayList<State> getAllStates() {
        return allStates;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    public IStateMachine dbg() {
        System.out.println("======[DEBUG]======");
        System.out.println(getType() + ":");

        System.out.println("\tInitial State:");
        System.out.println("\t\t{ " + initState.getStateName() + " }");

        System.out.println("\tTransitions:");
        for(State s : allStates) {
            for(Transition t : s.leavingTransitions) {
                System.out.format("\t\t{ %s - (%s, %s, %s) }\n", s.getStateName(), t.outputOption, t.outputText, t.exitState.getStateName());
            }
        }
        System.out.println("===================\n");
        return this;
    }

    @Override
    public IStateMachine fsm(String name) {
        this.type = name;
        return this;
    }

    // used only once for initialisation
    private boolean swap = true;

    @Override
    public IStateMachine addState(String name) {
        index = isState(name);
        if(index >= 0) {
            return this;
        }

        allStates.add(new State(name));
        savedStateToAdd = name;

        // set the first added node as default start
        if(swap){
            currentState = get(name);
            initState = get(name);
            swap = false;
        }
        return this;
    }

    @Override
    public IStateMachine addTransition(String inputOption, String output, String exitState) {
        State exit = get(exitState);
        State toAdd = get(savedStateToAdd);

        // both states exist
        if(toAdd != null && index == -1 && exit != null) {
            toAdd.leavingTransitions.add(new Transition(inputOption, output, exit));
            return this;
        }

        if(toAdd != null && index >= 0 && exit != null) {
            allStates.get(index).leavingTransitions.add(new Transition(inputOption, output, exit));
            return this;
        }

        // exit state not defined
        allStates.add(new State(exitState));
        addTransition(inputOption, output, exitState);

        return this;
    }

    @Override
    public IStateMachine addTransition(String inputOption, String exitState) {
        State exit = get(exitState);
        State toAdd = get(savedStateToAdd);

        // both states exist
        if(toAdd != null && index == -1 && exit != null) {
            toAdd.leavingTransitions.add(new Transition(inputOption, exit));
            return this;
        }

        if(toAdd != null && index >= 0 && exit != null) {
            allStates.get(index).leavingTransitions.add(new Transition(inputOption, exit));
            return this;
        }

        // something went wrong
        assert toAdd == null;

        // exit state not defined
        allStates.add(new State(exitState));
        addTransition(inputOption, exitState);

        return this;
    }

}
