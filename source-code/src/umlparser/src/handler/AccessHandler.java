/**
 * 
 */
package umlparser.src.handler;

import com.github.javaparser.ast.body.ModifierSet;

/**
* @author Manthan H.
* @Description Access Handler
*/
public class AccessHandler {
	public static char getAccessSpecifier(int accessModifierCode,boolean isMethodFlag){
		switch(accessModifierCode){
			case ModifierSet.PRIVATE :
				return '-';
			case ModifierSet.PROTECTED :
				return '#';
			case ModifierSet.PUBLIC :
				return '+';
			default :
				if(isMethodFlag){
					if(ModifierSet.isPublic(accessModifierCode)){
						return '+';
					}
					else{
						return '~';
					}					
				}					
				else
					return '~';
		}
	}
}
