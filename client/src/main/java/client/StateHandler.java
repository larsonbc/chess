package client;
import client.websocket.WebSocketFacade;

public class StateHandler {
    private State state = State.SIGNEDOUT;
    private String authToken;
    private GameplayClient gameplayClient;
    private Integer currentGameId;
    private String playerColor;

    public WebSocketFacade getWs() {
        return ws;
    }

    public void setWs(WebSocketFacade ws) {
        this.ws = ws;
    }

    private WebSocketFacade ws;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public GameplayClient getGameplayClient() {
        return gameplayClient;
    }

    public void setGameplayClient(GameplayClient gameplayClient) {
        this.gameplayClient = gameplayClient;
    }

    public int getCurrentGameId() {
        return currentGameId;
    }

    public void setCurrentGameId(Integer currentGameId) {
        this.currentGameId = currentGameId;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
}
