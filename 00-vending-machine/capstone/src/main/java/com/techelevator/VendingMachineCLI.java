//////////////////////////////////////////
//	  MAIN METHOD AND HUB FOR ACTION    //
//										//
//	This is were all methods are        //
//  eventually called. This is where    //
//  the main menu is an most of the     //
//  user interaction is happening.      //
//										//
//////////////////////////////////////////

package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {
///////////////////////////////////////////////////////////////	
// THESE ARE THE GIVEN MENU OPTIONS THAT THE USER SEES FIRST//
/////////////////////////////////////////////////////////////	
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Menu";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT };
	
	boolean keepShopping = true;
	private boolean menuBreak = true;
////////////////////////////////////////////////////////////////////////	
//THESE ARE THE OBJECTS AND VARIABLES USED THROUGHT THIS PAGE OF CODE//
//////////////////////////////////////////////////////////////////////	
	private BigDecimal selectedItemPrice;
	private int theIndex;
	private String itemSelection;
	private Menu menu;
	Scanner customerInput = new Scanner(System.in);
	CashRegister cashRegister = new CashRegister();
	Inventory inventorychoice = new Inventory();
	BookKeeping booking = new BookKeeping();
	String choice;
	BigDecimal change;
	
	
///////////////////////////
// SETS UP VARIABLE MENU//
/////////////////////////

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public VendingMachineCLI() {
	}
////////////////////////////////////////////////////////////////////////////////////	
// THIS METHOD IS THE HEART OF THE VENDING MACHINE.(WHERE MOST METHODS ARE CALLED//
//////////////////////////////////////////////////////////////////////////////////
	public void run() {
		try {
			fileinput();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		boolean shouldLoop = true;

		while (shouldLoop) {
			choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayMenu();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

//				try {
//					fileinput();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
				try {
					fileinput();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
////////////////////////////////////////////////////////////////////////
///////////////ABOVE HERE COLLECTS USER CHOICE FROM MAIN MENU//////////
//////////////////////////////////////////////////////////////////////
///////////////BELOW METHOD CHECKS TO SEE IF MACHINE HAS ANY INVENTORY//
////////////////////////////////////////////////////////////////////////
				checkInventory();

				
				if (checkInventory()) {

					secondMenu();

				} else {
					System.out.println("COMPLETELY OUT OF STOCK");
					shouldLoop = false;
				}

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {

				try {
					booking.dataStorage();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// do any end of program processing - good place for a method call
				shouldLoop = false;
			}
		}
	}

///////////////////////////////////
//      Check Inventory	        //
// THIS METHOD LOOKS TO SEE IF // 
//	MACHINE HAS ANY INVENTORY.//
///////////////////////////////
	public boolean checkInventory() {
		int quantityTotal = 0;
		for (int each : inventorychoice.quantity) {
			quantityTotal += each;
		}
		if (quantityTotal == 0) {

			return false;
		} else {
			return true;
		}

	}

///////////////////////////////////////////////////////////
//			 ITEM SELECTION								//
// THIS METHOD CHECKS USER INPUT FOR ITEM SELECTION    //
// AND VALIDATES THAT THE CHOICE IS AN EXISTING		  //	
// OPTION. IF IT IS, THE ELSE STATEMENT CONFIRMS 	 //
// SELECTION WITH USER AND TAKES THEM TO FINAL 		//
// TRANSACTION METHOD AFTER PULLING INDEX OF CHOICE//							   
////////////////////////////////////////////////////
	public int itemSelection() {
		
		Scanner customerInput = new Scanner(System.in);
		System.out.println("Please choose an item e.g. A1");

		itemSelection = customerInput.nextLine().toUpperCase();

		theIndex = inventorychoice.itemNumber.indexOf(itemSelection);

		while (!inventorychoice.itemNumber.contains(itemSelection)) {
			System.out.println("Please choose valid option");
			itemSelection = customerInput.nextLine().toUpperCase();;
			theIndex = inventorychoice.itemNumber.indexOf(itemSelection);
		}

		if (inventorychoice.quantity.get(theIndex) <= 0) {
			System.out.println("SOLD OUT");
			itemSelection();

		} else {

			System.out.println("You chose " + itemSelection + " " + inventorychoice.names.get(theIndex) + "  "
					+ inventorychoice.price.get(theIndex));
			theIndex = inventorychoice.itemNumber.indexOf(itemSelection);
			System.out.println("Is this what you want?? \n(1)Yes   \n(2)No");

			int choice = customerInput.nextInt();
			if (choice == 1) {
				endTransaction();
			} else {

			}
		}
		
		return theIndex;

	}
	
/////////////////////////////
//      MAIN METHOD       //
///////////////////////////

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);

		cli.run();

	}

//////////////////////////////////
// 		  SOUND                //
// THIS METHOD RETURNS OUTPUT //
//STATEMENT FOR PARTICULAR   //
//TYPES OF SNACKS BY USING  //
//KEY NUMBER ALIGNMENT OF  // 
// INDECIES.			  //
///////////////////////////

	public void sound() {
		if (inventorychoice.itemNumber.get(theIndex).contains("A")) {
			System.out.println("Crunch Crunch, Yum!" + " Thanks for the purchase! ");
		} else if (inventorychoice.itemNumber.get(theIndex).contains("B")) {
			System.out.println("Munch Munch, Yum!" + " Thanks for the purchase! ");
		} else if (inventorychoice.itemNumber.get(theIndex).contains("C")) {
			System.out.println("Glug Glug, Yum!" + " Thanks for the purchase! ");

		} else if (inventorychoice.itemNumber.get(theIndex).contains("D")) {
			System.out.println("Chew Chew, Yum!" + " Thanks for the purchase! ");
		}
	}

////////////////////////////////////////////////////////
// 			FILE INPUT                               //
// Locates file and creates array lists of inventory//
/////////////////////////////////////////////////////
	public void fileinput() throws Exception {
		File inventoryinput = new File(
				"vendingmachine.csv");
		Scanner inventoryscan = new Scanner(inventoryinput);
		try {
			while (inventoryscan.hasNextLine()) {

				String currentLine = inventoryscan.nextLine();
				String[] currentLineArray = currentLine.split("\\|");
				List<String> arrayTransfer = new ArrayList<String>();
				for (String each : currentLineArray) {
					arrayTransfer.add(each);
				}

//////////////////////////////////////////////////////////////////////////
// THESE OBJECTS ADD DATA TO LISTS IN INVENTORY CLASS FROM READING FILE//
////////////////////////////////////////////////////////////////////////
				inventorychoice.itemNumber.add(arrayTransfer.get(0));
				inventorychoice.names.add(arrayTransfer.get(1));
				inventorychoice.price.add(new BigDecimal(arrayTransfer.get(2)));

				inventorychoice.quantity.add(5);
//////////////////////////////////////////////////////////////////////////
//THESE OBJECTS ADD DATA TO BOOKKEEPING CLASS THAT WE DID NOT FINISH   //
////////////////////////////////////////////////////////////////////////
				booking.soldItems.add(0);
				booking.nameBooking.add(arrayTransfer.get(1));
				booking.price.add(new BigDecimal(arrayTransfer.get(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		inventoryscan.close();
	}

///////////////////////////////////////
// 			DISPLAY MENU		    //
// THIS METHOD READS THE MENU FILE // 
// AND FEEDS IT TO USER FOR OPTION// 
// ONE							 // 
//////////////////////////////////

	public void displayMenu() {
		File menuTxtFile = new File(
				"vendingmachine.csv");
		try {
			Scanner scanMenu = new Scanner(menuTxtFile);
			System.out.println();
			while (scanMenu.hasNextLine()) {
				String currentLine = scanMenu.nextLine();
				System.out.println(currentLine);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

//////////////////////////////////////////////
//   TRANSACTION CONFIRMATION      		   //
//	THIS METHOD CONFIRMS THE USER HAS	  //
//  ENOUGH MONEY FOR SELECTED ITEM, THEN //
//  UPDATES QUANTITY ARRAY FOR THAT     //
//  SOLD ITEM. AND CALCULATES CHANGE   //
////////////////////////////////////////
	public void endTransaction() {
		if(theIndex != -1) {       						// if you selected an item, default index != -1
		
		selectedItemPrice = inventorychoice.price.get(theIndex);

		if (selectedItemPrice.doubleValue() > cashRegister.getTendered().doubleValue()) {
			System.out.println(
					"You need $" + (selectedItemPrice.subtract(cashRegister.getTendered())) + "more for this item");
			
			
		} else {
			sound();
			change = cashRegister.getTendered().subtract(selectedItemPrice);
			
			System.out.println("Your Balance is : $"+change);
			System.out.println("(1) Keep Shopping \n(2)Im done");
			String selection =customerInput.nextLine();
			
			if(selection.equals("1")){
				cashRegister.setTendered(change); //// machine gives the change and updates tendered ZERO
			}else {
			
			
				cashRegister.setTendered(new BigDecimal("0")); //// machine gives the change and updates tendered ZERO
				changeConverter(change);
				keepShopping =false;
		
		}
			

			int updatedquantity = inventorychoice.quantity.get(theIndex) - 1;
			inventorychoice.quantity.add(theIndex, updatedquantity);

			int updateSold = booking.soldItems.get(theIndex) + 1;
			booking.soldItems.add(theIndex, updateSold);
			
		 }

		}
		
		else {
			selectedItemPrice = new BigDecimal("0");				// if you did not select an item
		}
		
	}
		

	
/////////////////////////////////
//Bill CONVERTER			  //
///////////////////////////////

	public void changeConverter(BigDecimal moneyNeedsToBeChanged) {
		
		
		BigDecimal balanceInCents       = moneyNeedsToBeChanged.multiply(new BigDecimal("100"));
		BigDecimal quartersReminder     = balanceInCents.remainder(new BigDecimal("25"));
		BigDecimal numberOfquarters     = balanceInCents.subtract(quartersReminder).divide(new BigDecimal("25"));
		BigDecimal balanceQuarters      = numberOfquarters.multiply(new BigDecimal("25"));
		BigDecimal balanceAfterQuarters = balanceInCents.subtract(balanceQuarters);

		BigDecimal dimesReminder 		= balanceAfterQuarters.remainder(new BigDecimal("10"));
		BigDecimal numberOfDimes 		= balanceAfterQuarters.subtract(dimesReminder).divide(new BigDecimal("10"));
		BigDecimal balanceDimes 		= numberOfDimes.multiply(new BigDecimal("10"));
		BigDecimal balanceAfterDimes 	= balanceAfterQuarters.subtract(balanceDimes);

		BigDecimal numberOfCents 		= balanceAfterDimes;

		System.out.println("Your Change is : " + change + "\n" + numberOfquarters.intValue() + " quarters " + "\n"
				+ numberOfDimes.intValue() + " dimes " + "\n" + numberOfCents.intValue() + " pennies");
	
		
	}

/////////////////////////////////////////////
//SECOND MENU 				  //
///////////////////////////////////////////
	public void secondMenu() {
		menuBreak = true;
		while (menuBreak) {
			System.out.println("\nCurrent money Provided: $" + cashRegister.getTendered());

			System.out.println("(1) Feed Money");

			System.out.println("(2) Select Product ");

			System.out.println("(3) Finish Transaction");
			String choice = customerInput.nextLine();

			while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
				System.out.println("Please make a valid selection");
				System.out.println("(1) Feed Money");
				System.out.println("(2) Select Product");
				
				System.out.println("(3) Finish Transaction");
				  
				choice = customerInput.nextLine();

			}

			if (choice.equals("1")) {
				cashRegister.transaction();
			} else if (choice.equals("2") && cashRegister.getTendered().doubleValue() == 0) {
				System.out.println("Feed money first then Select Product");
			} else if (choice.equals("2")) {
				itemSelection();
			} else if (choice.equals("3") && cashRegister.getTendered().doubleValue()== 0 ) {
			
				menuBreak = false;
			}	
			else if (choice.equals("3") ) {
				if(cashRegister.getTendered().doubleValue()> 0) {
					changeConverter(cashRegister.getTendered());
					cashRegister.setTendered(new BigDecimal("0"));
					
					
				}
				
			}
			
			} 
		
		}
	

/////////////////////////////////////////////
//  		GETS AND SETS  				  //
///////////////////////////////////////////

	public int getTheIndex() {
		return theIndex;
	}

	public BigDecimal getSelectedItemPrice() {
		return selectedItemPrice;
	}

	public void setSelectedItemPrice(BigDecimal selectedItemPrice) {
		this.selectedItemPrice = selectedItemPrice;
	}

	public void setTheIndex(int theIndex) {
		this.theIndex = theIndex;
	}

	public String getItemSelection() {
		return itemSelection;
	}

	public Scanner getCustomerInput() {
		return customerInput;
	}
}
