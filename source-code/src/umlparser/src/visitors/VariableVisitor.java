package umlparser.src.visitors;

import java.util.ArrayList;
import java.util.List;

import umlparser.src.handler.AccessHandler;
import umlparser.src.model.InstanceVariable;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
/**
* @author Manthan H.
* @Description Instance variable Parser
*/
@SuppressWarnings("rawtypes")
public class VariableVisitor extends VoidVisitorAdapter{

	InstanceVariable instVar;
	private List<InstanceVariable> instVarStrList;
	
	public VariableVisitor(){
		instVarStrList = new ArrayList<InstanceVariable>();
	}

	/** Generates list of all instance variables */
	@Override	
	public void visit(FieldDeclaration n, Object arg){

		if(AccessHandler.getAccessSpecifier(n.getModifiers(),false) == '-' ||
				AccessHandler.getAccessSpecifier(n.getModifiers(),false) == '+'){	//Include only private and public instance variables
			instVar = new InstanceVariable(AccessHandler.getAccessSpecifier(n.getModifiers(),false),
					n.getVariables().get(0).getId().toString(),n.getType().toString());
			instVarStrList.add(instVar);
		}						
	}

	/** Returns list of all instance variables */
	public List<InstanceVariable> getVarList(){			
		return instVarStrList;
	}

}