package LinkedList;

public class MyCircularDoublyLinkedList<E> {
	
	private Node<E> head; // 노드의 첫 부분을 가리키는 레퍼런스
	private Node<E> tail; // 노드의 끝 부분을 가리키는 레퍼런스
	
	private int size; // 리스트 요소 개수
	
	// 생성자
	public MyCircularDoublyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	// 노드 클래스
	private static class Node<E> {
		private E data; // Node에 담을 데이터
		private Node<E> next; // 다음 Node 객체를 가리키는 레퍼런스
		private Node<E> prev; // 이전 Node 객체를 가리키는 레퍼런스
		
		Node(Node<E> prev, E data, Node<E> next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	// 첫 번째 위치에 요소 추가
	public void addFirst(E value) {
		// 1. head와 tail을 임시 백업함
		Node<E> first = head;
		Node<E> last = tail;
		
		// 2. 새 노드를 추가 (이 때, 첫 번째 노드니까 prev는 null이 되고 next는 head가 가리키는 노드가 됨)
		Node<E> new_node = new Node<>(null, value, first);
		
		// 3. 노드를 추가하였으니 리스트 크기 증가
		size++;
		
		// 4. 첫 번째 기준이 변경되었으니 head를 삽입된 새 노드를 참조하도록 업데이트
		head = new_node;
		
		// 5. 만일 빈 리스트에서 최초의 요소 추가였을 경우,
		if (first == null) {
			tail = new_node; // tail도 첫 번째 노드를 바라보도록 업데이트
			
			// circular 처리
			new_node.next = new_node; // 추가 노드는 서로 자기 자신을 참조하게 함
			new_node.prev = new_node;
		}
		// 6. 만일 빈 리스트가 아닐 경우,
		else {
			first.prev = new_node; // 추가되기 이전 첫 번째였던 노드에서 prev를 새 노드로 참조하도록 업데이트
			
			// circular 처리
			new_node.prev = last; // 추가 노드(첫 번째)의 prev를 마지막 노드를 참조하도록 업데이트
			last.prev = new_node; // 마지막 노드의 next가 추가 노드를 참조하도록 업데이트
		}
	}
	
	// 마지막 위치에 요소 추가
	public void addLast(E value) {
		// 1. head와 tail을 임시 백업함
		Node<E> first = head;
		Node<E> last = tail;
		
		// 2. 새 노드를 추가 (이 때, 마지막 노드니까 next는 null이 되고 prev는 tail이 가리키는 노드가 됨)
		Node<E> new_node = new Node<>(last, value, null);
		
		// 3. 노드를 추가하였으니 리스트 크기 증가
		size++;
		
		// 4. 마지막 노드 기준이 변경 되었으니 tail을 삽입된 새 노드를 참조하도록 업데이트
		tail = new_node;
		
		// 5. 만일 빈 리스트에서 최초의 요소 추가였을 경우,
		if (last == null) {
			head = new_node; // head도 첫 번째 노드를 바라보도록 업데이트
			
			// circular 처리
			new_node.next = new_node; // 추가 노드는 서로 자기 자신을 참조하게 됨
			new_node.prev = new_node;
		}
		// 6. 만일 빈 리스트가 아닐 경우,
		last.next = new_node; // 추가되기 이전 마지막이였던 노드에서 next를 새 노드로 참조하도록 업데이트
		
		// circular 처리
		new_node.next = first; // 추가 노드(마지막)의 next를 첫 번째 노드를 참조하도록 업데이트
		first.prev = new_node; // 첫 번째 노드의 prev를 추가 노드를 참조하도록 업데이트
	}
	
	// 마지막 위치에 요소 추가 (성공하면 true) -> Doubly LinkedList와 동일
	// 지정된 요소에 위치 추가 -> Doubly LinkedList와 동일
	
	// 맨 앞 요소를 제거 (제거한 요소는 반환)
	public E removeFirst() {
		// 1. 만일 삭제할 요소가 아무 것도 없으면 에러
		if (head == null) {
			throw new RuntimeException();
		}
		
		// 2. 삭제될 첫 번째 요소의 데이터를 백업
		E returnValue = head.data;
		
		// 3. 두 번째 노드를 임시 저장
		Node<E> first = head.next;
		
		// 4. 첫 번째 노드의 내부 요소를 모두 삭제
		// head.prev는 이미 null
		head.data = null;
		head.next = null;
		
		// 5. head가 다음 노드를 가리키도록 업데이트
		head = first;
		
		// 6. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 7. 만일 리스트의 유일한 값을 삭제해서 빈 리스트가 될 경우, tail도 null 처리
		if (head == null) {
			tail = null;
		}
		
		// 8. 마지막으로 삭제된 요소를 반환
		return returnValue;
	}
	
	// 맨 앞 요소를 제거 -> Doubly LinkedList와 동일
	
	// 맨 뒤 요소를 제거
	
	
	// 인덱스 위치의 요소를 제거 -> Doubly LinkedList와 동일
	// 요소값이 일치하는 위치의 요소를 제거 (중복 요소값이 있을 경우 맨 앞의 요소가 제거) -> Doubly LinkedList와 동일
	
	// 요소를 출력하기 위해 Object 클래스의 toString() 메소드 오버라이딩
	// Circular LinkedList는 노드 간에 모두 참조가 되어있기 때문에,
	// next나 prev가 null일 경우의 수는 사용하지 못함
	// 따라서 리스트의 사이즈까지 인덱스 순회로 구성
	@Override
	public String toString() {
		if (head == null) {
			return "[]";
		}
		
		Node<E> n = head;
		String result = "[";
		
		for (int index = 0; index < size; index++) {
			if (index == size - 1) {
				result += n.data;
				break;
			}
			result += n.data + ", ";
			n = n.next;
		}
		
		result += "]";
		
		return result;
	}
}
