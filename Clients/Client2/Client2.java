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

public class Client2 {
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		int PortNumber = 4455;
		
		Socket client = new Socket("localhost", PortNumber);
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		
		// Number of threads
		final int NUMTH = 50;
		ExecutorService ex = Executors.newFixedThreadPool(NUMTH);
		CompletionService<StringBuilder> pool = new ExecutorCompletionService<StringBuilder>(ex);
		
		// Number of times task executes
		for(int i=0; i<500000; i++){
			pool.submit(new task2());
		}
		
		for(int i=0; i<500000; i++){
			StringBuilder result = pool.take().get();
			out.println(result);
		}
		// Accept no more threads, finish all existing threads
		ex.shutdown();
		// Wait till all threads have finished
		while(!ex.isTerminated()){}
		System.out.println("Finished all threads");
				
		String answer = in.readLine();
        System.out.println(answer);
		
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
