import java.util.Scanner;

public class Test {
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
        var p1_label = String.format("%s\t%s\t%d\t%s", char_a, p1.name, p1.getPLY(), p1.abEnabled);
        var p2_label = String.format("%s\t%s\t%d\t%s", char_b, p2.name, p2.getPLY(), p2.abEnabled);
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
                var p1_label = String.format("%s\t%s\t%d\t%s", char_a, p1.name, p1.getPLY(), p1.abEnabled);
                var p2_label = String.format("%s\t%s\t%d\t%s", char_b, p2.name, p2.getPLY(), p2.abEnabled);
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
                var p1_label = String.format("%s\t%s\t%d\t%s", char_a, p1.name, p1.getPLY(), p1.abEnabled);
                var p2_label = String.format("%s\t%s\t%d\t%s", char_b, p2.name, p2.getPLY(), p2.abEnabled);
                System.out.printf("%s\t%s\t%s\t%s\t%d\t%d%n", progress, p1_label, p2_label, result == BoardField.BLACK ? p1_label : p2_label, p1.timeSoFar, p2.timeSoFar);
            }
        }
    }

    public static void custom() {
        Player p1 = new HumanPlayer("HumanPlayer");
        Player p2 = new PenaltyRewardPlayer("PenaltyRewardPlayer");
        int first, second, ply1 = 4, ply2 = 4;
        boolean abEnabled = false;
        boolean flag = true;
        String astr = """

                Choose the players. Available options:\s
                1. HumanPlayer\s
                2. PenaltyRewardPlayer, AI\s
                3. LeastMovesPlayer, AI
                """;
        System.out.println(astr);

        Scanner scanner = new Scanner(System.in);
        while (flag) {
            flag = false;
            System.out.print("Please choose for the first player: ");
            first = scanner.nextInt();
            if (first != 1) {
                System.out.print("Please specify the ply: ");
                ply1 = scanner.nextInt();

                System.out.print("Please specify if alphabeta prunning enabled: ");
                abEnabled = scanner.nextInt() == 1;
            }
            switch (first) {
                case 1 -> p1 = new HumanPlayer("Human");
                case 2 -> p1 = new PenaltyRewardPlayer("PenaltyRewardPlayer", ply1, abEnabled);
                case 3 -> p1 = new LeastMovesPlayer("LeastMovesPlayer", ply1, abEnabled);
                default -> {
                    flag = true;
                    System.out.print("Invalid input! Please input again!\n");
                }
            }
        }
        flag = true;
        while (flag) {
            flag = false;
            System.out.print("Please choose for the second player: ");
            second = scanner.nextInt();
            if (second != 1) {
                System.out.print("Please specify the ply: ");
                ply2 = scanner.nextInt();

                System.out.print("Please specify if alphabeta prunning enabled: ");
                abEnabled = scanner.nextInt() == 1;
            }
            switch (second) {
                case 1 -> p2 = new HumanPlayer("Human");
                case 2 -> p2 = new PenaltyRewardPlayer("PenaltyRewardPlayer", ply2, abEnabled);
                case 3 -> p2 = new LeastMovesPlayer("LeastMovesPlayer", ply2, abEnabled);
                default -> {
                    flag = true;
                    System.out.print("Invalid input! Please input again!\n");
                }
            }
        }

        Othello game = new Othello();

        game.play(p1, p2, false);

    }

    public static void main(String[] args) {
//        bestVsWorst();
//        alphaBetaVsNonAlphaBeta();
//        everyVsEvery();
        custom();


        return;
    }
}
