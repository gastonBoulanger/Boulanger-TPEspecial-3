package TPE3;

import java.util.HashSet;
import java.util.Set;


public class Node {
	private String key;
	private Type type;
	private Set<Node> relations;
	
	public Node(Type type, String key){
		this.type = type;
		this.key = key;
		this.relations = new HashSet<>();
	}
	public void addRelation(Node n){
	this.relations.add(n);
	}
	public Set<Node> getRelations(){
		return this.relations;
	}
	
 	public String getKey() {
		return key;
	}
 	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (relations == null) {
			if (other.relations != null)
				return false;
		} else if (!relations.equals(other.relations))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
