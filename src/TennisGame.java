import com.tennisparty.constant.GameStatus;
import com.tennisparty.model.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TennisGame {

    private String player1;

    private String player2;
    private List<Set> scoreSets = new ArrayList<>();
    private Set currentSet = new Set();
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;

    public String showScore(){
        return scoreSets.stream()
                .map(Set::getSetScore)
                // Chaque score de chaque set est joint pour former le score global de la partie
                .collect(Collectors.joining(" "));
    }

    public String showCurrentPointStatus(){
        // Si pas de tie break, on affiche le score du P1 et le score du P2 simplement
        if (currentSet.getTieBreak().getScorePlayer1().equals(-1)){
            return currentSet.getCurrentPoint().getScorePlayer1() + " - " + currentSet.getCurrentPoint().getScorePlayer2();
        // Sinon, on affiche le score des 2 joueurs suffixé du score du tie break
        } else {
            return "Tie break : " + currentSet.getTieBreak().getScorePlayer1() + " - " + currentSet.getTieBreak().getScorePlayer2();
        }
    }

    public String showGameStatus(){
        return gameStatus.label;
    }
}
