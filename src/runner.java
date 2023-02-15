public class runner{
    public static void main(String[] args) {
        Board board = new Board();
        ConnectFour game = new ConnectFour(board);
        game.setPlayer1(new AIPlayer('X', board, "AI"));
        game.setPlayer2(new HumanPlayer('Y', board, "Alice"));
        game.playGame();
    }    
}
