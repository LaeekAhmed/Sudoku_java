import java.util.Random;

public class AIPlayer extends Player {

    public AIPlayer(char symbol, Board board, String name) {
        super(symbol, board, name);
        board.setSym1(symbol); // allows board to tell player's the symbol!
        //TODO Auto-generated constructor stub
    }

    @Override
    public void makeMove(Board board) {
        // this line is solely required if both the players are AI's.
        char oppSym = (board.getSym2() != symbol) ? board.getSym2() : board.getSym1();
        boolean step;
        board.smartPos(symbol);
		int[] a1 = board.getPos(symbol).clone(); // simple assignment has side effects (ie changes)!
        board.smartPos(oppSym);
		int[] a2 = board.getPos(oppSym);
        int[] arr1 = {-1,0}; // default vaule (not 0,0 since it can be an actual case)

        // comparing ele manually since importing arrays lib isn't allowed;
        // System.out.println("PosB equal to {-1,0} ? : "+(a1[0]==arr1[0] && a1[1]==arr1[1])
		// +", posB = ["+a1[0]+','+a1[1]+"]");
        // System.out.println("PosR equal to {-1,0} ? : "+(a2[0]==arr1[0] && a2[1]==arr1[1])
		// +", posR = ["+a2[0]+','+a2[1]+"]");

        // winning move;
		if (!(a1[0]==arr1[0] && a1[1]==arr1[1])){

            board.place(a1[1]+1,symbol);
            System.out.println("Winning Move! ;");
        }
        /* Blocking move ;
        checking if pos_array != {-1,0}*/
		else if (!(a2[0]==arr1[0] && a2[1]==arr1[1])){
            board.place(a2[1]+1,symbol);
            System.out.println("Blocking move! ;");
        }
        else{
            do {
                System.out.println("Performing random move;");
                Random rand = new Random(); //instance of random class
                //generate random values from [0,7)
                int pos = rand.nextInt(7)+1; // for place ; [1,7]
                step =  board.place(pos, symbol);
            }
            while (step == false);

        }
        
    }
    
}
