package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

public class CashRegister {

	Inventory inventorychoice = new Inventory();
	private BigDecimal tendered = new BigDecimal("0");

	Scanner customerInput = new Scanner(System.in);
///////////////////////////////////////
//				TRANSACTION	
//	This method is what asks user to 	
//	insert cash
// 
/////////////////////////////////////
	public void transaction() {

		System.out.println("Plese enter money e.g $1 $2 $5 $10 ");
		
		BigDecimal payment = customerInput.nextBigDecimal();
		
		
		while(!payment.equals(new BigDecimal("1")) && 
		      !payment.equals(new BigDecimal("2")) && 
		      !payment.equals(new BigDecimal("5")) &&
		      !payment.equals(new BigDecimal("10"))){
			
			System.out.println("Plese enter valid amount  ( $1 , $2 ,  $5 , $10  )");
			payment = customerInput.nextBigDecimal();
		}
		
		tendered = tendered.add(payment);
		
		
	}


	

//////////////////////////////////
//			GETS AND SETS      //
////////////////////////////////

	public BigDecimal getTendered() {
		return tendered;
	}

	public void setTendered(BigDecimal tendered) {
		this.tendered = tendered;
	}

}
