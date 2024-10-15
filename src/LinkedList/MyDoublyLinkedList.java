package LinkedList;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyDoublyLinkedList<E> {
	// 하나의 노드는 다음 노드 정보만 가지고 있기 때문에, 가장 처음과 마지막 노드를 참조하고 있는 포인트가 필요
	private Node<E> head; // 리스트의 가장 첫 노드를 가리키는 레퍼런스
	private Node<E> tail; // 리스트의 가장 마지막 노드를 가리키는 레퍼런스
	
	private int size; // 리스트 요소 개수 (연결된 노드의 개수)
	
	// 생성자
	public MyDoublyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	// 노드는 그냥 객체임
	// 여러 노드 객체들을 내부적으로 체인처럼 연결
	// inner static class
	private static class Node<E> {
		private E data; // Node에 담을 데이터
		private Node<E> next; // 다음 Node 객체를 가리키는 레퍼런스
		private Node<E> prev; // 이전 Node 객체를 가리키는 레퍼런스
		
		// 생성자 (이전 노드 포인트 | 데이터 | 다음 노드 포인트)
		Node(Node<E> prev, E data, Node<E> next) {
			this.prev = prev;
			this.data = data;
			this.next = next;
		}
	}
	
	// 만일 인덱스가 0(처음)에 가깝다면 순차 방향 탐색
	// 만일 인덱스가 size(마지막)에 가깝다면 역 방향 탐색
	private Node<E> search(int index) {
		Node<E> n; // 반환할 노드
		
		// 1. 만일 인덱스가 시작에 가까우면 순차 탐색
		if ((size / 2) > index) {
			n = head;
			for (int i = 0; i < index; i++) {
				n = n.next;
			}
		}
		// 2. 만일 인덱스가 끝에 가까우면, 역순 탐색
		else {
			n = tail;
			for (int i = size - 1; i > index; i--) {
				n = n.prev;
			}
		}
		
		return n;
	}
	
	// 첫 번째 위치에 요소 추가
	public void addFirst(E value) {
		// 1. head를 임시 백업함
		Node<E> first = head;
		
		// 2. 새 노드를 추가
		Node<E> new_node = new Node<>(null, value, first);
		
		// 3. 노드를 추가하였으니 리스트 크기 증가
		size++;
		
		// 4. 첫 번째 기준이 변경되었으니 head를 삽입된 새 노드를 참조하도록 업데이트
		head = new_node;
		
		if (first == null) {
			// 5. 만일 빈 리스트에서 최초의 요소 추가였을 경우, tail도 첫째 노드를 바라보도록 업데이트
			tail = new_node;
		} else {
			first.prev = new_node;
		}
	}
	
	// 마지막 위치에 요소 추가
	public void addLast(E value) {
		// 1. tail을 임시 백업함
		Node<E> last = tail;
		
		// 2. 새 노드를 추가 (이 떄 마지막 노드니까 next는 null이 되고 prev는 tail이 가리키는 노드가 됨)
		Node<E> new_node = new Node<>(last, value, null);
		
		// 3. 노드를 추가하였으니 리스트 크기 증가
		size++;
		
		// 4. 마지막 기준이 변경 되었으니 tail을 삽입된 새 노드로 참조하도록 업데이트
		tail = new_node;
		
		// 5. 만일 빈 리스트에서 최초의 요소 추가였을 경우, head도 첫째 노드를 바라보도록 업데이트
		if (last == null) {
			head = new_node;
		}
		// 6. 만일 빈 리스트가 아닐 경우, 추가되기 이전 마지막 노드의 next가 새 노드를 참조하도록 업데이트
		else {
			last.next = new_node;
		}
	}
	
	// 마지막 위치에 요소 추가 (성공하면 true)
	public boolean add(E value) {
		addLast(value);
		return true;
	}
		
	// 특정 위치에 요소 추가
	public void add(int index, E value) {
		// 1. 인덱스 범위 체크
		// 인덱스가 0보다 작거나 size 보다 클 경우 에러
		// (인덱스가 size 보다 크면 마지막 요소 다음 빈 공간의 빈 공간을 가리키는거니까)
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		
		// 2. 추가하려는 index가 0이면 addFirst 호출
		if (index == 0) {
			addFirst(value);
			return;
		}
		
		// 3. 추가하려는 index가 size와 같으면 addLast 호출
		if (index == size) {
			addLast(value);
			return;
		}
		
		// 4. 추가하려는 위치의 다음 노드 얻기 (현재 해당 위치의 인덱스 노드)
		// 값이 추가되면서 밀려남
		Node<E> next_node = search(index);
		
		// 5. 추가하려는 위치의 이전 노드 얻기 (현재 해당 위치의 인덱스 노드)
		// 값이 추가되면서 밀려남
		Node<E> prev_node = next_node.prev;
		
		// 6. 새 노드 생성 (바로 이전 / 다음 노드와 연결됨)
		Node<E> new_node = new Node<>(prev_node, value, next_node);
		
		// 7. 노드를 추가하였으니 리스트 크기 증가
		size++;
		
		// 8. 이전 노드의 next를 새 노드에 연결
		prev_node.next = new_node;
		
		// 9. 다음 노드의 prev를 새 노드에 연결
		next_node.prev = new_node;
	}
	
	// 맨 앞 요소를 제거 (제거한 요소는 반환)
	public E removeFirst() {
		// 1. 만일 삭제할 요소가 아무것도 없으면 에러
		if (head == null) {
			throw new NoSuchElementException();
		}
		
		// 2. 삭제될 첫 번째 요소의 데이터를 백업
		E value = head.data;
		
		// 3. 두 번째 노드를 임시 저장
		Node<E> first = head.next;
		
		// 4. 첫 번째 노드의 내부 요소를 모두 삭제
		// head.prev는 이미 null
		head.data = null;
		head.next = null;
		
		// 5. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 6. head가 다음 노드를 가리키도록 업데이트
		head = first;
		
		// 7. 만일 리스트의 유일한 값을 삭제해서 빈 리스트가 될 경우, tail도 null 처리
		if (first == null) {
			tail = null;
		}
		// 8. 만일 빈 리스트가 아닐 경우, 삭제 되기 이전 두 번째였던 first가 첫 번째 노드가 되니 prev를 null 처리
		first.prev = null;
		
		// 9. 마지막으로 삭제된 요소를 반환
		return value;
	}
	
	// 맨 앞 요소를 제거
	public E remove() {
		return removeFirst();
	}
	
	// 맨 뒤 요소를 제거
	public E removeLast() {
		// 1. 만일 삭제할 요소가 아무것도 없으면 에러
		if (tail == null) {
			throw new NoSuchElementException();
		}
		
		// 2. 삭제될 마지막 요소의 데이터를 백업
		E value = tail.data;
		
		// 3. 마지막 노드의 이전 노드를 임시 저장
		Node<E> last = tail.prev;
		
		// 4. 마지막 노드의 내부 요소를 모두 삭제
		// tail.next는 이미 null
		tail.data = null;
		tail.prev = null;
		
		// 5. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 6. tail이 이전 노드를 가리키도록 업데이트
		tail = last;
		
		if (last == null) {
			// 7. 만일 리스트의 유일한 값을 삭제해서 빈 리스트가 될 경우, head도 null 처리
			head = null;
		}
		// 8. 만일 빈 리스트가 아닐 경우, 삭제되기 이전 마지막의 노드였던 last가 마지막 노드가 되니 next를 null 처리
		else {
			last.next = null;
		}
		
		// 9. 마지막으로 삭제된 요소를 반환
		return value;
	}
	
	// 인덱스 위치의 요소를 제거
	public E remove(int index) {
		// 1. 인덱스가 0보다 작거나 size 보다 크거나 같은 경우 에러 (size와 같을 경우 빈 공간을 가리키는 거니까)
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		// 2. 인덱스가 0이면 removeFirst 메소드 실행
		if (index == 0) {
			return removeFirst();
		}
		
		// 3. 인덱스가 size - 1(마지막 요소 위치) 이면 removeLast 메소드 실행
		if (index == size - 1) {
			removeLast();
		}
		
		// 4. 삭제할 위치의 노드 저장
		Node<E> del_node = search(index);
		
		// 5. 삭제할 위치의 이전 노드 저장
		Node<E> prev_node = del_node.prev;
		
		// 6. 삭제할 위치의 다음 노드 저장
		Node<E> next_node = del_node.next;
		
		// 7. 삭제될 첫 번째 요소의 데이터를 백업
		E value = del_node.data;
		
		// 8. 삭제 노드의 내부 요소를 모두 삭제
		del_node.data = null;
		del_node.prev = null;
		del_node.next = null;
		
		// 9. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 10. 이전 노드가 다음 노드를 가리키도록 업데이트
		prev_node.next = next_node;
		
		// 11. 다음 노드가 이전 노드를 가리키도록 업데이트
		next_node.prev = prev_node;
		
		// 12. 마지막으로 삭제된 요소를 반환
		return value;
	}
	
	// 요소값이 일치하는 위치의 요소를 제거 (중복 요소값이 있을 경우 맨 앞의 요소를 제거)
	// search() 메소드를 사용할 수 없기 때문에 직접 순회를 해야함
	public boolean remove(Object value) {
		
		// 1. 삭제 노드를 저장할 변수 선언
		Node<E> del_node = null;
		
		// 2. 리스트를 루프할 변수 선언
		Node<E> i = head;
		
		// 3. 노드의 next를 순회하면서 해당 값을 찾음
		while (i != null) {
			// 노드의 값과 매개변수 값이 같으면
			if (Objects.equals(i.data, value)) {
				del_node = i;
				break;
			}
			
			i = i.next;
		}
		
		// 4. 만일 찾은 요소가 없다면 false 리턴
		if (del_node == null) {
			return false;
		}
		
		// 5. 만일 삭제하려는 노드가 head(첫 번째 요소)라면, 기존 removeFirst()를 사용
		if (del_node == head) {
			removeFirst();
			return true;
		}
		
		// 6. 만일 삭제하려는 노드가 tail(마지막 요소)라면, 기존 removeLast() 사용
		if (del_node == tail) {
			removeLast();
			return true;
		}
		
		// 7. 이전 노드, 다음 노드 구하기
		Node<E> prev_node = del_node.prev;
		Node<E> next_node = del_node.next;
		
		// 8. 삭제 요소 데이터 모두 제거
		del_node.data = null;
		del_node.prev = null;
		del_node.next = null;
		
		// 9. 요소가 삭제 되었으니 크기 감소
		size--;
		
		// 10. 이전 노드와 다음 노드끼리 서로 연결
		prev_node.next = next_node;
		next_node.prev = prev_node;
		
		return true;
	}
	
	// 해당 인덱스의 값 얻기
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return search(index).data;
	}
	
	// 해당 인덱스에 값 설정하기
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
	
	// 해당 값이 들어있는 인덱스의 위치를 얻기
	// 순차대로 검색해서 위치 반환
	// 만일 찾고자 하는 값이 배열에 중복으로 여러개 들어있으면 가장 먼저 검색되는 요소의 위치 반환
	public int indexOf(Object value) {
		Node<E> n = head;
		
		int i = 0;
		
		while (n != null) {
			if (Objects.equals(value, n.data)) {
				return i;
			}
			
			i++;
			n = n.next;
		}
		
		return -1;
	}
	
	// 억순으로 검색해서 위치 반환
	public int lastIndexOf(Object value) {
		Node<E> n = tail;
		
		int i = size - 1;
		
		while (n != null) {
			if (Objects.equals(value, n.data)) {
				return i;
			}
			
			i--;
			n = n.prev;
		}
		
		return -1;
	}
	
	// MyLinkedList 클래스에서는 size 변수가 private 접근제한자를 갖기 때문에,
	// 만일 외부에서 참조가 필요하다면 메소드를 통해 반환하는 식으로 처리
	public int size() {
		return size;
	}
	
	// 리스트가 비어있는지 아닌지 확인하는 메소드
	public boolean isEmpty() {
		return size == 0;
	}
	
	// 모든 요소 지우기
	public void clear() {
		// i.next가 null이면 마지막을 의미하는거니, null이 아닐 때까지 순회
		Node<E> i = head;
		
		while (i != null) {
			i.data = null;
			i.next = null;
			i.prev = null;
			
			i = i.next;
		}
		
		head = null;
		tail = null;
		
		size = 0; // 모든 요소를 지웠으니 size도 초기화
	}
	
	// 사용자가 찾고자 하는 요소가 존재 하는지 안 하는지 반환
	public boolean contains(Object value) {
		return indexOf(value) != -1; // -1이 아니라는 말은 요소가 리스트에 존재한다는 것
	}
	
	// 요소들을 출력하기 위해 Object 클래스의 toString() 메소드 오버라이딩
	// 배열을 만들고 각 배열 인덱스마다 리스트의 노드 요소값을 대입해 복사한 뒤 문자열로 만들어 반환
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
