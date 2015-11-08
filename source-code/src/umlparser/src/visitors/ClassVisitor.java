package umlparser.src.visitors;

import java.util.Iterator;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
/**
* @author Manthan H.
* @Description Class and interface Parser
*/
@SuppressWarnings("rawtypes")
public class ClassVisitor extends VoidVisitorAdapter{

	private String extendStr;
	private String implementStr;
	private String ballNSocketStr;
	private	String classNameStr;
	
	public ClassVisitor(){
		extendStr = "";
		implementStr ="";
		classNameStr="";
		ballNSocketStr="";
	}
	
	/** Generates class, extends and implements string */
	@Override		
	public void visit(ClassOrInterfaceDeclaration n, Object arg){

		ClassOrInterfaceType eName;
		ClassOrInterfaceType iName;

		classNameStr = n.getName() + " : ";			

		List<ClassOrInterfaceType> eList = n.getExtends();
		List<ClassOrInterfaceType> iList = n.getImplements();
		Iterator<ClassOrInterfaceType> eListIt = eList.iterator();
		Iterator<ClassOrInterfaceType> iListIt = iList.iterator();
		while(eListIt.hasNext()){
			eName = eListIt.next();
			extendStr += n.getName() + " --|> " + eName.getName() + "\n";				
		}
		while(iListIt.hasNext()){
			iName = iListIt.next();
			implementStr += n.getName() + " ..|> " + iName.getName() + "\n";
			implementStr += "interface " + iName.getName() + " << interface >> "+ "\n";
			
			ballNSocketStr += n.getName() + " --() " + iName.getName() + "\n";
			ballNSocketStr += "interface " + iName.getName() + "\n";
			
		}

	}
	public String getExtendStr() {
		return extendStr;
	}
	public String getImplementStr() {
		return implementStr;
	}
	
	public String getBallNSocketStr() {
		return ballNSocketStr;
	}

	/** Returns class name as String */
	public String getClassNameStr(){
		return classNameStr;
	}

}