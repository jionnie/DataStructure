package LinkedList;

import java.util.Arrays;
import java.util.Objects;

public class MySinglyLinkedList<E> {
	// 하나의 노드는 다음 노드 정보만 가지고 있기 때문에,
	// 가장 처음과 가장 마지막 노드를 참조하고 있는 포인트를 알기 위해 head와 tail이 필요
	private Node<E> head; // Node의 첫 부분을 가리키는 포인트
	private Node<E> tail; // Node의 마지막 부분을 가리키는 포인트
	
	private int size; // 리스트에 있는 요소 개수(연결된 노드 개수)
	
	public MySinglyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	// Node 클래스는 오로지 MySinglyLinkedList 클래스에서만 이용됨
	// 그러므로 inner static class
	// 노드는 그냥 객체임
	private static class Node<E> {
		private E data;	// Node에 담을 데이터
		private Node<E> next; // 다음 Node 객체를 가리키는 레퍼런스 (다음 연결 요소의 주소를 저장)
		
		// 생성자
		Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	// 노드의 next 필드 자체가 그 다음 노드를 가리킴
	// 따라서 index 전 까지만 탐색
	private Node<E> search(int index) {
		// head(처음 위치)에서 부터 차례로 index 까지 검색
		Node<E> n = head;
		for (int i = 0; i < index; i++) {
			n = n.next;
		}
		return n;
	}
	
	// 첫 번째 위치에 요소 추가하는 메소드
	public void addFirst(E value) {
		// 1. 먼저 가장 앞의 요소를 가져옴
		Node<E> first = head;
		
		// 2. 새 노드 생성 (이 때 데이터와 next 포인트를 준다)
		Node<E> new_node = new Node<>(value, first);
		
		// 3. 요소가 추가 되었으니 size를 늘린다
		size++;
		
		// 4. 맨 앞의 요소가 추가 되었으니 head를 업데이트 한다
		head = new_node;
		
		// 5. 만일 최초로 요소가 add 된 것이면 head와 tail이 가리키는 요소는 같게 된다
		if (first == null) {
			tail = new_node;
		}
	}
	
	// 마지막 위치에 요소 추가하는 메소드
	public void addLast(E value) {
		// 1. 먼저 가장 뒤의 요소를 가져옴
		Node<E> last = tail;
		
		// 2. 새 노드 생성 (맨 마지막 요소 추가니까 next는 null이다)
		Node<E> newNode = new Node<>(value, null);
		
		// 3. 요소가 추가되었으나 size를 늘린다
		size++;
		
		// 4. 맨 뒤에 요소가 추가되었으니 tail를 업데이트한다
		tail = newNode;
		
		if (last == null) {
			// 5. 만일 최초로 요소가 add 된 것이면 head와 tail이 가리키는 요소는 같게 된다
			head = newNode;
		} else {
			// 6. 최초 추가가 아니라면 last 변수(추가 되기 전 마지막이었던 요소)에서 추가된 새 노드를 가리키도록 업데이트
			last.next = newNode;
		}
	}
	
	// add의 동작은 addLast와 같음
	// 실제로 LinkedList API를 보면 add() 호출 시 addLast()를 호출함
	public boolean add(E value) {
		addLast(value);
		return true;
	}
	
	// 리스트 중간에 삽입하는 메소드
	// 인덱스가 처음과 끝이면 addFirst(), addLast() 재활용
	// 추가하려는 위치 구하기 위해 search() 이용
	// 이전 노드(prev_node)와 다음 노드(next_node) 참조값을 변수에 백업하는 것이 포인트
	// prev_node의 next -> new_node, new_node의 next -> next_node 연결 
	public void add(int index, E value) {
		// 1. 인덱스가 0보다 작거나 size보다 같거나 클 경우 에러
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		// 2. 추가하려는 index가 0이면 addFirst 호출
		if (index == 0) {
			addFirst(value);
			return;
		}
		
		// 3. 추가하려는 index가 size - 1과 같으면 addLast 호출
		if (index == size - 1) {
			addLast(value);
			return;
		}
		
		// 4. 추가하려는 위치의 이전 노드 얻기
		Node<E> prev_node = search(index - 1);
		
		// 5. 추가하려는 위치의 다음 노드 얻기
		Node<E> next_node = prev_node.next;
		
		// 6. 새 노드 생성 (바로 다음 노드와 연결)
		Node<E> newNode = new Node<>(value, next_node);
		
		// 7. size 증가
		size++;
		
		// 8. 이전 노드를 새 노드와 연결
		prev_node.next = newNode;
	}
	
	// 맨 앞 요소를 제거 (제거한 요소는 반환)
	public E removeFirst() {
		// 1. 만약 삭제할 요소가 아무것도 없으면 에러
		if (head == null) {
			throw new IndexOutOfBoundsException();
		}
		
		// 2. 삭제될 첫 번째 요소의 데이터를 백업
		E returnValue = head.data;
		
		// 3. 두 번째 노드를 임시 저장
		Node<E> first = head.next;
		
		// 4. 첫 번째 노드의 내부 요소를 모두 삭제
		// head가 두 번째 노드를 참조하게 될 것이므로 null 처리해서 GC가 수거해가도록 함
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
	
	// 기본 remove() 동작은 add()와 달리 첫째 요소를 처리
	public E remove() {
		return removeFirst();
	}
	
	// 인덱스 위치의 요소를 제거하는 메소드
	public E remove(int index) {
		// 1. 인덱스가 0보다 작거나 size 보다 크거나 같은 겨우 에러 (size와 같을 경우 빈 공간을 가리키는 것)
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		// 2. 인덱스가 0이면 removeFirst 메소드 실행하고 반환
		if (index == 0) {
			removeFirst();
		}
		
		// 3. 삭제할 위치의 이전 노드 저장
		Node<E> prev_node = search(index - 1);
		
		// 4. 삭제할 위치의 노드 저장
		Node<E> del_node = prev_node.next;
		
		// 5. 삭제할 위치의 다음 노드 저장
		Node<E> next_node = del_node.next;
		
		// 6. 삭제될 첫 번째 요소의 데이터를 백업
		E returnValue = del_node.data;
		
		// 7. 삭제 노드의 내부 요소를 모두 삭제
		del_node.data = null;
		del_node.next = null;
		
		// 8. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 9. 이전 노드가 다음 노드를 가리키도록 업데이트
		prev_node.next = next_node;
		
		// 10. 마지막으로 삭제된 요소를 반환
		return returnValue;
	}
	
	// 값으로 요소를 remove 하는 메소드
	public boolean remove(Object value) {
		// 1. 만일 삭제할 요소가 아무것도 없으면 에러
		if (head == null) {
			throw new RuntimeException();
		}
		
		// 2. 이전 노드, 삭제 노드, 다음 노드를 저장할 변수 선언
		Node<E> prev_node = null;
		Node<E> del_node = null;
		Node<E> next_node = null;
		
		// 3. 루프 변수 선언
		Node<E> i = head;
		
		// 4. 노드의 next를 순회하면서 해당 값을 찾음
		while (i != null) {
			if (Objects.equals(i.data, value)) {
				// 노드의 값과 매개변수 값이 같으면 삭제 노드에 요소를 대입하고 break;
				del_node = i;
				break;
			}
			
			// Singly LinkedList에는 prev 정보가 없기 때문에 이전 노드에도 요소를 일일히 대입하여야 함
			prev_node = i;
			i = i.next;
		}
		
		// 5. 만일 찾은 요소가 없다면 리턴
		if (del_node == null) {
			return false;
		}
		
		// 6. 만일 삭제하려는 노드가 head라면, 첫 번째 요소를 삭제하는 것이니 removeFirst()를 사용
		if (del_node == head) {
			removeFirst();
			return true;
		}
		
		// 7. 다음 노드에 삭제 노드 next의 요소를 대입
		next_node = del_node.next;
		
		// 8. 삭제 요소 데이터 모두 제거
		del_node.data = null;
		del_node.next = null;
		
		// 9. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 10. 이전 노드가 다음 노드를 참조하도록 업데이트
		prev_node.next = next_node;
		
		return true;
	}
	
	// Singly LinkedList는 노드 prev 개념이 없기 때문에 노드의 이전 요소를 참조할 방법이 없으므로,
	// 결국은 처음부터 끝까지 순회해야 함
	public E removeLast() {
		return remove(size - 1);
	}
	
	// 특정 인덱스의 값을 얻는 메소드
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return search(index).data;
	}
	
	// 특정 인덱스에 값을 설정하는 메소드
	public void set(int index, E value) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		// 1. search 메소드를 이용해 교체할 노드를 얻음
		Node<E> replace_node = search(index);
		
		// 2. 교체할 노드의 요소를 변경
		replace_node.data = null;
		replace_node.data = value;
	}
	
	// 배열을 만들고 각 배열 인덱스마다 리스트의 노드 요소값을 대입해 복사를 한 뒤 문자열로 만들어 반환
	@Override
	public String toString() {
		// 1. 만일 head가 null일 경우 빈 배열
		if (head == null) {
			return "[]";
		}
		
		// 2. 현재 size만큼 배열 생성
		Object[] array = new Object[size];
		
		// 3. 노드 next를 순회하면서 배열에 노드값을 저장
		int index = 0;
		Node<E> n = head;
		while (n != null) {
			array[index] = (E) n.data;
			index++;
			n = n.next;
		}
		
		// 3. 배열을 스트링화하여 반환
		return Arrays.toString(array);
	}
}
