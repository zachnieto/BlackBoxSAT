import java.util.ArrayList;

public interface IHint {
  /**
   * 
   * @return a Boolean formula based off of this IHint. 
   */
  public String generate();

  public String generate(ArrayList<IHint> hints);

  /**
   *  
   * @return an x,y coordinate of the next position, moving in the given direction. 
   */
  public int[] getNextPosn(); 
  
  
  //public IHint makeNewPosn(); 
  
  
  /**
   * 
   * @return an x,y coordinate of the position we need to check for up deflection.s
   */
  public int[] getDeflect(int up);
  
  public boolean outOfBounds(int[] position); 

}
