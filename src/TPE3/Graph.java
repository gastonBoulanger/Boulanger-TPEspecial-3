package TPE3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Graph implements IGraph {
	private Map<String, Node> users = new HashMap<>();
	private Map<String, Node> likes = new HashMap<>();

	@Override
	public void addUser(String st) {
		Node n = new Node(Type.USER, st);
		users.put(st, n);
	}

	public void setRelations(String st, ArrayList<String> likesList) {
		Node u = this.users.get(st);
		ArrayList<Node> relations = new ArrayList<>();
		for (String s : likesList) {
			if (!this.likes.containsKey(s)) {
				addLike(s);
			} 
			relations.add(this.likes.get(s));
			this.likes.get(s).addRelation(u);
		}
		for (Node n : relations) {
			u.addRelation(n);
		}
	}
	@Override
	public void addLike(String st) {
		Node n = new Node(Type.LIKE, st);
		this.likes.put(st, n);
	}

	@Override
	public boolean likeExists(String st) {
		return likes.containsKey(st);
	}

	@Override
	public boolean userExists(String st) {
		return users.containsKey(st);
	}
	
	// Dado un usuario se recorre todos los nodos usuarios buscando en cada uno que coincidan con mas de dos gustos en comun
	@Override
	public Set<Node> similarUsers(String id) {
		Set<Node> similars = new HashSet<>();
		if (this.userExists(id)) {
			Node u = users.get(id);
			for (Node n : this.users.values()) {
				if (countCommonLikes(u, n) >= 2) {
					similars.add(n);
				}
			}
			similars.remove(u);
		}
		return similars;
	}

	private int countCommonLikes(Node u1, Node u2) {
		int count = 0;
		for (Node like : u1.getRelations()) {
			for (Node like2 : u2.getRelations()) {
				if (like.equals(like2)) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public Node mostLiked() {
		Node mostLiked = null;
		for (Node like : this.likes.values()) {
			if (mostLiked == null) {
				mostLiked = like;
			} else if (mostLiked.getRelations().size() < like.getRelations().size()) {
				mostLiked = like;
			}
		}
		return mostLiked;
	}

	// Dada una persona, se muestra aquella que tenga gustos más lejanos a él.
	public Node farestUser(String st) { 
		Node givenUser = this.users.get(st);
		Set<Node> solution = new HashSet<Node>();
		solution.add(givenUser);
		Map<Node, Integer> distance = new HashMap<>();

		initDistances(givenUser, distance); // Calcula la primera fila de la matriz de distancias
		iterateDistances(givenUser, solution, distance);

		Node farestUser = getFarestUser(distance); // Se queda con el usuario con la distancia mayor

		return farestUser;
	}

	private Node getFarestUser(Map<Node, Integer> D) {
		int longestDistance = 0;
		Node farestUser = null;

		for (Map.Entry<Node, Integer> m : D.entrySet()) {
			if (m.getValue() > longestDistance) {
				longestDistance = m.getValue();
				
				farestUser = m.getKey();
			}

		}
		return farestUser;
	}

	private void iterateDistances(Node givenUser, Set<Node> S, Map<Node, Integer> D) {

		for (int i = 1; i < (users.values().size() - 1); i++) {
			Set<Node> vlS = new HashSet<>(users.values()); // V less S o (V-S)
			vlS.removeAll(S); // V-S
			Node w = chooseMin(vlS, D); // Elijo el minimo w de la tabla de distancias
			S.add(w); // agrego w al conjunto Solución
			vlS.remove(w);
			// Ahora que elejimos un w, hay que actualizar la tabla de distancias, parados en este w.
			for (Node v : vlS) { // para cada vértice V en V-S,
				//en lugar de buscar la distancia minimabuscamos el camino más largo
				D.put(v, Math.max(D.get(v), D.get(w) + getDistance(w, v)));
			}

		}
	}

	private void initDistances(Node givenUser, Map<Node, Integer> D) {
		for (Node i : users.values()) {
			if (i.equals(givenUser)) { // Salteo el usuario dado (equivale al i=2)
				continue;
			}
			D.put(i, getDistance(givenUser, i));
		}
	}

	//Elige un vértice w en V-S tal que D[w] sea un mínimo; //

	private Node chooseMin(Set<Node> vlS, Map<Node, Integer> d) {
		int minDistance = Integer.MAX_VALUE;
		Node minNode = null;

		for (Node w : vlS) {
			if (d.get(w) < minDistance) { // con d.get(w) pido la distancia que tengo guardada en el arreglo de distancias
				minDistance = d.get(w);
				minNode = w;
			}
		}
		return minNode;
	}

	//Debería devolver 1 si los nodos están conectados o Integer.MAX_VALUE si no están conectados.

	private Integer getDistance(Node givenUser, Node i) {
		// Agarramos todos los likes de ese usuario y para cada like pedimos los usuarios que gustan de ese like
		for (Node like : givenUser.getRelations()) {
			for (Node user : like.getRelations()) {
				if (user.equals(givenUser)) {
					// Si alguno es el nodo i devolvemos 1 (están conectados)
					return 1;
				}
			}
		}
		// si no están conectados, devuelvo Integer.MAX_VALUE
		return Integer.MAX_VALUE;
	}
}
