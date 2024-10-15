package LinkedList;

public class MyCircularDoublyLinkedListMain {
	public static void main(String[] args) {
		MyCircularDoublyLinkedList<String> list = new MyCircularDoublyLinkedList<String>();
		
		list.addFirst("안녕하세요");
		list.addLast("감사해요");
		list.addFirst("잘있어요");
		list.addLast("다시 만나요");
		System.out.println(list);
		list.removeFirst();
		System.out.println(list);
	}
}
