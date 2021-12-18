import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interpreter implements IInterpreter {

    private final StateMachine sm;
    public Interpreter(StateMachine sm) {
       this.sm = sm;
    }
    /**
     * Runs the state machine based on the input provided in the list input.
     * It immediately stops when an end state is reached or when the input is invalid.
     * In that case, it returns the list of outputs created so far during execution.
     *
     * @param input a list of input messages, each of which trigger a transition.
     * @return the list of output messages; when no output is created by a transition, no element
     * is added to the list.
     */
    @Override
    public List<String> run(List<String> input) {
        List<String> output = new ArrayList<>(); // create the output list
        for(String in : input) // for each input given by list
        {
            for(Transition t : sm.getCurrentState().leavingTransitions) // loop through all transitions
            {
                if(sm.getCurrentState().isEndState()) // if current state is end state exit
                {
                    return output;
                }
                if(t.outputOption.equals(in)) // if current transition in loop equals input
                {
                    sm.setState(t.exitState); // set current state to exit stage

                    if(t.outputText != null) { // if output text exists
                        output.add(t.outputText); // add to output list
                    }
                    break;
                }
            }
        }
        return output;
    }

    /**
     * Prints the start state (#printCurrentStateAnInputs) and possible inputs.
     * Then, reads input from the console and outputs the transitions' output values when defined.
     * Also prints the current state after each input. The method returns when a final state is reached.
     */
    @Override
    public void runInteractive() {
        System.out.println("[+] Current State: " + sm.getCurrentState().getStateName());
        printCurrentStateAndInputs();
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        for (Transition t : sm.getCurrentState().leavingTransitions) // for all transitions
        {
            if (sm.getCurrentState().isEndState()) // if current state is a end state exit
            {
                return;
            }
            if (t.outputOption.equals(input)) // if input name is found in a transition
            {
                sm.setState(t.exitState); // set current state to the exit state
                if (t.outputText != null) // if a output text exists
                {
                    System.out.println(t.outputText); // print output text
                }
                break;
            }
        }
        runInteractive(); // recursion
    }

    /**
     * Prints the current state and available inputs (i.e., all inputs of transitions leaving from
     * the current state).
     */
    @Override
    public void printCurrentStateAndInputs() {
        StringBuilder str = new StringBuilder();
        for (Transition t : sm.getCurrentState().leavingTransitions) {
            str.append(t.outputOption).append(", ");
        }
        String commaSeparatedList = str.toString();
        if (commaSeparatedList.length() > 0) {
            commaSeparatedList = commaSeparatedList.substring(0, commaSeparatedList.length() - 2);
        }
        System.out.println("[+] Select a option: " + commaSeparatedList);
    }
}
