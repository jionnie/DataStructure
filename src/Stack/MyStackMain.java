package Stack;

public class MyStackMain {
	public static void main(String[] args) {
		MyStackWithArray<Integer> stack = new MyStackWithArray<Integer>();
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		
		System.out.println(stack);
		
		System.out.println(stack.peek());
		
		System.out.println(stack.search(4));
		System.out.println(stack.search(3));
		
		stack.pop();
		stack.pop();
		stack.pop();
		
		System.out.println(stack);
	}
}
