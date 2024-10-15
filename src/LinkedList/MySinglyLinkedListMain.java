package LinkedList;

public class MySinglyLinkedListMain {
	public static void main(String[] args) {
		MySinglyLinkedList<String> list = new MySinglyLinkedList<String>();
		list.add("안녕하세요");
		list.addFirst("어라");
		list.addLast("반갑습니다");
		System.out.println(list);
		System.out.println("첫 번째 단어: " + list.get(0));
		list.remove();
		System.out.println(list);
		list.remove("안녕하세요");
		System.out.println(list);
	}
}
