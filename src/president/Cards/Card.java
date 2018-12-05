/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.Cards;

/**
 *
 * @author Kévin
 */
public abstract class Card {
    
    private String name;
    private Suit suit;
    private Integer value;
    
    public Card(String name, Suit suit, Integer value) {
        this.name = name;
        this.suit = suit;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Suit getSuit() {
        return suit;
    }

    public Integer getValue() {
        return value;
    }

    public String toString() {
        return name + suit;
    }
    
}
