package umlparser.src.constants;

import java.util.regex.Pattern;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
* @author Manthan H.
* @Description Model to hold collection types
*/
public final class UConstants{

	private HashMap<String,String> collectionTypes;


	public UConstants(){
		collectionTypes = new HashMap<String,String>();
		collectionTypes.put("COLLECTION","Collection<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("LIST","List<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("SET","Set<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("MAP","Map<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("QUEUE","Queue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");

		collectionTypes.put("ALIST","ArrayList<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("LLIST","LinkedList<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");

		collectionTypes.put("HSET","HashSet<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("LHSET","LinkedHashSet<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("TSET","TreeSet<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");

		collectionTypes.put("HMAP","HashMap<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("TMAP","TreeMap<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("LHMAP","LinkedHashMap<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");

		collectionTypes.put("PQUEUE","PriorityQueue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("SQUEUE","SynchronousQueue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionTypes.put("DQUEUE","DelayQueue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");

		collectionTypes.put("ARRAY","[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*(\\[\\])+[\\s]*");
	}
	
	public boolean isCollection(String dataType){
		Iterator<String> collectionTypesIt = collectionTypes.keySet().iterator();
		String pattern;

		while(collectionTypesIt.hasNext()){
			pattern = collectionTypes.get(collectionTypesIt.next());			
			if(Pattern.matches(pattern,dataType.trim())){
				return true;
			}
		}

		return false;
	}

	public List<String> getBaseType(String dataType){
		List<String> baseType= new ArrayList<String>();
		
		if(dataType.contains("[")){	//Collection is an Array object
			baseType.add((dataType.substring(0,dataType.indexOf("["))).trim());
		}
		else if(dataType.contains("<")){	//Collection is a Collection object
			if(dataType.contains(",")){		//Collection is a Map collection object
				baseType.add((dataType.substring(dataType.indexOf("<")+1,dataType.indexOf(","))).trim());
				baseType.add((dataType.substring(dataType.indexOf(",")+1,dataType.indexOf(">"))).trim());
			}
			else{		//Collection is non-map collection
				baseType.add((dataType.substring(dataType.indexOf("<")+1,dataType.indexOf(">"))).trim());
			}
		}
		else{
			baseType.add(dataType);
		}
		return baseType;
	}
}