package ClientServer;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Number of threads
		final int NUMTH = 50;
		ExecutorService ex = Executors.newFixedThreadPool(NUMTH);
		CompletionService<StringBuilder> pool = new ExecutorCompletionService<StringBuilder>(ex);
		
		// Number of times task executes
		for(int i=0; i<500000; i++){
			pool.submit(new task());
		}
		
		for(int i=0; i<500000; i++){
			StringBuilder result = pool.take().get();
			System.out.println(result);
		}
		
		// Accept no more threads, finish all existing threads
		ex.shutdown();
		// Wait till all threads have finished
		while(!ex.isTerminated()){}
		System.out.println("Finished all threads");
	}
}// End class Main