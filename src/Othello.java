import java.lang.Character;
import java.lang.String;
import java.util.ArrayList;

public class Othello {
	
	private static final int size = 8;
	private BoardField[][] cBoard;
	public static String newline = System.getProperty("line.separator");

	private static final int[] dirR = {-1,1,0,0,1,-1,1,-1};
	private static final int[] dirC = {0,0,1,-1,1,-1,-1,1};
	
	/**
	 * Blank class constructor.
	 * The playing board size is 8*8 but to make the programming easy, here use a 10*10 2D array 
	 * and the four edge is outside the playing board and will be set to BoardField.EMPTY
	 */
	public Othello() {
		cBoard = new BoardField[size+2][size+2];
		reset();
	}
	
	/**
	 * Class constructor allowing the addition of a board. This special
	 * constructor allows the board state to be loaded as-is. There are
	 * no players attached, so lists of moves can be generated from the
	 * current board state.
	 *
	 * @param n	An int that determines the size of the board.
	 * @param workingBoard A 2 Dimensional array of chars describing a board
	 */
	public Othello(int n, BoardField[][] workingBoard){
		if (n != size+2) {
			System.out.println(" Invalid game board!");
		}
		else 
			cBoard = workingBoard;
	} // end of constructor
	
	/**
	 * Copy Constructor takes an object of type Othello and makes a copy
	 * 
	 * @param k An object of type Othello to be copied.
	 */
	public Othello(Othello k) {
		cBoard = k.getBoardCopy();
	}
	
	/**
	 * This resets the board to its start state
	 */
	public void reset() {
		
		// Define two counters and set them to zero
		int i,j;
		for(i=1; i<size+1; i++)
			for(j=1; j<size+1; j++)
				cBoard[i][j] = BoardField.EMPTY;
		for(i=0; i<size+2; i++) 
			cBoard[0][i] = cBoard[i][0] = cBoard[size+1][i] = cBoard[i][size+1] = BoardField.BORDER;
		i = size/2;
		cBoard[i][i] = cBoard[i+1][i+1] = BoardField.WHITE;
		cBoard[i][i+1] = cBoard[i+1][i] = BoardField.BLACK;
	} // End of reset

	/**
	 * Counts the number of times the specified symbol occurs.
	 * Can be used as a score counter
	 * 
	 * @param symbol A char that specifies the symbol to search for
	 * @return An int with the number of occurrences of the symbol
	 */
	public int countSymbol(BoardField symbol) {
		// set the count to zero
		int count = 0;
		// check all squares and count the symbol
		for (int c = 1 ; c <= size; c++) {
			for (int r = 1; r <= size; r++) {
				if (cBoard[c][r] == symbol) {
					count++;
				}
			}
		}
		// return the count
		return count;
	} // end of countSymbol class
	
	
	/**
	 * sum of the weights of the parameter specified player on board
	 */
	public int weightedSquares(BoardField player) {
		int[][] weights = {{0,0,0,0,0,0,0,0,0,0},
					  {0,120,-20,20,5,5,20,-20,120,0},
					  {0,-20,-40,-5,-5,-5,-5,-40,-20,0},
					  {0,20,-5,15,3,3,15,-5,20,0},
					  {0,5,-5,3,3,3,3,-5,5,0},
					  {0,5,-5,3,3,3,3,-5,5,0},
					  {0,20,-5,15,3,3,15,-5,20,0},
					  {0,-20,-40,-5,-5,-5,-5,-40,-20,0},
					  {0,120,-20,20,5,5,20,-20,120,0},
					  {0,0,0,0,0,0,0,0,0,0}};
		int sum = 0;
		for(int i=1; i<=size; i++)
			for(int j=1; j<=size; j++) {
				if(cBoard[i][j] == player)
					sum += weights[i][j];
			}
		return sum;
	}
	
	/**
	 * This calculates the number of the parameter specified player adjacent to empty squares.
	 */
	public int frontierDisks(BoardField player) {
		
		int sum = 0;
		for(int i=1; i<=size; i++) 
			for(int j=1; j<=size; j++) {
				if(cBoard[i][j] == player) {
					for(int k=0; k<8; k++)
						if(cBoard[i+dirR[k]][j+dirC[k]] == BoardField.EMPTY){
							sum++;
							break;
						}
				}
			}
		return sum;
	}

	
	
	
	/**
	 * Returns the opponent of the specified player
	 * 
	 * @param player A char describing the current player. Valid chars are BoardField.BLACK or BoardField.WHITE
	 * @return BoardField.BLACK if player is BoardField.WHITE, or BoardField.WHITE if player is BoardField.BLACK
	 */
	public BoardField opponent(BoardField player) {
		if (player == BoardField.BLACK)
			return BoardField.WHITE;
		else
			return BoardField.BLACK;
	}// end of opponent
	
	/**
	 * check whether this move would result in any flips in this direction.
	 * @param player the current player who is making this move
	 * @param r the row number of the move square
	 * @param c the column of the move square
	 * @param dir the direction that we are checking
	 */
	public boolean wouldFlip(BoardField player, int r, int c, int dir) {
		int row = r, col = c;
		boolean flag = false;
		for(int i=0; i<8; i++) {// todo why this loop
			// row is -1
			row+=dirR[dir];
			col+=dirC[dir];
			if(cBoard[row][col] == opponent(player)){
				flag = true;
			}
			else if(cBoard[row][col] == player){
				return flag;
			}
			else return false;
		}
		return false;
	}
	
	/**
	 * Make any flips in the given direction
	 * @param player the current player who is making this move
	 * @param r the row number of the move square
	 * @param c the column of the move square
	 * @param dir the direction that we are checking
	 */
	public void makeFlip(BoardField player, int r, int c, int dir) {
		if(wouldFlip(player,r,c,dir)) {
			r+=dirR[dir];
			c+=dirC[dir];
			while(cBoard[r][c] != player) {
				cBoard[r][c] = player;
				r+=dirR[dir];
				c+=dirC[dir];
			}
		}
	}
	
	public boolean validMove(BoardField player,int r, int c) {
		if(cBoard[r][c] == BoardField.EMPTY) {
			for(int k=0; k<8; k++)
				if(wouldFlip(player,r,c,k)) {
					return true;
				}
		}
		return false;
	}
	
	public boolean anyLegalMove(BoardField player) {
		for(int i=1; i<=size; i++)
			for(int j=1; j<=size; j++)
				if(validMove(player,i,j))
					return true;
		return false;
	}
	
	/**
	 * This makes a copy of the current board, tries the move
	 * on it, and if the move is valid, copies the board back
	 * over the game-board
	 * 
	 * @param player A char noting the player making the move
	 * @param m A OthelloMove containing the details of the move
	 */
	public void makeMove(BoardField player, OthelloMove m) {
		int r = m.getRow();
		int c = m.getCol();
		if(validMove(player,r,c)) {
			cBoard[r][c] = player;
			for(int i=0; i<size; i++) {
				makeFlip(player,r,c,i);
			}			
		}
	}
	
	/**
	 * This returns a string that describes the board
	 * @return A string that describes the board
	 */
	public String boardToString(BoardField currentMove) {
		System.out.println(currentMove == BoardField.BLACK ? "Black's move.":"White's move.");
		var possibleMoves = generateMoves(currentMove);
		
		StringBuilder result;
		
		// initialise as  two spaces
		result = new StringBuilder("  ");
		
		// Now print out the numbers on the top row
		for (int i = 1; i <= size; i++) {
			result.append(i).append(" ");
		}
		result.append(newline);
		// Now, print out the current state of the table
		for (int y = 1; y <= size; y++) {
			result.append(y).append(" ");
			for (int x = 1; x <= size; x++) {
				String sign;
				if(cBoard[y][x] == BoardField.WHITE) {
					sign = ConsoleColors.WHITE_BRIGHT + "■" + ConsoleColors.RESET;
				} else if (cBoard[y][x] == BoardField.BLACK) {
					sign = ConsoleColors.BLACK + "■" + ConsoleColors.RESET;
				} else {
					int finalX = x;
					int finalY = y;
					if (possibleMoves.stream().anyMatch(move -> move.getCol() == finalX && move.getRow() == finalY)) {
						sign = ConsoleColors.WHITE + "▢" + ConsoleColors.RESET;
					} else {
						sign = ConsoleColors.WHITE + "·" + ConsoleColors.RESET;
					}
				}
				result.append(sign).append(" ");
			}
			// insert a newline
			result.append(newline);
		}
		
		return result.toString();
	}
	
	/**
	 * This generates a list of all possible moves from the current position
	 * 
	 * @param player A char, either BoardField.BLACK or BoardField.WHITE to identify the player taking the move
	 * @return An ArrayList of type OthelloMove with the list of all possible
	 * 			moves
	 */
	public ArrayList<OthelloMove> generateMoves(BoardField player) {
		ArrayList<OthelloMove> possibleMoves = new ArrayList<>();
		for(int i=1; i<=size; i++) {
			for(int j=1; j<=size; j++) {
				if(validMove(player,i,j)) {
					OthelloMove aMove = new OthelloMove(i,j);
					possibleMoves.add(aMove);
				}
			}
		}
		return possibleMoves;
	} // end of generateMoves
	
	/**
	 * This creates a copy of the current state of the board
	 * and returns it as a 2 dimensional array of type int[row][col].
	 * Useful for manipulation of the board and checking of possibilities
	 * 
	 * @return A 2 dimensional array of type char[row][col]
	 */
	public BoardField[][] getBoardCopy() {
		
		// copy of the board state
		BoardField[][] newBoard = new BoardField[size+2][size+2];
		
		// replicate the board in a new array. Must be done
		// because arrayClone does not deep copy multidimensional arrays
		for (int i = 0; i < size+2; i++)
			System.arraycopy(cBoard[i], 0, newBoard[i], 0, size);
		
		return newBoard;
	} // end of getBoardCopy
	
	/**
	 * Plays one game of Othello with the specified two players
	 * 
	 * @param p1 A Player object that will be player 1
	 * @param p2 A Player object that will be player 2
	 */
	public void play(Player p1, Player p2) {
		p1.initialize(BoardField.BLACK);
		p2.initialize(BoardField.WHITE);
		BoardField currentMove = BoardField.BLACK;
		
		while (true) {
			OthelloMove move;

			System.out.println(boardToString(currentMove));
			
			if (currentMove != BoardField.WHITE) {

				move = p1.makeMove(this);
				if (move.noMoves()) {
					if(anyLegalMove(p2.colour)) {
						System.out.println(p1.name + "'s (Black) move.");
						if(move.gameOver()) {
							System.out.println(p1.name + " concedes. Game Over!\n");
							break;
						}
						else {
							System.out.println("No valid moves. "+ p1.name + " must pass.");
							currentMove = BoardField.WHITE;
						}
					}
					else {
						System.out.println("Game over!\n");
						int difference = countSymbol(p1.colour) - countSymbol(p2.colour);
						if(difference<0)
							System.out.println(p2.name + " is the winner.");
						else System.out.println(p1.name + " is the winner.");
						break;
					}
				}
				else {
					// Make the move
					System.out.println(p1.name + "'s (Black) move.");
					makeMove(p1.colour, move);
					// Show the board
					String moveString = "The move is    " + move.getRow() + ", " +
							move.getCol();
					// Print the move
					System.out.println(moveString);

					currentMove = BoardField.WHITE;
					continue;
				}
			} else if (currentMove != BoardField.BLACK) {
				move = p2.makeMove(this);
				
				/* handle the situation where human player concede or no valid moves and must pass or game over*/
				if (move.noMoves()) {
					if(anyLegalMove(p1.colour)) {
						System.out.println(p2.name + "'s (White) move.");
						if(move.gameOver()) {
							System.out.println(p1.name + " concedes. Game Over!\n");
							break;
						}
						else {
							System.out.println("No valid moves." + p2.name + " must pass. ");
							currentMove = BoardField.BLACK;
						}
					}
					else {
						System.out.println("Game over!\n");
						int difference = countSymbol(p1.colour) - countSymbol(p2.colour);
						if(difference<0)
							System.out.println(p2.name + " is the winner.");
						else System.out.println(p1.name + "is the winner.");
						break;
					}
				}
				else {
					// Make the move
					System.out.println(p2.name + "'s (White) move.");
					makeMove(p2.colour, move);
					currentMove = BoardField.BLACK;
					// Show the board
					String moveString = "The move is " + move.getRow() + ", " +
							move.getCol();
					// Print the move
					System.out.println(moveString);
				}
			}
			
		}

	} // end of playOneGame
	
} // End of Othello Class
