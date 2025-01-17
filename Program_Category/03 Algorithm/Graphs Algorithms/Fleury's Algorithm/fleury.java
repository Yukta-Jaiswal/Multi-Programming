import java.util.ArrayList;
public class Graph {

	private int vertices; // No. of vertices
	private ArrayList<Integer>[] adj; // adjacency list

	// Constructor
	Graph(int numOfVertices)
	{
		// initialise vertex count
		this.vertices = numOfVertices;

		// initialise adjacency list
		initGraph();
	}

	// utility method to initialise adjacency list
	@SuppressWarnings("unchecked") private void initGraph()
	{
		adj = new ArrayList[vertices];
		for (int i = 0; i < vertices; i++) {
			adj[i] = new ArrayList<>();
		}
	}

	// add edge u-v
	private void addEdge(Integer u, Integer v)
	{
		adj[u].add(v);
		adj[v].add(u);
	}

	// This function removes edge u-v from graph.
	private void removeEdge(Integer u, Integer v)
	{
		adj[u].remove(v);
		adj[v].remove(u);
	}

	/* The main function that print Eulerian Trail.
	It first finds an odd degree vertex (if there
	is any) and then calls printEulerUtil() to
	print the path */
	private void printEulerTour()
	{
		// Find a vertex with odd degree
		Integer u = 0;
		for (int i = 0; i < vertices; i++) {
			if (adj[i].size() % 2 == 1) {
				u = i;
				break;
			}
		}

		// Print tour starting from oddv
		printEulerUtil(u);
		System.out.println();
	}

	// Print Euler tour starting from vertex u
	private void printEulerUtil(Integer u)
	{
		// Recur for all the vertices adjacent to this
		// vertex
		for (int i = 0; i < adj[u].size(); i++) {
			Integer v = adj[u].get(i);
			// If edge u-v is a valid next edge
			if (isValidNextEdge(u, v)) {
				System.out.print(u + "-" + v + " ");

				// This edge is used so remove it now
				removeEdge(u, v);
				printEulerUtil(v);
			}
		}
	}

	// The function to check if edge u-v can be
	// considered as next edge in Euler Tout
	private boolean isValidNextEdge(Integer u, Integer v)
	{
		if (adj[u].size() == 1) {
			return true;
		}
		boolean[] isVisited = new boolean[this.vertices];
		int count1 = dfsCount(u, isVisited);

		removeEdge(u, v);
		isVisited = new boolean[this.vertices];
		int count2 = dfsCount(u, isVisited);
		addEdge(u, v);
		return (count1 > count2) ? false : true;
	}

	// A DFS based function to count reachable
	// vertices from v
	private int dfsCount(Integer v, boolean[] isVisited)
	{
		// Mark the current node as visited
		isVisited[v] = true;
		int count = 1;
		// Recur for all vertices adjacent to this vertex
		for (int adj : adj[v]) {
			if (!isVisited[adj]) {
				count = count + dfsCount(adj, isVisited);
			}
		}
		return count;
	}

	// Driver program to test above function
	public static void main(String a[])
	{
		// Let us first create and test
		// graphs shown in above figure
		Graph g1 = new Graph(4);
		g1.addEdge(0, 1);
		g1.addEdge(0, 2);
		g1.addEdge(1, 2);
		g1.addEdge(2, 3);
		g1.printEulerTour();

		Graph g2 = new Graph(3);
		g2.addEdge(0, 1);
		g2.addEdge(1, 2);
		g2.addEdge(2, 0);
		g2.printEulerTour();

		Graph g3 = new Graph(5);
		g3.addEdge(1, 0);
		g3.addEdge(0, 2);
		g3.addEdge(2, 1);
		g3.addEdge(0, 3);
		g3.addEdge(3, 4);
		g3.addEdge(3, 2);
		g3.addEdge(3, 1);
		g3.addEdge(2, 4);
		g3.printEulerTour();
	}
}

