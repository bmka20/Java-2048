/**
* Name: Brandon Adiele
* Pennkey: badiele
* Execution: java Board
*
* Description: Creates the game board, moves the blocks,
*              and checks if the user won or lost the game
**/
public class Board {
    private static GameBlock[][] gameBlocks;
    private int score;
    
    public Board(int score) {
        this.gameBlocks = new GameBlock[4][4]; //Makes 2d array of GameBlock objects
        this.score = score;
    }
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: Draws the board using PennDraw
    */
    public void drawBoard() {
        // Grid Size
        PennDraw.square(0.5, 0.5, 0.4);
        // Horizontal Lines
        PennDraw.line(0.1, (0.8 / 4) + 0.1, 0.9, (0.8 / 4) + 0.1);
        PennDraw.line(0.1, (0.8 / 2) + 0.1, 0.9, (0.8 / 2) + 0.1);
        PennDraw.line(0.1, ((0.8 / 4) * 3) + 0.1, 0.9, ((0.8 / 4) * 3) + 0.1);
        // Vertical Lines
        PennDraw.line((0.8 / 4) + 0.1, 0.9, (0.8 / 4) + 0.1, 0.1);
        PennDraw.line((0.8 / 2) + 0.1, 0.9, (0.8 / 2) + 0.1, 0.1);
        PennDraw.line(((0.8 / 4) * 3) + 0.1, 0.9, ((0.8 / 4) * 3) + 0.1, 0.1);
        // Move Tracker
        PennDraw.text(0.2, 0.95, "# of Moves: " + score);
    }
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: Fills the board with game blocks/sets the board to a 2d array.
    *              All of the blocks start with a value of 0. 
    */
    public void fillGameBoard() {
        double y = 0.8;
        for (int i = 0; i < gameBlocks.length; i++) {
            double x = 0.2; // x is here because I want to add from 0.2 each row
            for (int j = 0; j < gameBlocks[i].length; j++) {
                gameBlocks[i][j] = new GameBlock(x, y, 0);
                x += 0.2;
            }
            y -= 0.2;
        }
        // Starts the game with two random blocks while keeping the score at 0
        addBlock();
        this.score = 0; // Need because addBlocks adds 1 to score
        addBlock();
        this.score = 0;
    }
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: Adds a random block to the board/adds a random value 
    *              to an unoccupied spot in the 2d array
    */
    public void addBlock() {
        // varibles that get a new random number each time this method is called
        int randomBlock = (int) (Math.random() * 10);
        int randomX = (int) (Math.random() * 4);
        int randomY = (int) (Math.random() * 4);
        int counter = 0;
        /**
        * Need this nested loop so the method doesn't infinetly loop. Without this,
        * I keept running into an infinite loop with the recursive call at the end. 
        * This makes sure that there's an unoccupied spot(block = to 0) on the board
        */
        for (int i = 0; i < gameBlocks.length; i++) {
            for (int j = 0; j < gameBlocks[i].length; j++) {
                if (gameBlocks[i][j].getBlockValue() == 0) {
                    counter++;
                }
            }
        }
        if (counter > 0) {
            // inserts the random 0 - 3 varibles into the 2d slots 
            if (gameBlocks[randomY][randomX].getBlockValue() == 0) {
                if (randomBlock > 0) { // More likely to add a random 2 than a 4
                    gameBlocks[randomY][randomX].setBlockValue(2);
                } else {
                    gameBlocks[randomY][randomX].setBlockValue(4);
                }
                updateBoard();
            } else {
                addBlock();
            }
        }
    }
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: Updates the board by redrawing everying with a new score.
    */
    public void updateBoard() {
        PennDraw.clear();
        PennDraw.text(0.2, 0.95, "# of Moves: " + score);
        for (int y = 0; y < gameBlocks.length; y++) {
            for (int x = 0; x < gameBlocks[y].length; x++) {
                gameBlocks[y][x].drawBlock();
            }
        }
        increaseScore(); // Method to increase score by 1
    }
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: Slides all of the blocks in the 2d array to the right. Does this
    *              by starting at the right most block and going backwards, 
    *              comparing the values of the block next to it. If there's a non 
    *              zero block to the left of a zero block, then the zero block's 
    *              value is set to the left block's value. Then the left block's 
    *              value becomes zero. This makes the game look like blocks are 
    *              sliding, but the values of blocks are just changing & only non 
    *              zero blocks appear. Left, up, and down have very similar logic, 
    *              just iterate through the 2d array differently. 
    */
    public void right() {
        for (int i = 0; i < gameBlocks.length; i++) {
            // conditional method to check if blocks can still move.
            while (doneYet(gameBlocks)) {  
                // compares right most block to left one
                for (int j = 3; j >= 1; j--) {
                    // only moves block if the one left of it is 0 
                    while (gameBlocks[i][j - 1].getBlockValue() > 0 && 
                           gameBlocks[i][j].getBlockValue() == 0) {
                        // Blocks don't literally "move." The values just change
                        gameBlocks[i][j].setBlockValue(gameBlocks[i][j - 1].
                                                       getBlockValue());
                        gameBlocks[i][j - 1].setBlockValue(0); // 0 blocks dont show
                    }
                }
            }
            rightHelper(gameBlocks);
        }
        // Need these helper functions to finish combining and shifting 
        combineRight(gameBlocks); 
        rightHelper(gameBlocks);
    }
    
    /**
    * Inputs: 2d array of type GameBlock
    * Outputs: N/A 
    * Description: Combines the blocks to the right if block values are equal
    */
    public void combineRight(GameBlock[][] arr) {
        for (int i = 0; i < gameBlocks.length; i++) {
            for (int j = 3; j >= 1; j--) {
                // checks to see if the blockValues are the same.
                if (arr[i][j].compareBlock(arr[i][j - 1])) {
                arr[i][j].setBlockValue(arr[i][j].getBlockValue() * 2);
                arr[i][j - 1].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: A boolean 
* Description: Checks to see if any blocks can be combined to the right 
*/
public boolean combineRightCheck(GameBlock[][] arr) {
    // Counter only goes up if blocks can combine
    int counter = 0;
    for (int i = 0; i < gameBlocks.length; i++) {
        for (int j = 3; j >= 1; j--) {
            if (arr[i][j].compareBlock(arr[i][j - 1])) { 
            counter++;
        }
    }
}
    if (counter > 0) { // returns false if at least 1 pair of blocks can combine
        return false;
    }
    return true;
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A
* Description: Helper function that repeats logic used in right method 
*/
public void rightHelper(GameBlock[][] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 3; j >= 1; j--) {
            while (arr[i][j - 1].getBlockValue() > 0 && 
                   arr[i][j].getBlockValue() == 0) {
                arr[i][j].setBlockValue(arr[i][j - 1].getBlockValue());
                arr[i][j - 1].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: N/A
* Outputs: N/A
* Description: Slides all of the blocks in the 2d array to the left.
*/
public static void left() {
    for (int i = 0; i < gameBlocks.length; i++) {
        while (doneYet(gameBlocks)) {
            // Similar but opposite logic to right method 
            for (int j = 0; j < gameBlocks[i].length - 1; j++) {
                while (gameBlocks[i][j + 1].getBlockValue() > 0 && 
                       gameBlocks[i][j].getBlockValue() == 0) {
                    gameBlocks[i][j].setBlockValue(gameBlocks[i][j + 1].
                                                   getBlockValue());
                    gameBlocks[i][j + 1].setBlockValue(0);
                }
            }
        }
        leftHelper(gameBlocks);
    }
    combineLeft(gameBlocks);
    leftHelper(gameBlocks);
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A
* Description: Helper function for left method that repeats same logic
*/
public static void leftHelper(GameBlock[][] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length - 1; j++) {
            while (arr[i][j + 1].getBlockValue() > 0 && 
                   arr[i][j].getBlockValue() == 0) {
                arr[i][j].setBlockValue(arr[i][j + 1].getBlockValue());
                arr[i][j + 1].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A
* Description: Helper function to combine blocks in left direction
*/
public static void combineLeft(GameBlock[][] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length - 1; j++) {
            if (arr[i][j].compareBlock(arr[i][j + 1])) {
                arr[i][j].setBlockValue(arr[i][j].getBlockValue() * 2);
                arr[i][j + 1].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: A boolean
* Description: Helper function to check if the blocks are done shifting horizontally
*/
public static boolean doneYet(GameBlock[][] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length - 2; j++) {
            // Checking to see if a 0 block is sandwiched between two non zeros 
            if (arr[i][j].getBlockValue() > 0 && 
                arr[i][j + 1].getBlockValue() == 0 && 
                arr[i][j + 2].getBlockValue() > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
    return true;
}

/**
* Inputs: N/A
* Outputs: N/A
* Description: Slides all of the blocks in the 2d array to the top. Iterates 
*              through the 2d array vertically rather than horizontally
*/
public void up() {
    // reversing i & j to go through array vertically. 
    for (int j = 0; j < gameBlocks.length; j++) {
        while (doneYetUp(gameBlocks)) {
            for (int i = 0; i < gameBlocks[j].length - 1; i++) {
                while (gameBlocks[i + 1][j].getBlockValue() > 0 && 
                       gameBlocks[i][j].getBlockValue() == 0) {
                    gameBlocks[i][j].setBlockValue(gameBlocks[i + 1][j].
                                                   getBlockValue());
                    gameBlocks[i + 1][j].setBlockValue(0);
                }
            }
        }
        upHelper(gameBlocks);
    }
    combineUp(gameBlocks);
    upHelper(gameBlocks);
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A
* Description: Helper function that repeats logic used in up method.
*/
public void upHelper(GameBlock[][] arr) {
    for (int j = 0; j < arr.length; j++) {
        for (int i = 0; i < arr.length - 1; i++) {
            while (arr[i + 1][j].getBlockValue() > 0 && 
                   arr[i][j].getBlockValue() == 0) {
                arr[i][j].setBlockValue(arr[i + 1][j].getBlockValue());
                arr[i + 1][j].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A 
* Description: Combines the blocks upwards if they have the same block value 
*/
public void combineUp(GameBlock[][] arr) {
    for (int j = 0; j < arr.length; j++) {
        for (int i = 0; i < arr[j].length - 1; i++) {
            if (arr[i][j].compareBlock(arr[i + 1][j])) { 
                arr[i][j].setBlockValue(arr[i][j].getBlockValue() * 2);
                arr[i + 1][j].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: A boolean 
* Description: Checks if the blocks can be combined upwards
*/
public boolean combineUpCheck(GameBlock[][] arr) {
    int counter = 0;
    for (int j = 0; j < arr.length; j++) {
        for (int i = 0; i < arr[j].length - 1; i++) {
            if (arr[i][j].compareBlock(arr[i + 1][j])) {
                counter++;
            }
        }
    }
    if (counter > 0) {
        return false;
    } else {
        return true;
    }
}

/**
* Inputs: N/A
* Outputs: N/A
* Description: Slides all of the blocks in the 2d array to the bottom.
*/
public void down() {
    for (int j = 0; j < gameBlocks.length; j++) {
        while (doneYetDown(gameBlocks)) {
            for (int i = 3; i >= 1; i--) {
                while (gameBlocks[i - 1][j].getBlockValue() > 0 && 
                       gameBlocks[i][j].getBlockValue() == 0) {
                    gameBlocks[i][j].setBlockValue(gameBlocks[i - 1][j].
                                                   getBlockValue());
                    gameBlocks[i - 1][j].setBlockValue(0);
                }
            }
        }
        downHelper(gameBlocks);
    }
    combineDown(gameBlocks);
    downHelper(gameBlocks);
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A
* Description: Helper function that repeats logic used in down method 
*/
public void downHelper(GameBlock[][] arr) {
    for (int j = 0; j < arr.length; j++) {
        for (int i = 3; i >= 1; i--) {
            while (arr[i - 1][j].getBlockValue() > 0 && 
                   arr[i][j].getBlockValue() == 0) {
                arr[i][j].setBlockValue(arr[i - 1][j].getBlockValue());
                arr[i - 1][j].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: N/A
* Description: Combines the blocks downward if they have the same block value 
*/
public void combineDown(GameBlock[][] arr) {
    for (int j = 0; j < arr.length; j++) {
        for (int i = 3; i >= 1; i--) {
            if (arr[i][j].compareBlock(arr[i - 1][j])) {
                arr[i][j].setBlockValue(arr[i][j].getBlockValue() * 2);
                arr[i - 1][j].setBlockValue(0);
            }
        }
    }
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: A boolean 
* Description: Helps check to see if the blocks are done shifting down
*/
public boolean doneYetDown(GameBlock[][] arr) {
    for (int j = 0; j < arr.length; j++) {
        for (int i = 0; i < arr[j].length - 2; i++) {
            if (arr[i][j].getBlockValue() > 0 && 
                arr[i + 1][j].getBlockValue() == 0 && 
                arr[i + 2][j].getBlockValue() > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
    return true;
}

/**
* Inputs: 2d array of type GameBlock
* Outputs: A boolean
* Description: Helps check to see if the blocks are done shifting up
*/
public boolean doneYetUp(GameBlock[][] arr) {
    for (int j = 0; j < arr.length; j++) {
        for (int i = 3; i >= 2; i--) {
            if (arr[i][j].getBlockValue() > 0 && 
                arr[i - 1][j].getBlockValue() == 0 && 
                arr[i - 2][j].getBlockValue() > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
    return true;
}

/**
* Inputs: N/A
* Outputs: The score
* Description: Gets the score of the board 
*/
public int getScore() {
    return this.score;
}

/**
* Inputs: The score
* Outputs: N/A
* Description: Sets the score of the board
*/
public void setScore(int score) {
    this.score = score;
}

/**
* Inputs: N/A
* Outputs: N/A
* Description: Increases the current score by 1
*/
public void increaseScore() {
    this.score++;
}

/**
* Inputs: N/A
* Outputs: A boolean
* Description: Checks to see if the user lost. This method determines that the user 
*              lost the game if there are no 0 value blocks (full board), the user 
*              can't combine upwards & the user can't combine blocks to the right 
*/
public boolean checkIfLost() {
    int counter = 0;
    for (int i = 0; i < gameBlocks.length; i++) {
        for (int j = 0; j < gameBlocks[i].length; j++) {
            if (gameBlocks[i][j].getBlockValue() == 0) {
                counter++;
            }
        }
    }
    if (counter > 0) {
        return true;
    } else if (counter == 0 && combineUpCheck(gameBlocks) && 
               combineRightCheck(gameBlocks)) {
        return false;
    }
    return true;
}

/**
* Inputs: N/A
* Outputs: A boolean 
* Description: Checks to see if the user one. Constantly checks every value 
*              of the blocks on the board to see if there's a 2048 block 
*/
public boolean checkIfWon() {
    int counter = 0;
    for (int i = 0; i < gameBlocks.length; i++) {
        for (int j = 0; j < gameBlocks[i].length; j++) {
            if (gameBlocks[i][j].getBlockValue() == 2048) { 
                counter++;
            }
        }
    }
    if (counter > 0) {
        return false;
    }
    return true;
}
    
}
