import java.util.Scanner;

public class Test {
    // todo make sure game code is valid
    // todo add moving without alpha-beta prunning
    // todo add my heuristics
    // todo add benchmark
    // todo better GUI

    public static void main(String[] args) {

        Player p1 = new HumanPlayer("Human");
        Player p2 = new WeightedSquaresPlayer("WeightedSquares");
        int first, second, ply1 = 4, ply2 = 4;
        boolean abEnabled = false;
        boolean flag = true;
        String astr = """

                Choose the players. Available options:\s
                1. HumanPlayer
                2. FrontierDisksPlayer, heuristic:\s
                      using the feature number of disks adjacent to empty squares.
                3. WeightedSquaresPlayer, heuristic:\s
                      using the feature weighted squares value for one player.
                4. PotentialMobilityPlayer, heuristic:\s
                      combine two features: number of frontier discs\s
                        and number of empty discs adjacent to the opponent's discs.

                """;
        System.out.println(astr);

        /* set the two player according to user's choices */
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
                case 2 -> p1 = new FrontierDisksPlayer("FrontierDisks", ply1, abEnabled);
                case 3 -> p1 = new WeightedSquaresPlayer("WeightedSquares", ply1, abEnabled);
                case 4 -> p1 = new CurrentMobilityPlayer("CurrentMobility", ply1, abEnabled);
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
                case 2 -> p2 = new FrontierDisksPlayer("FrontierDisks", ply2, abEnabled);
                case 3 -> p2 = new WeightedSquaresPlayer("WeightedSquares", ply2, abEnabled);
                case 4 -> p2 = new CurrentMobilityPlayer("CurrentMobility", ply2, abEnabled);
                default -> {
                    flag = true;
                    System.out.print("Invalid input! Please input again!\n");
                }
            }
        }

        Othello game = new Othello();

        game.play(p1, p2);
    }
}
