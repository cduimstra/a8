package ClientServer;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
	private static Socket client = null;
	private static BufferedReader in = null;
	private static PrintWriter out = null;
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		int PortNumber = 8070;
		
		try{
			System.out.println("Connecting to server on port " + PortNumber);
			client = new Socket("localhost", PortNumber);
			System.out.println("Connected to " + client.getRemoteSocketAddress());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
		    System.out.println(e);
	    }
		
		// Number of threads
		final int NUMTH = 2;
		ExecutorService ex = Executors.newFixedThreadPool(NUMTH);
		CompletionService<StringBuilder> pool = new ExecutorCompletionService<StringBuilder>(ex);
		
		// Number of times task executes
		for(int i=0; i<10; i++){
			pool.submit(new task());
		}
		
		for(int i=0; i<10; i++){
			StringBuilder result = pool.take().get();
			result.insert(0,"ADD-");
			out.println(result);
		}
		// Accept no more threads, finish all existing threads
		ex.shutdown();
		// Wait till all threads have finished
		while(!ex.isTerminated()){}
		System.out.println("Finished all threads");
		
		//String answer = in.readLine();
        //System.out.println(answer);
		
		// Close Connections
		try{
			out.close();
			in.close();
			client.close();
		}catch(IOException e){
			System.out.println(e);
		}
	} // End Main
} // End Client
