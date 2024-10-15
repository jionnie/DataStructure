package LinearList;

public class MyArrayListMain {
	public static void main(String[] args) {
		MyArrayList<Integer> list = new MyArrayList<Integer>();
		
		list.add(3);
		list.add(5);
		list.add(1);
		list.add(10);
		list.add(4);
		list.add(2, 11);
		System.out.println(list);
	}
}
