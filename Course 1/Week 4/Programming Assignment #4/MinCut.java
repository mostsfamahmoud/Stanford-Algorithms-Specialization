package Week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

class Edge {
    int src, dest;

    Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
    }
}

class Graph {
    int V, E;
    Edge[] edge;

    Graph(int V, int E) {
        this.V = V;
        this.E = E;
        this.edge = new Edge[E];
        for (int i = 0; i < E; i++) {
            this.edge[i] = new Edge(0, 0);
        }
    }
}

class Subset {
    int parent, rank;

    Subset(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

public class MinCut {
    static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    static void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    static int randomContraction(Graph graph) {
        int V = graph.V, E = graph.E;
        Edge[] edge = graph.edge;

        Subset[] subsets = new Subset[V];

        for (int v = 0; v < V; v++) {
            subsets[v] = new Subset(v, 0);
        }

        int vertices = V;
        while (vertices > 2) {
            int i = new Random().nextInt(E);

            int subset1 = find(subsets, edge[i].src);
            int subset2 = find(subsets, edge[i].dest);

            if (subset1 != subset2)
            {
                union(subsets, subset1, subset2);
                vertices--;
            }
        }
        int cutEdges = 0;
        for (int i = 0; i < E; i++) {
            int subset1 = find(subsets, edge[i].src);
            int subset2 = find(subsets, edge[i].dest);
            if (subset1 != subset2) cutEdges++;
        }
        return cutEdges;
    }

    static Graph createGraph(int V, int E) {
        return new Graph(V, E);
    }

    public static void main(String[] args) throws IOException {
        int V = 200;

        BufferedReader file = new BufferedReader(new FileReader("KargerMinCut.txt"));
        String str;
        int val;
        int E = 0;
        while ((str = file.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(str);
            String c = tokenizer.nextToken();
            int i = Integer.parseInt(c);
            while (tokenizer.hasMoreTokens()) {
                val = Integer.parseInt(tokenizer.nextToken());
                if (val > i) E++;
            }
        }
        file.close();

        Graph graph = createGraph(V, E);
        BufferedReader file2 = new BufferedReader(new FileReader("KargerMinCut.txt"));
        String str2;
        int val2;
        int edgeCount = 0;
        while ((str2 = file2.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(str2);
            String c2 = tokenizer.nextToken();
            int vertex = Integer.parseInt(c2);
            while (tokenizer.hasMoreTokens()) {
                val2 = Integer.parseInt(tokenizer.nextToken());
                if (val2 > vertex) {
                    graph.edge[edgeCount].src = vertex - 1;
                    graph.edge[edgeCount].dest = val2 - 1;
                    edgeCount++;
                }
            }
        }
        file2.close();

        int minContractions = 1000000;
        for (int i = 0; i < 200; i++) {
            int calculated = randomContraction(graph);
            if (calculated < minContractions) {
                minContractions = calculated;
            }
        }
        System.out.println(minContractions);
    }
}
