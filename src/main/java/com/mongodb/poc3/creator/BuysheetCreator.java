package com.mongodb.poc3.creator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mongodb.poc3.model.Buysheet;
import com.mongodb.poc3.model.DataPoints;



public class BuysheetCreator {

	
	public Buysheet create()
	{
		Buysheet bs = new Buysheet();
		bs.setCreatedBy("shalini");
		bs.setDateCreated(new Date());
		bs.setSheetName("Buysheet1");
		
		DataPoints dp = new DataPoints();
		HashMap<String, Integer> hmM = new HashMap<>();
		List<DataPoints> list = new ArrayList<>();

		for(int i =0 ;i<100;i++)
			hmM.put("M"+i, +100+i);
		
		for(int cc=0 ;cc<50;cc++)
			for(int cl=0;cl<60;cl++)
				for(int w=0;w<53;w++)
				{
					dp.setCc("cc"+cc);
		            dp.setCl("cl"+cl);
		            dp.setWeek("w"+w);
		            dp.setHmMajor(hmM);
		            list.add(dp);
		            
				}
		bs.setRows(list);
		 return bs; 
	}
}
