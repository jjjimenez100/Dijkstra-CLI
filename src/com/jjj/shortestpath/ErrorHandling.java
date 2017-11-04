package com.jjj.shortestpath;
/**
 * This class serves as the error handling/input validation mechanism of the program.
 * @author JJJimenez
 */
import java.util.InputMismatchException;
import java.util.Scanner;
public class ErrorHandling {
    private int numInput = 0;
    private String strInput = "";
    private Scanner reader = new Scanner(System.in);
     /**
     * Method that checks for the validity of user inputs.
     * @param type the datatype of input to be tested; 1 - int , 2 - string
     * @return true if user input is valid
     */
    public boolean isValidInput(int type){
        try {
            if(type==1){
                numInput = reader.nextInt();
            }             
            else if(type==2){
                strInput = reader.next();
            }  
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input. Try again.");
            reader.next();
            return false;
        }    
    }
    /**
     * Method that returns the validated integer input.
     * @return a valid integer input
     */
    public int getNumInput(){
        return numInput;
    }
    /**
     * Method that returns the validated string input.
     * @return a valid string input
     */
    public String getStrInput(){
        return strInput;
    }
}
