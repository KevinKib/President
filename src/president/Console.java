/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president;

/**
 *
 * @author maxim
 */
public class Console {
    
    private static boolean textDisplay = true;
    
    /**
     * Writes a classic message in the console, depending if it's allowed or not.
     * @param string String to write in the console.
     */
    public static void write(String string) {
        if (textDisplay) {
            System.out.println(string);
        }
    }
    
    /**
     * Writes an error message in the console, depending if it's allowed or not.
     * @param string String to write in the console.
     */
    public static void error(String string) {
        if (textDisplay) {
            System.err.println(string);
        }
    }
    
    public static void setTextDisplay(boolean b) {
        textDisplay = b;
    }
    
    public static boolean isTextDisplayed() {
        return textDisplay;
    }
    
}
