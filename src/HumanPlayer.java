import java.util.Scanner; // for input

public class HumanPlayer extends Player {

    // constructor;
    public HumanPlayer(char symbol, Board board, String name) {
        super(symbol, board, name);
        board.setSym2(symbol); // allows board to access the symbol!
    }

    @Override
    public void makeMove(Board board) {
        int pos;
        boolean step;
        do {
            Scanner inp_move = new Scanner(System.in);
            System.out.print(name+", please input your move: ");
            pos = inp_move.nextInt();
            step =  board.place(pos, symbol);
        }
        while (step == false);
        
    }
    
}
