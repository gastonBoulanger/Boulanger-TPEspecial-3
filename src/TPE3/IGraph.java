package TPE3;

import java.util.Set;

public interface IGraph {

	
	public void addUser(String st);
	public void addLike (String st);
	
	public boolean likeExists(String st);
	public boolean userExists(String st);
	
	public Set<Node> similarUsers(String id);
	public Node mostLiked();
}