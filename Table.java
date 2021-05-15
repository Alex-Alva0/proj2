import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti ordonez
*/
public class Table extends JFrame implements ActionListener
{
	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;
	JList p2HandPile;
	Deck cardDeck;
	Deck stackDeck;

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	DefaultListModel p1Hand;
	DefaultListModel p2Hand;

	private void deal(Card [] cards)
	{
		for(int i = 0; i < cards.length; i ++)
			cards[i] = (Card)cardDeck.dealCard();
	}
	public Table()
	{
		super("The Card Game of the Century");

		setLayout(new BorderLayout());
		setSize(1200,700);


		cardDeck = new Deck();

		for(int i = 0; i < Card.suit.length; i++){
			for(int j = 0; j < Card.rank.length; j++){
				Card card = new Card(Card.suit[i],Card.rank[j]);
				cardDeck.addCard(card);
			}
		}
		cardDeck.shuffle();
		stackDeck = new Deck();

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));

		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);

		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();

		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();

		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);

		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay  ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);


		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		p1Hand = new DefaultListModel();
		for(int i = 0; i < cardsPlayer1.length; i++)
			p1Hand.addElement(cardsPlayer1[i]);
		p1HandPile = new JList(p1Hand);


		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack));

		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay  ");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);

		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		p2Hand = new DefaultListModel();

		for(int i = 0; i < cardsPlayer2.length; i++)
			p2Hand.addElement(cardsPlayer2[i]);

		p2HandPile = new JList(p2Hand);

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack));

		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);

		System.out.println("PLayer1: " + p1Hand);
		System.out.println("PLayer2: " + p2Hand);
	}

	int turns = 0; 
	public void actionPerformed(ActionEvent e)
	{

		Object src = e.getSource();
		if(turns%2==0){
			player1Turn(src);
		}
		if(turns%2==1){
			player2Turn(src);
		}
	}

	public static void main(String args[])
	{
		Table t = new Table();
		t.setVisible(true);
	}

	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex = Card.getSuitIndex(suit);
		int rankIndex = Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

	void pickedCard(Card card)
	{
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("Added:  " + card);
	}
	void discardCard(Card card)
	{
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("Discarded:  " + card);
	}

	void updateTurn(){
		turns+=1;
	}

	
	void chooseCardPlayer1(Object src){
		if(src == p1Deck){
			Card card = cardDeck.dealCard();
			if (card != null){
				p1Hand.addElement(card);
				pickedCard(card);
			}
			if(cardDeck.getSizeOfDeck() == 0){
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
			}
		}
		if(p1Stack == src)
		{
			Card card = stackDeck.removeCard();
			if(card != null)
			{			
				Card topCard = stackDeck.peek();
				if (topCard != null)
				{
					topOfStack.setIcon(topCard.getCardImage());
				}
				else{
					topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				}
				p1Hand.addElement(card);
				pickedCard(card);	
			}
		}
	}

	void chooseCardPlayer2(Object src){
		if(src == p2Deck){
			Card card = cardDeck.dealCard();
			if (card != null){
				p2Hand.addElement(card);
				pickedCard(card);
			}
			if(cardDeck.getSizeOfDeck() == 0){
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
			}
		}
		
		if(p2Stack == src)
		{
			Card card = stackDeck.removeCard();
			if(card != null)
			{				
				Card topCard = stackDeck.peek();
				if (topCard != null)
				{
					topOfStack.setIcon(topCard.getCardImage());
				}
				else{
					topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				}
				p2Hand.addElement(card);
				pickedCard(card);
			}
		}
	}
	

	void endTurnP1(Object src){
		if(p1LayOnStack == src){
			int [] num  = p1HandPile.getSelectedIndices();
			if (num.length == 1)
			{
				Object obj = p1HandPile.getSelectedValue();
				if (obj != null)
				{
					p1Hand.removeElement(obj);
					Card card = (Card)obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());
					discardCard(card);
				}
			}
			System.out.println("PLayer1 now: " + p1Hand);
			updateTurn();
		}
	}
	
	void endTurnP2(Object src){
		if(p2LayOnStack == src){
			int [] num  = p2HandPile.getSelectedIndices();
			if (num.length == 1)
			{
				Object obj = p2HandPile.getSelectedValue();
				if (obj != null)
				{
					p2Hand.removeElement(obj);
					Card card = (Card)obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());
					discardCard(card);
				}
			}
			System.out.println("PLayer2 now: " + p2Hand);
			updateTurn();
		}
	}

	void player2Turn(Object src){ 
		System.out.println("Player 2: ");
		chooseCardPlayer2(src);
		if(p2Lay == src){
			Object [] cards = p2HandPile.getSelectedValues();
			if (cards != null){
				for(int i = 0; i < cards.length; i++)
				{
					Card card = (Card)cards[i];
					p2Hand.removeElement(card);
					layCard(card);
					discardCard(card);
				}
			}
		}
		endTurnP2(src);
	}

	void player1Turn(Object src){ 
		System.out.println("Player 1: "); 
		chooseCardPlayer1(src);
		
		if(p1Lay == src){
			Object [] cards = p1HandPile.getSelectedValues();
			if (cards != null){
				for(int i = 0; i < cards.length; i++)
				{
					Card card = (Card)cards[i];
					p1Hand.removeElement(card);
					layCard(card);
					discardCard(card);
				}
			}
		}

		endTurnP1(src);
	}
}
class HandPanel extends JPanel
{

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}
class SetPanel extends JPanel
{
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}

}
