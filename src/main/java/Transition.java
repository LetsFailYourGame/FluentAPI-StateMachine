public class Transition {
    String outputOption;
    String outputText;
    State exitState;

    public Transition(String option, String outputText, State exitState)
    {
        this.outputOption = option;
        this.outputText = outputText;
        this.exitState = exitState;
    }
    public Transition(String option, State exitState)
    {
        this.outputOption = option;
        this.exitState = exitState;
    }
}