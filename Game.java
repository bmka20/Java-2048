/**
* Name: Brandon Adiele
* Pennkey: badiele
* Execution: java Game
*
* Description: Stores a main function that enables it to run the entire game
**/
public class Game {
    public static void main(String[] args) {
        Board gameBoard = new Board(0);
        gameBoard.drawBoard();
        gameBoard.fillGameBoard();
        gameBoard.setScore(1);
        
        /*
         * Only run the game if the user hasn't lost or won yet. This 
         * is why I had the methods return false if they won or lost, so 
         * it would stop this while loop. 
        */
        while (gameBoard.checkIfWon() && gameBoard.checkIfLost()) {
            if (PennDraw.hasNextKeyTyped()) {
                char keyPressed = PennDraw.nextKeyTyped();
                PennDraw.enableAnimation(30);
                if (keyPressed == 'w') {
                    gameBoard.up();
                    gameBoard.addBlock();
                }
                if (keyPressed == 's') {
                    gameBoard.down();
                    gameBoard.addBlock();
                }
                if (keyPressed == 'a') {
                    gameBoard.left();
                    gameBoard.addBlock();
                }
                if (keyPressed == 'd') {
                    gameBoard.right();
                    gameBoard.addBlock();
                }
                PennDraw.advance();
            }
        }
        PennDraw.disableAnimation(); // Stops animation so I can show a message
        if (gameBoard.checkIfWon() == false) {
            PennDraw.setPenColor(PennDraw.BLACK);
            PennDraw.setFontSize(25);
            PennDraw.text(0.5, 0.5, "You Won in " + (gameBoard.getScore() - 1) + 
                          " moves!");
        }
        if (gameBoard.checkIfLost() == false) {
            PennDraw.setPenColor(PennDraw.BLACK);
            PennDraw.setFontSize(25);
            PennDraw.text(0.5, 0.5, "You Lost in " + (gameBoard.getScore() - 1) + 
                          " moves!");
        }
    }
    
}
