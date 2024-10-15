package Queue;

import java.util.Arrays;
import java.util.NoSuchElementException;

// LinkedList로 구현
public class MyQueue<E> implements Queue<E> {
	private Node<E> head; // 노드의 첫 부분을 가리키는 레퍼런스
	private Node<E> tail; // 노드의 끝 부분을 가리키는 레퍼런스
	private int size; // 리스트 요소 개수

	// 생성자
	public MyQueue() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	// 노드 클래스
	private static class Node<E> {
		private E data;
		private Node<E> next;

		Node(E data) {
			this.data = data;
			this.next = null;
		}
	}

	// 맨 뒤에 요소 추가 (= 리스트의 add())
	@Override
	public boolean offer(E value) {
		Node<E> newNode = new Node<E>(value);

		// 큐가 비어있을 경우
		if (size == 0) {
			head = newNode;
		}
		// 그 외의 경우 마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리키도록 함
		else {
			tail.next = newNode;
		}

		tail = newNode; // tail이 가리키는 노드를 새 노드로 바꿔줌
		size++;

		return true;
	}

	// 맨 앞의 요소 제거 (= 리스트의 remove())
	// 삭제할 요소가 없으면 null 반환
	@Override
	public E poll() {
		// 삭제할 요소가 없을 경우 null 반환
		if (size == 0) {
			return null;
		}

		// 삭제될 요소의 데이터를 반환하기 위한 임시 변수
		E value = head.data;

		// head 노드의 다음 노드
		Node<E> nextNode = head.next;

		// head의 모든 데이터들을 삭제
		// gc가 수거해가도록 함
		head.data = null;
		head.next = null;

		// head가 삭제된 head 노드의 다음 노드를 가리키도록 변경
		head = nextNode;
		size--;

		return value;
	}

	// poll()과 같으나 삭제할 요소가 없을 경우 예외 발생
	@Override
	public E remove() {
		E value = poll();

		if (value == null) {
			throw new NoSuchElementException();
		}

		return value;
	}

	// 가장 앞에 있는 데이터(front)를 삭제하지 않고 확인만 하는 메소드
	@Override
	public E peek() {
		// 요소가 없을 경우 null 반환
		if (size == 0) {
			return null;
		}

		return head.data;
	}

	// peek()와 같으나 얻은 요소가 null 이라면 예외 발생
	@Override
	public E element() {
		E value = peek();

		if (value == null) {
			throw new NoSuchElementException();
		}

		return value;
	}

	// 현재 큐에 있는 요소의 개수를 반환하는 메소드
	@Override
	public int size() {
		return size;
	}

	// 현재 큐가 비어있는지 확인하는 메소드
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	// 찾고자 하는 요소가 큐에 들어있는지 확인하는 메소드
	@Override
	public boolean contains(Object value) {
		// head 데이터부터 i가 null이 될 때 까지 value랑 i의 데이터(i.data)랑 같은지를 비교하고 같으면 true
		for (Node<E> i = head; i != null; i = i.next) {
			if (value.equals(i.data)) {
				return true;
			}
		}

		return false;
	}

	// 큐의 모든 요소를 제거
	@Override
	public void clear() {
		for (Node<E> i = head; i != null;) {
			Node<E> next = i.next;
			i.data = null;
			i.next = null;
			i = next;
		}

		size = 0;
		head = tail = null;
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
