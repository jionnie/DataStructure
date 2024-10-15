package Queue;

public class MyQueueMain {
	public static void main(String[] args) {
//		MyQueueWithArray<Integer> queue = new MyQueueWithArray<Integer>();
//		queue.offer(1);
//		queue.offer(2);
//		queue.offer(3);
//		queue.offer(3);
//		queue.offer(3);
//		queue.offer(3);
//		queue.poll();
//		System.out.println(queue.peek());
//		System.out.println(Math.abs(queue.size()));
//		System.out.println(queue);
		
		MyQueue<String> queue = new MyQueue<String>();
		queue.offer("안녕하세요");
		queue.offer("감사해요");
		queue.offer("잘 있어요");
		queue.offer("다시 만나요");
		System.out.println(queue);
		System.out.println(queue.poll());
		System.out.println(queue);
		queue.clear();
		System.out.println(queue);
	}
}
