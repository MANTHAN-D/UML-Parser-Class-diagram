package umlparser.src.handler;

import umlparser.src.model.InstanceVariable;
import java.util.regex.Pattern;
/**
* @author Manthan H.
* @Description Association handler
*/
public class AssociationHandler{
	
	public static String handleDuplicateAssociation(String base,String class1,InstanceVariable instVar,String class2,boolean collectionFlag){
		
		String newString;

		String input1 = class1 + "\\s--\\s"+ class2 + "\n";
		String input2 =  class1 + "\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class2 + "\n";
		String input3 =  class1 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s" + class2 + "\n";
		String input4 =  class1 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class2 + "\n";

		String reverseInput1 = class2 + "\\s--\\s"+ class1 + "\n";
		String reverseInput2 = class2 + "\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class1 + "\n";
		String reverseInput3 = class2 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s" + class1 + "\n";
		String reverseInput4 = class2 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class1 + "\n";

		int startPosition=-1,endPosition;

		String tobeChecked;
		String tobeReplaced;
		do{
			startPosition = base.indexOf(class1+" ",startPosition+1);
			if(startPosition >= 0){
				endPosition = base.indexOf("\n",startPosition)+1;

				tobeChecked = base.substring(startPosition,endPosition);
				if(Pattern.matches(input1,tobeChecked)){
					if(collectionFlag){
						tobeReplaced = class1 + " -- " +
						 "\""+ "*\""+ class2 + "\n";
					}
					else{
						tobeReplaced = class1 + " -- " +
						 "\"" + "1\""+ class2 + "\n";	
					}
					
					newString = base.replace(tobeChecked,tobeReplaced);
					return newString;
				}
				else if(Pattern.matches(input2,tobeChecked)){
					if(collectionFlag){
						
						tobeReplaced = class1 + " -- " +
						 "\"" +
						 tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1)) +
						 ", " + 
						 " *\""+ class2 + "\n";
					}
					else{
						tobeReplaced = class1 + " -- " +
						 "\"" +
						 tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1)) +
						 ", " 
						 + " 1\""+ class2 + "\n";	
					}
					
					newString = base.replace(tobeChecked,tobeReplaced);					
					return newString;
				}
				else if(Pattern.matches(input3,tobeChecked)){
					if(collectionFlag){
						
						tobeReplaced = class1 + 
						tobeChecked.substring(tobeChecked.indexOf("\""),tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1)+1)
						+ " -- " +
						 "\"" + "*\""+ class2 + "\n";
					}
					else{
						tobeReplaced = class1 + 
						tobeChecked.substring(tobeChecked.indexOf("\""),tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1)+1)
						+" -- " +
						 "\"" + "1\""+ class2 + "\n";	
					}

					newString = base.replace(tobeChecked,tobeReplaced);
					return newString;
				}
				else if(Pattern.matches(input4,tobeChecked)){					
					if(collectionFlag){
						
						tobeReplaced = class1 + 
						tobeChecked.substring(tobeChecked.indexOf("\""),tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1)+1)
						+ " -- " +
						 "\"" +
						 tobeChecked.substring(tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- "))+1,
						 	tobeChecked.indexOf("\"",tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- "))+1)) +
						 ", " + 
						 " *\""+ class2 + "\n";
					}
					else{
						tobeReplaced = class1 + 
						tobeChecked.substring(tobeChecked.indexOf("\""),tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1)+1)
						+" -- " +
						 "\"" + 
						 tobeChecked.substring(tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- "))+1,
						 	tobeChecked.indexOf("\"",tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- "))+1)) +
						 ", " + 
						" 1\""+ class2 + "\n";	
					}
					
					newString = base.replace(tobeChecked,tobeReplaced);					
					return newString;
				}
				else{
					//do nothing					
				}

			}					

		}while(startPosition >= 0);


		startPosition = -1;
		do{
			startPosition = base.indexOf(class2+" ",startPosition+1);			
			if(startPosition >= 0){
				endPosition = base.indexOf("\n",startPosition)+1;

				tobeChecked = base.substring(startPosition,endPosition);
				if(Pattern.matches(reverseInput1,tobeChecked)){
					if(collectionFlag){
						tobeReplaced = class2 + "\"" + "*\""
						+ " -- " 
						 + class1 + "\n";
					}
					else{
						tobeReplaced = class2 + "\"" + "1\""
						+ " -- " +
						  class1 + "\n";	
					}
					
					newString = base.replace(tobeChecked,tobeReplaced);
					return newString;
				}
				else if(Pattern.matches(reverseInput2,tobeChecked)){
					if(collectionFlag){
						
						tobeReplaced = class2 + "\"" + "*\""
						+ " -- " +
						"\"" +
						 tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1))
						 +"\"" + class1 + "\n";						
					}
					else{
						tobeReplaced = class2 + "\"" + "1\""
						+ " -- " +
						"\"" +
						 tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1))
						 +"\"" + class1 + "\n";						
					}										
					
					newString = base.replace(tobeChecked,tobeReplaced);
					return newString;
				}
				else if(Pattern.matches(reverseInput3,tobeChecked)){
					if(collectionFlag){	
						tobeReplaced = class2 + "\"" +
						tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1))
						+ ", " +
						" *\""
						+ " -- " +
						class1 + "\n";
					}
					else{
						tobeReplaced = class2 + "\"" +
						tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1))
						+ ", " +
						" 1\""
						+ " -- " +
						class1 + "\n";
					}	
					
					newString = base.replace(tobeChecked,tobeReplaced);
					return newString;
				}
				else if(Pattern.matches(reverseInput4,tobeChecked)){
					if(collectionFlag){
						
						tobeReplaced = class2 + "\"" +
						tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1))
						+ ", " +
						" *\"" +
						" -- " +
						 tobeChecked.substring(tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- ")),
						 	tobeChecked.indexOf("\"",tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- "))+1)+1)
						 + class1 + "\n";	
					}
					else{
						tobeReplaced = class2 + "\"" +
						tobeChecked.substring(tobeChecked.indexOf("\"")+1,tobeChecked.indexOf("\"",tobeChecked.indexOf("\"")+1))
						+ ", " +
						" 1\"" +
						" -- " +
						 tobeChecked.substring(tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- ")),
						 	tobeChecked.indexOf("\"",tobeChecked.indexOf("\"",tobeChecked.indexOf(" -- "))+1)+1)
						 + class1 + "\n";	
					}

					newString = base.replace(tobeChecked,tobeReplaced);
					return newString;
				}
				else{
					// do nothing
				}
			}				

		}while(startPosition >= 0);

		//to do as no duplicate dependency
		if(collectionFlag){
			newString = base + class1 + " -- " + "\""+ "*\""
		+ class2 + "\n";															
		}
		else{
			newString = base + class1 + " -- " + "\""+ "1\""
		+ class2 + "\n";														
		}

		return newString;
	}
}