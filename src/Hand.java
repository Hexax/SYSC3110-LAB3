import java.util.*;

/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}

    private final List<Card> cards;

    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public Hand(String c) {
        String[] cardsChars = c.split(" ");
        cards = new ArrayList<>();
        for (String s : cardsChars){
            cards.add(new Card(s));
        }
    }
    
    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD TC TH 7C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */
    protected boolean hasNKind(int n) {
        for (Card.Rank r : Card.Rank.values()){
            int count = 0;
            for (Card c : cards) {
                if (c.getRank() == r) {
                    count++;
                }
            }
            if (count == n) return true;
        }
        return false;
    }
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
        return false;
    }   
    
    /**
     * @returns true if the hand is a straight 
     */
    public boolean isStraight() {
        int[] ranks = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++){
            ranks[i] = cards.get(i).getRank().ordinal();
        }
        Arrays.sort(ranks);

        if (ranks[0]==0 && ranks[1] == 1 && ranks[2] == 2 && ranks[3] == 3 && ranks[4] == 12) return true;

        for (int i = 0; i < ranks.length - 1; i++){
            if(ranks[i+1]!=ranks[i]+1){
                return false;
            }
        }
        return true;
    }
    
    /**
     * @returns true if the hand is a flush
     */
    public boolean isFlush() {
        Card.Suit s = cards.get(0).getSuit();
        for (Card c: cards){
            if (c.getSuit() != s) return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Hand h) {
        //hint: delegate!
		//and don't worry about breaking ties
        return this.kind().compareTo((h.kind()));
    }
    
    /**
	 * This method is already implemented and could be useful! 
     * @returns the "kind" of the hand: flush, full house, etc.
     */
    public Kind kind() {
        if (isStraight() && isFlush()) return Kind.STRAIGHT_FLUSH;
        else if (hasNKind(4)) return Kind.FOUR_OF_A_KIND; 
        else if (hasNKind(3) && hasNKind(2)) return Kind.FULL_HOUSE;
        else if (isFlush()) return Kind.FLUSH;
        else if (isStraight()) return Kind.STRAIGHT;
        else if (hasNKind(3)) return Kind.THREE_OF_A_KIND;
        else if (isTwoPair()) return Kind.TWO_PAIR;
        else if (hasNKind(2)) return Kind.PAIR; 
        else return Kind.HIGH_CARD;
    }

}
