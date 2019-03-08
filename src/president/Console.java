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
     * Writes in the console, depending if it's allowed or not.
     * @param string String to write in the console.
     */
    public static void write(String string) {
        if (textDisplay) {
            System.out.println(string);
        }
    }
    
    public static void setTextDisplay(boolean b) {
        textDisplay = b;
    }
    
    public static boolean isTextDisplayed() {
        return textDisplay;
    }
    
}
