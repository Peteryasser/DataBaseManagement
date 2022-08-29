package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NodeConnection {
    int portNumber;
    ServerSocket serverSocket;
    Socket socket;
    String currentLine;
    LSMTree lsmTree = new LSMTree();

    Thread listen  = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true){

                try {

                    RingStructure ringStructure= RingStructure.getInstance(5);

                    System.out.println("thread started " + portNumber);
                    //The server will keep listening for new connections
                   Socket socket = serverSocket.accept();
                   //Recive info from other node

                   //Recive info from client
                   InputStreamReader in = new InputStreamReader(socket.getInputStream());
                   //We use this to read the input from the socket
                   BufferedReader reader = new BufferedReader(in);
                   // convert the input to a string
                   String line = reader.readLine();
                   currentLine=line;
                   // given input from client
                    System.out.println("Received : "+ line);

                    if(line.substring(0, 3).equals("get")){

                        String key =line.substring(4,line.length()-1);
                        System.out.println("Key : "+key);
                        int hashCode=key.hashCode();
                        System.out.println("Hash Code is : "+hashCode);

                        int nodeIndexOnRing=ringStructure.find_Node(hashCode);
                        System.out.println("Get index in correspond Node : "+ nodeIndexOnRing);
                        int portNum= ringStructure.nodes_Ports.get(nodeIndexOnRing);
                        System.out.println("Correct Node Port : ------->" + portNum);
                        System.out.println("Is " + portNum +"==" +"curr "+portNumber);

                        if (portNum != portNumber ){
                            startSending(portNum);
                        }else {
                            //return the value to the Client
                            Socket clientSocket = new Socket("localhost", 7777);
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            String value = lsmTree.getValueOf(key);
                            out.println("key : "+ key +" has value of "+ value);


                        }

                    }else if(line.substring(0, 3).equals("set")){

                        String data =line.substring(4,line.length()-1);
                        String key = data.split(",")[0];
                        String value = data.split(",")[1];

                        int hashCode=key.hashCode();
                        System.out.println("Hash Code is : "+hashCode);

                        int nodeIndexOnRing=ringStructure.find_Node(hashCode);
                        System.out.println("Set index in correspond Node : "+ nodeIndexOnRing);
                        int portNum= ringStructure.nodes_Ports.get(nodeIndexOnRing);
                        System.out.println("Correct Node Port : ------->" + portNum);
                        System.out.println("Is " + portNum +"==" +"curr "+portNumber);

                        if (portNum != portNumber ){
                            startSending(portNum);
                        }else {
                            //return the value to the Client
                            lsmTree.put(key,value);
                            System.out.println("Key : "+key);
                            System.out.println("Value : "+value);

                            Socket clientSocket = new Socket("localhost", 7777);
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println("key : "+ key +" has value of "+ value);


                        }
                    }

                   PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                   out.println("get");

            } catch (IOException e) {
                e.printStackTrace();
            }
                System.out.println("____________________________________________");
            }
        }
    });

    Thread sending = new Thread(new Runnable() {
        private String line;
        public Runnable init(String line){
            System.out.println("init func :"+currentLine);

            this.line=line;
            return this;
        }
        @Override
        public void run() {

            try {
             //   Socket s = new Socket("localhost", 5000 + randomConnectToNode);

                PrintWriter out = new PrintWriter( socket.getOutputStream() , true);

                String input =currentLine;

                System.out.println(input);

                out.println(input);

                InputStreamReader in = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(in);

                String line = reader.readLine();
                System.out.println("Received from server: " + line);

                System.out.println("Received from Server: " + line);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("____________________________________________");

        }
    }.init(currentLine));

    public void startListen(int portNumber){
        try {
            serverSocket = new ServerSocket(portNumber);
            this.portNumber=portNumber;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(listen);
        t.start();
    }

    public void startSending(int portNumber){
        try {
            //I think there is a problem here a should destroy the socket after using if
            System.out.println("make socket to port number "+ portNumber);
            socket = new Socket("localhost", portNumber);
        } catch (IOException e) {
            System.out.println("socket Error ----");
            throw new RuntimeException(e);
        }
        //How to pass a parameter to a new thread ???
        sending.run();

    }


}
