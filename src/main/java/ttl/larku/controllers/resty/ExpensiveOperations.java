package ttl.larku.controllers.resty;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ttl.larku.domain.OperationResult;

@Path("/v1")
public class ExpensiveOperations {

	@Context
	private ServletContext servletContext;
	
	@Resource
	private ManagedExecutorService es;
	
	private int sleepTime = 2000;

	@GET
	@Path("/operation1")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void operation1(@Suspended AsyncResponse response) {
		//ExecutorService es = (ExecutorService) servletContext.getAttribute("executor");

		es.execute(new Runnable() {
			public void run() {
				OperationResult wrapper = new OperationResult();
				List<Integer> results = wrapper.list;
				// create random ints
				for (int i = 0; i < 10; i++) {
					int next = ThreadLocalRandom.current().nextInt();
					results.add(next);
				}

				Response.ResponseBuilder builder;
				builder = Response.ok(wrapper);

				// pretend that we actually did lots of work
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Send the response on it's way.
				response.resume(builder.build());
			}
		});
	}

	/**
	 * Uses a stream to create the list
	 * 
	 * @param response
	 */
	@GET
	@Path("/operation2")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void operation2(@Suspended AsyncResponse response) {
		ExecutorService es = (ExecutorService) servletContext.getAttribute("executor");

		es.execute(new Runnable() {
			public void run() {
				OperationResult wrapper = new OperationResult();
				wrapper.list = IntStream.range(0, 10).map(i -> ThreadLocalRandom.current().nextInt())
						.mapToObj(Integer::new).collect(Collectors.toList());

				Response.ResponseBuilder builder;
				builder = Response.ok(wrapper);

				// pretend that we actually did lots of work
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Send the response on it's way.
				response.resume(builder.build());
			}
		});
	}

	/**
	 * Completable future
	 * 
	 * @param response
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@GET
	@Path("/operation3")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void operation5(@Suspended AsyncResponse response) throws InterruptedException, ExecutionException {
		//ExecutorService es = (ExecutorService) servletContext.getAttribute("executor");

		System.out.println("Before creating CF, thread is " + Thread.currentThread().getName());
		CompletableFuture.supplyAsync(() -> {
			System.out.println("In CF, thread is " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			double d = Math.random();
			if(d > .5){
				throw new RuntimeException("Bad Stuff happened, d was " + d);
			}

			return Arrays.asList(new Integer[] { 10, 20 });
		}, es).handle((List<Integer> l, Throwable ex) -> {
			System.out.println("In Handle, thread is " + Thread.currentThread().getName());
			OperationResult wrapper = new OperationResult();
			//If no exception
			if (ex == null) {
				wrapper.list = l;
				System.out.println(l);
			} else {
				wrapper.error = "Error in operation1: " + ex.getMessage();
				System.out.println(wrapper.error);
			}
			Response.ResponseBuilder builder;
			builder = Response.ok(wrapper);

			// Send the response on it's way.
			response.resume(builder.build());

			return null;

		});

	}

}
