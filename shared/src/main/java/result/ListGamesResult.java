package result;

import model.GameSummary;

import java.util.ArrayList;

public record ListGamesResult(ArrayList<GameSummary> games) {
}
