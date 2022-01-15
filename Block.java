/**
* Name: Brandon Adiele 
* Pennkey: badiele 
* Execution: N/A
*
* Description: Interface for GameBlock class
**/
public interface Block {
    void drawBlock();
    
    boolean compareBlock(GameBlock block);
    
    int getBlockValue();
    
    void setBlockValue(int value);
}
