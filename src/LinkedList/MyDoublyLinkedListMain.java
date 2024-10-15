package LinkedList;

public class MyDoublyLinkedListMain {
	public static void main(String[] args) {
		MyDoublyLinkedList<String> list = new MyDoublyLinkedList<String>();
		list.add("안녕하세요");
		list.add("감사해요");
		list.add("잘있어요");
		list.add("다시 만나요");
		System.out.println(list);
		list.clear();
		System.out.println(list);
		System.out.println();
		
		list.add("안녕하세요");
		list.add("감사해요");
		System.out.println(list);
		list.add(1, "잘있어요");
		System.out.println(list);
		list.addFirst("다시만나요");
		System.out.println(list);
		System.out.println();
		
		list.remove();
		System.out.println(list);
	}
	
}
