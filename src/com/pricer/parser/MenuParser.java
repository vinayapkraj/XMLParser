package com.pricer.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Vinay
 *
 */
public class MenuParser {
	
	private static final String NODE_BREAK_FAST = "breakfast_menu";
	private static final String NODE_FOOD = "food";
	private static final String NODE_NAME = "name";
	private static final String NODE_PRICE = "price";
	private static final String NODE_DESCRIPTION = "description";
	private static final String NODE_CALORIES = "calories";
	private static final String SORT_ASCENDING = "asc";
	private static final String SORT_DESCENDING = "desc";
	private static final String XML_EXTENSION = "xml";
	private static final String JSON_EXTENSION = "json";
	
	public static void main(String[] args) {
		if (args.length==0){
			System.out.println("File and Sort Order is mandatory in the Arguments. Please rerun Again");
		}else{
		String filename = args[0];
		String sortOrder = args[1];
		ArrayList <FoodVO> breakfastMenu = new ArrayList<>();
		
		if (filename !=null && filename.endsWith(XML_EXTENSION)){
			try {
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document document = parser.parse(new File(filename));
		    System.out.println(document.getDocumentElement().getNodeName().toUpperCase());
		    document.getDocumentElement().normalize();		    
		    
		    
		    NodeList nList = document.getElementsByTagName(NODE_BREAK_FAST);
		    
		    for (int i = 0; i < nList.getLength(); i++) {
	
				Node nNode = nList.item(i);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element xmlElement = (Element) nNode;
					
					
					NodeList foodNodeL = xmlElement.getElementsByTagName(NODE_FOOD);
					for(int j=0;j<foodNodeL.getLength();j++){
						FoodVO foodVO = new FoodVO();
						Node foodNode = foodNodeL.item(j);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element foodElement = (Element) foodNode;
							
							foodVO.setName(foodElement.getElementsByTagName(NODE_NAME).item(i).getTextContent());
							foodVO.setPrice(foodElement.getElementsByTagName(NODE_PRICE).item(i).getTextContent());
							foodVO.setDescription(foodElement.getElementsByTagName(NODE_DESCRIPTION).item(i).getTextContent());
							foodVO.setCalories(Integer.parseInt(foodElement.getElementsByTagName(NODE_CALORIES).item(i).getTextContent()));
						}
						breakfastMenu.add(foodVO);
						
					}
				}
				
		    }
			
		    }
		
		catch (SAXException | IOException | ParserConfigurationException e) {
		        System.out.println("Exception while parsing XML "+ e);
		}
	}
		if(filename !=null && filename.endsWith(JSON_EXTENSION)){
			JSONParser jsonParser = new JSONParser();
			try {
				Object obj = jsonParser.parse(new FileReader(filename));
				JSONObject jsonObject = (JSONObject)obj;
				JSONObject breakfast_menu =(JSONObject) jsonObject.get("breakfast_menu");
							
				JSONArray foodArray = (JSONArray)breakfast_menu.get("food");
				System.out.println("try  " + foodArray.get(0));
				
				for ( int i=0;i<foodArray.size();i++){
					JSONObject jsonFoodList = (JSONObject) foodArray.get(i);
					FoodVO foodVO = new FoodVO();
					foodVO.setName((String)jsonFoodList.get(NODE_NAME));
					foodVO.setPrice((String)jsonFoodList.get(NODE_PRICE));
					foodVO.setDescription((String)jsonFoodList.get(NODE_DESCRIPTION));
					foodVO.setCalories(Integer.parseInt((String)jsonFoodList.get(NODE_CALORIES)));
					System.out.println("Name "+ foodVO);
					breakfastMenu.add(foodVO);
				}
				
			} catch (ParseException | IOException e) {
				
				System.out.println("Parse EXception "+e);
				e.printStackTrace();
			}
		}
		if(filename !=null && (filename.endsWith(JSON_EXTENSION) || filename.endsWith(XML_EXTENSION)) ){
		System.out.println("Food List Un Sorted");
		breakfastMenu.forEach(System.out::println);
		if (sortOrder!=null && sortOrder.equalsIgnoreCase(SORT_ASCENDING)){
			System.out.println("FoodList Sorted Ascending " );
			breakfastMenu.sort(Comparator.comparing(FoodVO::getName));
			breakfastMenu.forEach(System.out::println);
		}
		if (sortOrder!=null && sortOrder.equalsIgnoreCase(SORT_DESCENDING)){
			System.out.println("FoodList Sorted Descending");
			breakfastMenu.sort(Comparator.comparing(FoodVO::getName).reversed());
			breakfastMenu.forEach(System.out::println);
		}
	}else{
		System.out.println("Parsing for this file type Under Construction");
	}
		}
		
			
		}
	}

