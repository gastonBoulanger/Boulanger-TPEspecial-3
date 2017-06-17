package TPE3;

import java.util.Set;

public class Main {
	final static String path500k = "C:/Users/USER/Documents/Universidad/Tudai/2017/Programacion3/Boulanger-TPEspecial/datasets/dataset_500000.csv";
	final static String path1m = "C:/Users/USER/Documents/Universidad/Tudai/2017/Programacion3/Boulanger-TPEspecial/datasets/dataset_1000000.csv";
	final static String path3m = "C:/Users/USER/Documents/Universidad/Tudai/2017/Programacion3/Boulanger-TPEspecial/datasets/dataset_3000000.csv";
	final static String busqueda = "C:/Users/USER/Documents/Universidad/Tudai/2017/Programacion3/Boulanger-TPEspecial/datasets/dataset_busqueda_10000.csv";
	final static String eje = "C:/Users/USER/Documents/Universidad/Tudai/2017/Programacion3/Boulanger-TPEspecial/datasets/dataset_eje.csv";
	
	public static void main(String[] args) {
		// Se carga el grafo con archivo de precarga
		Graph graph = new Graph();
		CSVReader csvr = new CSVReader();
		graph = csvr.reader(path500k);
		
		//Dado un usuario imprime los usuarios con gustos mas similares a el.
		String user = "71095014";
		Set<Node> e = graph.similarUsers(user);
		for (Node node : e) {
			System.out.println(user + " es similar a: " + node.getKey());
		}
		
		//Imprime el gusto mas comun.
		System.out.println("El gusto mas comun es: " + graph.mostLiked().getKey());
		
		//Dado un usuario imprime el usuario mas elejado a el.
		System.out.println("El usuario mas lejano " + user + " es: " + graph.farestUser(user).getKey());
	}
}
