package com.csv.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.csv.entity.Inputs;

public class CsvReader {
	public static void main(String[] args) {
		
//		Import CSV data from csv file -- generics and collections used 
		
		String fileName = "test.csv";
		Inputs inputs = getInputs();
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			double amount = generateAmount(values,inputs);
			System.out.println("Relative balance from period is: "+"$"+amount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	Inputs are gathered from user.
	
	private static Inputs getInputs(){
		Scanner sc = new Scanner(System.in).useDelimiter("\n");
		System.out.println("Enter Account Id: ");
		String accountId = sc.next().trim();
		System.out.println("Enter from date and time: ");
		String from = sc.next().trim();
		System.out.println("Enter to date and time: ");
		String to = sc.next().trim();
		Inputs inputs = new Inputs(accountId, from, to);
		return inputs;
	}
	
//	Calculation logic of transaction between a timestamp.
//	Regardless of amount reversal timings , amount of reversal is excluded from transaction amount.
	
	private static double generateAmount(List<List<String>> values,Inputs inputs){
		double amount = 0;
		String accountId = inputs.getAccountId();
		long fromDateInMilliSec = getTimeInMilli(inputs.getFrom());
		long toDateInMilliSec = getTimeInMilli(inputs.getTo());
		
		for(List<String> value:values){
			if(accountId.equals(value.get(1).trim())&&fromDateInMilliSec <= getTimeInMilli(value.get(3).trim())&&toDateInMilliSec>=getTimeInMilli(value.get(3).trim())){
				amount = amount - Double.parseDouble(value.get(4));
			}
			if(value.get(5).trim().equals("REVERSAL")){
				String transaction = value.get(6).trim();
				for(List<String> locValue:values){
					if(locValue.get(0).trim().equals(transaction)){
						amount = amount + Double.parseDouble(locValue.get(4));
						break;
					}
				}
			}
		}
		return amount;
	}
	
//	Timestamp difference logic is calculated as below.
	
	private static long getTimeInMilli(String fromDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(fromDate);
			long millis = date.getTime();
			return millis;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

