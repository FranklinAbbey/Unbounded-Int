/*
*  Driver program to test and use UnboundedInt class
*  File    : LargeNumberTest.java
*  Authors : Frank Abbey, Maia Gray
*  Date    : 3/22/19
*/
import java.util.Scanner;
import java.util.InputMismatchException;

public class LargeNumberTest {

   public static void main( String[] args) {
   
   Scanner in = new Scanner(System.in);
   
   UnboundedInt firstLinkedNum = null;
   UnboundedInt secLinkedNum = null;
   
   System.out.println("\n      ~ ~ ~ Welcome to LargeNumberTest.java ~ ~ ~");
   System.out.println("\n This program performs simple operations with numbers too large\n to store as an integer or double value, referred to here\n as 'Unbounded Integers'");
      
      int choice = 0;
      while (choice != 7) {
       
         System.out.println("\n\n  ------Please select from the options below-----\n");
         System.out.println("1. Display both of the Unbounded Integers (with commas)");
         System.out.println("2. Input two new Unbounded Integers (without commas)");
         System.out.println("3. Check if the two numbers are equal");
         System.out.println("4. Report the sum of the two numbers");
         System.out.println("5. Report the product of the two numbers");
         System.out.println("6. Create and output the clone of the first number");
         System.out.println("7. QUIT");

         
         System.out.print("\nEnter your selection (number from 1 - 7) : ");
         try {
            choice = in.nextInt();
         }
         catch (InputMismatchException e) {
            System.out.println("(!) Invalid entry, enter only numbers!");
         } 
         System.out.println("\n");
         
         switch (choice) {
            //printing both UnboundedInt's
            case 1:
            
               try {
                  System.out.println("Unbounded Int #1 : " + firstLinkedNum);
               }
               catch(IllegalStateException e) {
                  System.out.println(e);
               }
               
               try {
                  System.out.println("Unbounded Int #2 : " + secLinkedNum);
               }
               catch(IllegalStateException e) {
                  System.out.println(e);
               }     
               break;
            //inputting new UnboundedInt values   
            case 2:
               
               try {
                  System.out.print("Please input the FIRST number (without commas): ");
                  String firstNum = in.next();
                  firstLinkedNum = new UnboundedInt(firstNum);    
               }
               catch(NumberFormatException e) {
                  System.out.println("(!) Invalid entry, enter only numbers!"); 
               }
                   
               try {
                  System.out.print("Please input the SECOND number (without commas): ");
                  String secondNum = in.next();
                  secLinkedNum = new UnboundedInt(secondNum);   
               }
               catch(NumberFormatException e) {
                  System.out.println("(!) Invalid entry, enter only numbers!"); 
               }  
               break;
            //determining equality   
            case 3:
               if(firstLinkedNum != null && secLinkedNum != null) {
                  try {
                     System.out.println("Are the two numbers equal? ---> " + firstLinkedNum.equals(secLinkedNum));
                  }
                  catch(IllegalStateException e) {
                     System.out.println(e);
                  }
               }
               else
                  System.out.println("(!) Cannot compare 'null' values.");
               break;
            //finding sum of two UnboundedInt's  
            case 4:
            
               if(firstLinkedNum != null && secLinkedNum != null) {
                  try {
                     System.out.println("  (#1) " + firstLinkedNum + "\n+ (#2) " + secLinkedNum + "\n-----------------------\n  (sum) " + firstLinkedNum.add(secLinkedNum));
                  }
                  catch(IllegalStateException e) {
                     System.out.println(e);
                  }  
               }
               else
                  System.out.println("(!) Cannot add 'null' values.");
               break;
            //finding product of two UnboundedInt's                    
            case 5:
            
               if(firstLinkedNum != null && secLinkedNum != null) {
                  try {
                     System.out.println("  (#1)  " + firstLinkedNum + "\nx (#2)  " + secLinkedNum + "\n-----------------------\n  (product) " + firstLinkedNum.multiply(secLinkedNum));
                  }
                  catch(IllegalStateException e) {
                     System.out.println(e);
                  }
               }
               else
                  System.out.println("(!) Cannot multiply 'null' values.");
               break;
            //cloning first UnboundedInt  
            case 6:
            
            if(firstLinkedNum != null && secLinkedNum != null) {
               try {
                  System.out.println("First Unbounded Int : " + firstLinkedNum + "\nClone : " + firstLinkedNum.clone());
               }
               catch(IllegalStateException e) {
                  System.out.println(e);
               }
            }
            else
               System.out.println("(!) Cannot clone 'null' values.");           
            break;
            //EXIT   
            case 7:
            
               System.out.println("exiting menu...\n");
               break;
            //defualt statement used here to take in more input after given
            //an illegal argument for "choice", such as a letter
            default :

               in.next();               
               break;
               
         }
         
      }//ends while loop   
      
      System.out.println("Thank you for using LargeNumberTest.java!");
         
   }//ends main method

}//ends LargeNumberTest class

