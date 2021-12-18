import java.util.List;

/**
 * @author Thorsten Berger <thorsten.berger@rub.de>
 */
public interface IInterpreter {

    /**
     * Runs the state machine based on the input provided in the list input.
     * It immediately stops when an end state is reached or when the input is invalid.
     * In that case, it returns the list of outputs created so far during execution.
     * @param input a list of input messages, each of which trigger a transition.
     * @return the list of output messages; when no output is created by a transition, no element
     * is added to the list.
     */
    public List<String> run(List<String> input );

    /**
     * Prints the start state (#printCurrentStateAnInputs) and possible inputs.
     * Then, reads input from the console and outputs the transitions' output values when defined.
     * Also prints the current state after each input. The method returns when a final state is reached.
     */
    public void runInteractive();

    /**
     * Prints the current state and available inputs (i.e., all inputs of transitions leaving from
     * the current state).
     */
    void printCurrentStateAndInputs();
}