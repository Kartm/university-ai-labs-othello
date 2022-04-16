import java.util.Scanner;

public class Main {
    public static MinimaxPlayer[] players = new MinimaxPlayer[]{
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 1, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 2, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 3, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 4, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 5, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 6, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 7, true),
            new LeastMovesPlayer("LeastMovesPlayer", 1, true),
            new LeastMovesPlayer("LeastMovesPlayer", 2, true),
            new LeastMovesPlayer("LeastMovesPlayer", 3, true),
            new LeastMovesPlayer("LeastMovesPlayer", 4, true),
            new LeastMovesPlayer("LeastMovesPlayer", 5, true),
            new LeastMovesPlayer("LeastMovesPlayer", 6, true),
            new LeastMovesPlayer("LeastMovesPlayer", 7, true),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 1, false),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 2, false),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 3, false),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 4, false),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 5, false),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 6, false),
            new PenaltyRewardPlayer("PenaltyRewardPlayer", 7, false),
            new LeastMovesPlayer("LeastMovesPlayer", 1, false),
            new LeastMovesPlayer("LeastMovesPlayer", 2, false),
            new LeastMovesPlayer("LeastMovesPlayer", 3, false),
            new LeastMovesPlayer("LeastMovesPlayer", 4, false),
            new LeastMovesPlayer("LeastMovesPlayer", 5, false),
            new LeastMovesPlayer("LeastMovesPlayer", 6, false),
            new LeastMovesPlayer("LeastMovesPlayer", 7, false),
    };

    public static void bestVsWorst() {
        var char_a = (char) ((2 % (players.length / 2)) + 65);
        var char_b = (char) ((13 % (players.length / 2)) + 65);

        var p1 = players[2]; // BLACK
        var p2 = players[13]; // WHITE

        p1.timeSoFar = 0;
        p2.timeSoFar = 0;

        Othello game = new Othello();
        var result = game.play(p1, p2, false);

        var progress = String.format("%d/%d", 2 * players.length + 13, players.length * players.length);
        var p1_label = String.format("%s\t%s\t%d\t%s", char_a, p1.name, p1.getDepth(), p1.abEnabled);
        var p2_label = String.format("%s\t%s\t%d\t%s", char_b, p2.name, p2.getDepth(), p2.abEnabled);
        System.out.printf("%s\t%s\t%s\t%s\t%d\t%d%n", progress, p1_label, p2_label, result == BoardField.BLACK ? p1_label : p2_label, p1.timeSoFar, p2.timeSoFar);
    }

    public static void alphaBetaVsNonAlphaBeta() {
        for (int i = 0; i < players.length / 2; i++) {
            for (int j = players.length / 2; j < players.length; j++) {
                var char_a = (char) ((i % (players.length / 2)) + 65);
                var char_b = (char) ((j % (players.length / 2)) + 65);

                var p1 = players[i]; // BLACK
                var p2 = players[j]; // WHITE

                p1.timeSoFar = 0;
                p2.timeSoFar = 0;

                Othello game = new Othello();
                var result = game.play(p1, p2, true);

                var progress = String.format("%d/%d", i * players.length + j, (players.length / 2) * (players.length / 2));
                var p1_label = String.format("%s\t%s\t%d\t%s", char_a, p1.name, p1.getDepth(), p1.abEnabled);
                var p2_label = String.format("%s\t%s\t%d\t%s", char_b, p2.name, p2.getDepth(), p2.abEnabled);
                System.out.printf("%s\t%s\t%s\t%s\t%d\t%d%n", progress, p1_label, p2_label, result == BoardField.BLACK ? p1_label : p2_label, p1.timeSoFar, p2.timeSoFar);
            }
        }
    }

    public static void everyVsEvery() {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players.length; j++) {
                var char_a = (char) ((i % (players.length / 2)) + 65);
                var char_b = (char) ((j % (players.length / 2)) + 65);

                var p1 = players[i]; // BLACK
                var p2 = players[j]; // WHITE

                p1.timeSoFar = 0;
                p2.timeSoFar = 0;

                Othello game = new Othello();
                var result = game.play(p1, p2, true);

                var progress = String.format("%d/%d", i * players.length + j, players.length * players.length);
                var p1_label = String.format("%s\t%s\t%d\t%s", char_a, p1.name, p1.getDepth(), p1.abEnabled);
                var p2_label = String.format("%s\t%s\t%d\t%s", char_b, p2.name, p2.getDepth(), p2.abEnabled);
                System.out.printf("%s\t%s\t%s\t%s\t%d\t%d%n", progress, p1_label, p2_label, result == BoardField.BLACK ? p1_label : p2_label, p1.timeSoFar, p2.timeSoFar);
            }
        }
    }

    public static void custom() {
        System.out.println("""
                Choose the players. Available options:\s
                1. HumanPlayer\s
                2. PenaltyRewardPlayer, AI\s
                3. LeastMovesPlayer, AI
                """);

        Scanner scanner = new Scanner(System.in);

        Player[] players = new Player[2];

        int playersSelected = 0;
        while (playersSelected < 2) {
            System.out.printf("Please choose for the %s player: %n", playersSelected == 0 ? "first":"second");
            int playerIndex = scanner.nextInt(); // 1-3
            int depthInput = 0;
            int abInput = -1;
            if (playerIndex > 1) {
                if(depthInput < 1 || depthInput > 3) {
                    System.out.print("Please specify the depth: ");
                    depthInput = scanner.nextInt();
                }

                if(abInput < 0 || abInput > 1) {
                    System.out.print("Please specify if alphabeta prunning enabled: ");
                    abInput = scanner.nextInt();
                }
            }

            switch (playerIndex) {
                case 1:
                    players[playersSelected] = new HumanPlayer("Human");
                    playersSelected++;
                    break;
                case 2:
                    players[playersSelected] = new PenaltyRewardPlayer("PenaltyRewardPlayer", depthInput, abInput == 1);
                    playersSelected++;
                    break;
                case 3:
                    players[playersSelected] = new LeastMovesPlayer("LeastMovesPlayer", depthInput, abInput == 1);
                    playersSelected++;
                    break;
                default:
                    System.out.print("Invalid input! Please input again!\n");
                    break;
            }
        }

        Othello game = new Othello();

        game.play(players[0], players[1], false);

    }

    public static void main(String[] args) {
//        bestVsWorst();
//        alphaBetaVsNonAlphaBeta();
//        everyVsEvery();
        custom();


        return;
    }
}
