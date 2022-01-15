/**
* Name: Brandon Adiele 
* Pennkey: badiele
* Execution: java GameBlock
* 
* Description: Creates GameBlock objects. Also able to draw the objects
*              at a certain position and compare their values. 
**/
public class GameBlock implements Block {
    private int blockValue;
    private double x;
    private double y;
    private final double blockSize = 0.1;
    
    public GameBlock(double x, double y, int blockValue) {
        this.x = x;
        this.y = y;
        this.blockValue = blockValue;
    }

    /**
     * Inputs: N/A
     * Outputs: N/A
     * Description: This method draws the block to its x and y coordinate. 
     *              The block's color depends the block's value.
     *              The text that shows the value of the block doesn't appear on 
     *              blocks with a value of zero. 
    */
    @Override
    public void drawBlock() {
        PennDraw.square(x, y, this.blockSize);
        if (this.blockValue > 0) {
            if (blockValue == 2) {
                PennDraw.setPenColor(255, 255, 204);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 4) {
                PennDraw.setPenColor(204, 255, 153);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 8) {
                PennDraw.setPenColor(102, 255, 102);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 16) {
                PennDraw.setPenColor(142, 198, 196);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 32) {
                PennDraw.setPenColor(51, 255, 153);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 64) {
                PennDraw.setPenColor(0, 255, 255);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 128) {
                PennDraw.setPenColor(0, 102, 204);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 256) {
                PennDraw.setPenColor(76, 0, 153);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 512) {
                PennDraw.setPenColor(102, 0, 51);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 1024) {
                PennDraw.setPenColor(198, 74, 102);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else if (blockValue == 2048) {
                PennDraw.setPenColor(240, 6, 45);
                PennDraw.filledSquare(x, y, this.blockSize);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(x, y, "" + this.blockValue);
            } else {
                PennDraw.square(x, y, this.blockSize);
            }
        }
    }

    /**
     * Inputs: A GameBlock object
     * Outputs: A boolean 
     * Description: Checks to see if the value of the GameBlocks are the same
    */
    @Override
    public boolean compareBlock(GameBlock block) {
        return this.blockValue == block.blockValue;
    }
    
    /**
     * Inputs: N/A
     * Outputs: An integer 
     * Description: Returns the value of the block
    */
    @Override
    public int getBlockValue() {
        return this.blockValue;
    }
    
    /**
     * Inputs: An integer 
     * Outputs: N/A
     * Description: Sets the value of the block 
    */
    @Override
    public void setBlockValue(int value) {
        this.blockValue = value;
    }
    
}
