/***************************************************************************
*@note
*  The UnboundedInt class allows for the addition and multiplication of values
*  that are too large to be stored with conventional data types such as an 
*  integer or double.
*
*@authors 
*  Frank Abbey and Maia Gray
*
*@version
*  March 22, 2019
***************************************************************************/
public class UnboundedInt implements Cloneable {
/** Invariant of the UnboundedInt class:
      1. "totalNodes" represents the amount of nodes in the UnboundedInt
      2. "front" refers to the front of the list (note - this would mean the 
         least significant value--ex: 18,965 is stored as [965]->[018] )
      3. "cursor" is used as an iterator, and represents the current node
      4. "back" refers to the tail of the list (note - this would mean the
         most significant value)
      5. each IntNode contains 1 to 3 digits, the max value being 999, "NODE_MAX" 
         is set to one more than this to make loops easier to read
**/
   private int totalNodes;
   private IntNode front;
   private IntNode cursor;
   private IntNode back;
   private final int NODE_MAX = 1000;
   
   /**
   * No-arg constructor initilizes UnboundedInt to "0"
   * @param 
   *     none
   * @postcondition
   *     The value in the only node of the UnboudedInt is 0.
   **/
   public UnboundedInt() {
      this("0");  
   }

   /**
   * Pulls apart the String "input" into nodes of 3 digits, each representing
   * an increasing significance (ex : "358459145" translates to [145]->[459]->[358] )
   * @param String input
   *     the number to be represented as the new UnboundedInt
   * @postcondition
   *     "totalNodes" in the UnboundedInt now represents the number
   *     of nodes in the UnboundedInt, also all leading zeros are eliminated
   **/
   public UnboundedInt(String input) {
      //this while loop eliminates leading zeros
      String alteredInput;
      int index = 0;
      while(input.charAt(index) == '0' && input.length() != 1) {
         index++;      
      }
      alteredInput = input.substring(index);
    
      totalNodes = 0;
      //for loop decrements by 3 to pull 3 charachters at a time          
      for(int i = alteredInput.length() - 1; i >= 0; i -= 3) {
      
         int contents;
         //if there is only one "number", 3 digits
         if(alteredInput.length() <= 3) {
            contents = Integer.parseInt(alteredInput.substring(0, alteredInput.length()));
            front = new IntNode(contents, front);
            this.start();
            totalNodes++;
         }
         //if there are more than 3 digits, start here
         else if(i == alteredInput.length() - 1) {
            contents = Integer.parseInt(alteredInput.substring(i - 2, i + 1));
            front = new IntNode(contents, front);
            this.start();
            totalNodes++;
         } 
         
         else if(i >= 3) {
            contents = Integer.parseInt(alteredInput.substring(i - 2, i + 1));
            cursor.addNodeAfter(contents);
            cursor = cursor.getLink();
            totalNodes++;
         }
         
         else {
            contents = Integer.parseInt(alteredInput.substring(0, i + 1)); 
            cursor.addNodeAfter(contents);
            //finally, initialize back;
            back = cursor; 
            totalNodes++; 
         } 
      }
      
   }
   
   /**
   * Adds two UnboundedInts and returns the sum as a new UnboundedInt
   *
   * @param UnboundedInt given
   *     the UnboundedInt to be added to the called object
   * @precondition
   *     neither of the UnboundedInt references are equal to 'null'
   * @postcondition
   *     The total number of nodes in the UnboundedInt now represents the number
   *     of nodes in the UnboundedInt
   * @throws IllegalStateException
   *     thrown if either reference is 'null'
   * @return UnboundedInt result
   *     an UnboudedInt representing the sum of the two numbers
   **/
   public UnboundedInt add(UnboundedInt given) {
   
      //"larger" reference will be needed to add remaining nodes
      UnboundedInt larger = null;
      int sum = 0;
      final int CARRY = 1;
      boolean isCarry = false;
      StringBuilder part = new StringBuilder("");
      //endPoint will represent the length of the shorter list
      int endPoint;
            
      //this statement is added safety, as a "NullPointerException" will 
      //be thrown if a method is attempted on a null reference
      if(this == null || given == null) {
         throw new IllegalStateException("(!)Cannot add 'null' values.");
      }
      
      //if/else chain determines which list is longer
      if(given.totalNodes == this.totalNodes) {
         endPoint = this.totalNodes;
      }
      else if(given.totalNodes > this.totalNodes) {
         endPoint = this.totalNodes;
         larger = given;
      }
      else {
         endPoint = given.totalNodes;
         larger = this;
      }
      
      //initialize the cursors
      this.start();
      given.start();
      
      //FIRST for loop takes care of nodes that are "in-line" 
      for(int i = 0; i < endPoint; i++) {
      
         sum += cursor.getData() + given.cursor.getData();
         
         if(isCarry) {
            sum += CARRY;
            isCarry = false;
         }
         if(sum >= NODE_MAX) {
            isCarry = true;
            sum -= NODE_MAX;
            
            //if/else chain adds zeros to "part" string builder for correct output
            if(sum < 100 && sum > 10) {
               part.insert(0, sum);
               part.insert(0, '0');
            }
            else if(sum < 10) {
               part.insert(0, sum);
               part.insert(0, "00");
            }
            else
               part.insert(0, sum);
                
         }   
         else {
            part.insert(0, sum);
            //adding in zeros to "part" string builder for correct output
            if(sum < 100 && sum > 10) 
               part.insert(0, '0');
            
            if(sum < 10) 
               part.insert(0, "00");
         }
         
         //move cursor along
         cursor = cursor.getLink();
         given.cursor = given.cursor.getLink(); 
         sum = 0;  
                 
      }    
      
      //SECOND for loop handles the values to get carried over from 
      //larger Unbounded Int (if they are the same length, this is not executed)
      if(larger != null) {
         for(IntNode k = larger.cursor; k != null; k = k.getLink()) {
            //if there is still a carry from the previous operation
            if(isCarry) {
        
               part.insert(0, k.getData() + 1);       
               //adding in zeros to "part" string builder for correct output
               if( (k.getData() + 1) < 100 && (k.getData() + 1) > 10) {
                  part.insert(0, '0');
               }
               
               if(k.getData() + 1 < 10) {
                  part.insert(0, "00");
               }
               
               isCarry = false;
            }
            
            else {
               part.insert(0, k.getData());
              
               if(k.getData() < 100 && k.getData() > 10) 
                  part.insert(0, '0');
               
               if(k.getData() < 10) 
                  part.insert(0, "00");        
            } 
         }
      }
      //if there is still a carry and the nodes were the same length,
      //the SECOND for loop never gets processsed, and "isCarry" is still
      // true
      if(isCarry) 
         part.insert(0, '1');
   
      UnboundedInt result = new UnboundedInt(part.toString());
      
      return result;
   } 
  
   /**
   * Multiplies to UnboundedInt objects and stores the result in a new UnboundedInt
   *
   * @param UnbounedInt given
   *     the UnboundedInt to be multiplied to the called object
   * @precondition
   *     neither UnboundedInt objects are 'null'
   * @postcondition
   *     the UnboundedInt objects that are multuplied together are unchanged
   * @throws IllegalStateException
   *     thrown if either reference is 'null'
   * @return UnboundedInt unbounded_Sum;
   *     The product of the two UnboundedInt objects
   **/
   public UnboundedInt multiply(UnboundedInt given) {
   
      //this statement is added safety, as a "NullPointerException" will 
      //be thrown if a method is attempted on a null reference
      if(this == null || given == null) {
         throw new IllegalStateException("(!) Cannot add 'null' values.");
      }
       
      UnboundedInt unbounded_Sum = new UnboundedInt("0");
      UnboundedInt unbounded_Temp = null;
      
      //if either value is zero, this prevents an output of "000,000,000" etc.
      if((given.totalNodes == 1 && given.front.getData() == 0) || (this.totalNodes ==1 && this.front.getData() == 0))
         return unbounded_Sum;
      
      int currentProduct;
      //top and bottom count represent the "place" within a particular number
      //(ex : 100's place = 1, 100,000's place = 2, etc.)
      int topCount;
      int bottomCount = 0;
      
      //for length of bottom
      for(IntNode bottom = given.front; bottom != null; bottom = bottom.getLink()) {
         bottomCount++;
         topCount = 0;
         //for length of top
         for(IntNode top = this.front; top != null; top = top.getLink()) {
            topCount++;
            //multiplying a node in the "bottom" to every node in the top,
            //and then iterating along the bottom by one and repeating
            currentProduct = (bottom.getData() * top.getData()); 
            unbounded_Temp = new UnboundedInt(Integer.toString(currentProduct)); 
            unbounded_Temp = shift(unbounded_Temp, topCount, bottomCount);
            unbounded_Sum = unbounded_Sum.add(unbounded_Temp);
          
         }
         
      }
      
      return unbounded_Sum;
      
   }

   /**
   * "Shifts" the UnboundedInt by adding a zero to the front of the list, to represent 
   * a place holder (ex : if [437] is meant to represent 437,000, shift adds 0 to the 
   * front of the node, [000]->[437] )
   * @param UnboundedInt given
   *     the UnboundedInt needing adjustment 
   * @postcondition
   *     given now has a specific number of zeros placed at the head of its list based
   *     on the "topCount" and "botCount" values
   * @return UnboundedInt result
   *     The altered UnboundedInt
   **/
   public UnboundedInt shift(UnboundedInt given, int topCount, int bottomCount) {
   
      UnboundedInt result = new UnboundedInt();
      result = given;
      
      //"howManyTimes" keeps track of how many zero nodes
      //to add in, based on the position of the operation
      int howManyTimes = (topCount + bottomCount) - 2;
      
      //if these are the "least significant" nodes, do nothing
      if(howManyTimes == 0) {
         return result;
      }
      
      else {
         //each IntNode added will really only be a single zero, 
         //but the separation of the nodes represents "adding" "000" to the 
         //end of the IntNode when needed to proceed with addition
         for(int i = 0; i < howManyTimes; i++) {
            result.front = new IntNode(0, given.front);
            result.totalNodes++;
            
         }
      
      }

      return result;
      
   }
   
   /**
   * Places the cursor at the first node in the UnboundedInt (the least significant digits)
   * @param none
   * @postcondition
   *     the cursor is at the first node, "front" 
   **/
   public void start() {
   
      cursor = front;
      
   } 
   
   /**
   * Clone method to clone an UnboundedInt object. Overrides Object clone() method
   * @param none
   * @throws CloneNotSupportedException
   *     Thrown if class does not implement Cloneable
   * @return UnboundedInt clonedNum
   *     the cloned UnboundedInt
   **/
   public Object clone() {
     
      UnboundedInt clonedNum;
      
      try {
         clonedNum = (UnboundedInt) super.clone( );
      }
      catch (CloneNotSupportedException e) {
         throw new RuntimeException("This class does not implement Cloneable");
      }
      
      clonedNum.front = IntNode.listCopy(front);
      
      return clonedNum;
   }   
   
   /**
   * Equals method determines if two UnboundedInt objects are equal to one another
   * @param Object obj
   *     a generic Object type object
   * @return boolean
   *     true if equal, false if not  
   **/
   public boolean equals(Object obj) {
       
      if(obj instanceof UnboundedInt) {
         UnboundedInt current = (UnboundedInt) obj;
            
            if(totalNodes != current.totalNodes)
               return false;
               
            IntNode passed = current.front;
            for(IntNode called = front; called != null; called = called.getLink()) {
               if(called.getData() != passed.getData())
                  return false;
               passed = passed.getLink();
            }     
            
      }
      
      return true;
         
   }   
   
   /**
   * Prints the UnboundedInt object as a string with commas and the correct 
   * number of zero's 
   * @param none
   * @throws IllegalStateException
   *     Thrown if the UnboundedInt is empty 
   * @return
   *     the output which is the UnboundedInt object
   **/
   public String toString() {
   
      if(front == null) {
         throw new IllegalStateException("(!) The sequence is empty.");
      } 
      
      StringBuilder sBuilder = new StringBuilder("");
      int contents;
      
      for(IntNode i = front; i != null; i = i.getLink()) {
         contents = i.getData();
         sBuilder.insert(0, Integer.toString(contents));
         
         //adding in "dead" zeros
         if(contents < 100 && contents > 10 && i.getLink() != null)
            sBuilder.insert(0, '0');
         if(contents < 10 && i.getLink() != null)
            sBuilder.insert(0, "00");
         if (i.getLink() != null)
            sBuilder.insert(0, ',');
      }
      
      String output = sBuilder.toString();
    
      return output;
     
   }   

}//ends UnboundedInt class