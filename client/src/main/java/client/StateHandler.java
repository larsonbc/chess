package client;

public class StateHandler {
    private State state = State.SIGNEDOUT;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
