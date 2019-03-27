import java.util.*;
import java.io.*;

class Island {
    // Attribute
    private int numberIsland, numberBridge, startIsland;
    // Store adjacency list of an island
    private ArrayList<Integer>[] adjIsland;
    // Store all possible set of lonely island
    private Set<Integer> lonelyIsland;
    // Create array of boolean for marking island whether has been visited or not
    private boolean[] visitedIsland; 
    // Store path between two islands
    private ArrayList<Integer> pathIsland;

    // Constructor
    @SuppressWarnings("unchecked") 
    Island() throws Exception {
        // Input file
        File inputFile = new File("input.txt");
        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        String[] initIsland = line.split(" ");

        // Initialization for island attribute
        this.numberIsland = Integer.parseInt(initIsland[0]);
        this.numberBridge = Integer.parseInt(initIsland[1]);
        this.startIsland = Integer.parseInt(initIsland[2]);
        this.lonelyIsland = new HashSet<Integer>();
        this.adjIsland = new ArrayList[this.getNumberIsland()+1]; 
        for(int i = 1; i <= this.getNumberIsland(); i++) { 
            this.adjIsland[i] = new ArrayList<>(); 
        } 

        // Filling adjacency list from external file
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            else {
                String[] initBridge = line.split(" ");
                int i = Integer.parseInt(initBridge[0]);
                int j = Integer.parseInt(initBridge[1]);
                this.adjIsland[i].add(j);   
            }
        }
    } 

    // Decrease and conquer algorithm starts here
    public void findPathIsland(int i, int j) { 
        // Array of boolean visited initialization
        this.visitedIsland = new boolean[this.getNumberIsland()+1]; 
        this.pathIsland = new ArrayList<>(); 
        this.pathIsland.add(i); 
        findPathIslandRecursive(i, j);
    } 

    private void findPathIslandRecursive(int u, int d) { 
        // Mark source or first island as visited
        this.visitedIsland[u] = true; 

        // Has reached the destination
        if (u == d) { 
            // Print a reachable path of islands
            System.out.println(pathIsland); 
            // Put last island into set as a temporary lonely island
            this.lonelyIsland.add(u);
            // Remove from set the unlonely island
            for (int i = 0; i < this.pathIsland.size()-1; i++) {
                this.lonelyIsland.remove(this.pathIsland.get(i));
            }
            // Mark last island as unvisited
            this.visitedIsland[u]= false; 
        } 

        for (Integer x : this.adjIsland[u]) { 
            // If an island in adjacency list hasn't been visited yet
            if (!this.visitedIsland[x]) {  
                this.pathIsland.add(x); 
                findPathIslandRecursive(x, d); 
                pathIsland.remove(x); 
            } 
        } 
        // Mark island as unvisited
        this.visitedIsland[u] = false; 
    } 
    // Decrease and conquer algorithm ends here

    // Getter
    public int getNumberIsland() {
        return this.numberIsland;
    }
    public int getNumberBridge() {
        return this.numberBridge;
    }
    public int getStartIsland() {
        return this.startIsland;
    }

    // Main Program
    public static void main(String[] args) throws Exception {
        // Starting point for calculating execution time
        long startTime = System.nanoTime();

        // Constructing object
        Island pulau = new Island();

        // Print all possible paths which can be reachable from start point
        System.out.println("All possible paths started from " + pulau.getStartIsland() + " :");
        for (int i = 1; i <= pulau.getNumberIsland(); i++) {
            if (pulau.getStartIsland() != i) {
                pulau.findPathIsland(pulau.getStartIsland(), i);
            }  
        }

        // Print all lonely islands
        System.out.println("Lonely island : " + pulau.lonelyIsland);

        // Ending point for calculating execution time
        long endTime = System.nanoTime();
        long exeTime = endTime-startTime;
        System.out.println(exeTime*0.000000001 + " seconds time taken");
    }
} 