package org.example;

import java.util.*;
import org.apache.commons.codec.digest.MurmurHash3;

public class RingStructure {

    int numberOfNodes, numberOfVirtualNodes;
    private volatile static RingStructure uniqueInstance;
    private RingStructure(int numberOfNodes, int numberOfVirtualNodes) {
        this.numberOfNodes=numberOfNodes;
        this.numberOfVirtualNodes=numberOfVirtualNodes;
    }
    public static RingStructure getInstance(int numberOfNodes, int numberOfVirtualNodes) {
        if (uniqueInstance==null){
            synchronized (RingStructure.class){
                if (uniqueInstance==null){
                    uniqueInstance= new RingStructure(numberOfNodes, numberOfVirtualNodes);
                }
            }
        }
        return uniqueInstance;
    }

     int find_Node(int K) {
        // Lower and upper bounds
        int start = 0;
        int end = numberOfNodes - 1;
        // Traverse the search space
        while (start <= end) {
            int mid = (start + end) / 2;
            // If K is found
            if (keys.get(mid) == K)
                return mid;
            else if (keys.get(mid)< K)
                start = mid + 1;
            else
                end = mid - 1;
        }

        // Return insert position
        return keys.get((end + 1)%this.numberOfNodes);
    }
    //5000 get numberOf nodes then number of virtual nodes
    // fixed to 10
     Map<Integer,Integer> nodes_Ports = new HashMap<>();
     List<Integer> keys= new ArrayList<>();
    void buildMap(int numberOfVirtualNodes) {
        int range = 2000000000;
        for (int i = 1; i <= numberOfNodes; i++) {
            keys.add(range/numberOfNodes);
            nodes_Ports.put(range/numberOfNodes,5000+i);

            int amp = 414248133;
            for (int j = 0; j < numberOfVirtualNodes; j++) {
                amp += 87187;
                int portNumber = 5000 + i;
                String string = Integer.toString(amp).concat(Integer.toString(portNumber*7979));
                int hashed = MurmurHash3.hash32x86(string.getBytes());
                keys.add(hashed);
                nodes_Ports.put(hashed,portNumber);
            }
        }
        Collections.sort(keys);
    }
    void addNode(int number){

    }

    public static void main(String[] args) {
        //buildMap(5,20);

    }

}
