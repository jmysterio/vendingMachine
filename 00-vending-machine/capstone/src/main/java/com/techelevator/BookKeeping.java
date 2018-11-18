package com.techelevator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookKeeping {
	private String totalSale = "";
	Inventory inventoryPrint = new Inventory();
	List<Integer> soldItems = new ArrayList<Integer>();
	List<String> nameBooking = new ArrayList<String>();
	List<BigDecimal> price = new ArrayList<BigDecimal>();

/////////////////////////////////////////////
//Write sale report file 				  //
///////////////////////////////////////////

	public void dataStorage() throws IOException {

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("myFile.txt"),
						StandardCharsets.UTF_8))) {

			for (int i = 0; i < nameBooking.size(); i++) {
				writer.write(nameBooking.get(i) + " |" + soldItems.get(i) + "\n");
				totalSale +=soldItems.get(i)*(price.get(i).intValue());

			}
			
			writer.write("\n");
			writer.write("Total Sale : $" +totalSale);
			
		}	
		 catch (IOException ex) {
			
		}
	}
}

