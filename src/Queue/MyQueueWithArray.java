package Queue;

import java.util.Arrays;

// 배열을 이용하여 큐를 구현하는 경우 원형 큐 형태로 구현해야함
public class MyQueueWithArray<E> {
	private static final int DEFAULT_CAPACITY = 6; // 최소 용량 크기
	private Object[] arr; // 요소를 담을 내부 배열
	private int front; // 가장 첫 번째 요소를 가리키는 포인트
	private int rear; // 가장 마지막 요소를 가리키는 포인트

	// 생성자
	public MyQueueWithArray() {
		this.arr = new Object[DEFAULT_CAPACITY];
		rear = -1;
		front = 0;
	}

	// 큐가 꽉 찼는지 확인하는 함수
	// queue[front]가 null이면 큐가 비어있는 것, null이 아니면 꽉 찬 것
	public boolean isFull() {
		return rear == arr.length - 1;
	}

	// 큐가 비어있는지 확인하는 함수
	public boolean isEmpty() {
		return front == rear;
	}

	// 클래스 내부에서만 실행되는 메소드이니 private
	private void resize() {
		int arr_capacity = arr.length - 1; // 마지막 인덱스

		if (rear == arr_capacity) {
			// 넉넉하게 공간을 유지하기 위해 현재 용량의 두 배로 설정
			int new_capacity = arr.length * 2;

			// 복사할 배열을 new_capacity 용량 만큼 설정하고 arr 원소들을 전체 복사
			arr = Arrays.copyOf(arr, new_capacity);
		}

		// 용량에 비해 데이터 양이 적은 경우 (최적화)
		if (rear < (arr_capacity / 2)) {
			// 기존 용량의 반을 설정
			int half_capacity = arr.length / 2;

			// half_capacity와 default 용량 중 큰 걸 복사
			arr = Arrays.copyOf(arr, half_capacity);
			return;
		}

//				for (int i = 0; i < size; i++) {
//					newArr[i] = arr[front + i];
//				}
//				arr = newArr;
//				front = 0;
//				rear = size - 1;
	}

	// 큐에 데이터를 넣는 함수
	public boolean offer(E value) {
		if (isFull()) {
			resize(); // 큐가 꽉 차면 배열 리사이징 실행
		}

		rear++;
		arr[rear] = value;

		return true;
	}

	// 맨 앞의 요소를 제거하는 메소드 (값 반환)
	// Object 배열인 arr에서 가져오게 되는 요소가 Object 타입이라 제네릭 E 타입으로 형변환해줘야 하는데
	// 형변환 하는 과정에서 에디터에서 경고창이 뜸 -> 제네릭 자체가 확인되지 않은 모호한 타입이기 때문
	// 어차피 스택 클래스에서 제네릭 타입으로만 요소를 다루기 때문에 형 안정성이 확보되므로
	// ClassCastException이 뜨지 않으니 이 경고들을 무시하겠다는 의미의 어노테이션
	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public E poll() {
		if (isEmpty()) {
			return null;
		}

		E value = (E) arr[front];
		
		arr[front] = null;

		resize();

		return value;
	}

	// 큐의 맨 앞의 값만 확인하고 꺼내지는 않는 메소드
	@SuppressWarnings("unchecked")
	public E peek() {
		if (isEmpty()) {
			throw new Error("Empty Queue");
		}

		// 큐의 마지막 원소 값만 반환 (삭제 x)
		return (E) arr[front];
	}

	// 들어있는 요소 개수 반환하는 메소드
	public int size() {
		return front - rear;
	}

	// 큐 배열을 콘솔 화면에 출력하기 위한 메소드
	@Override
	public String toString() {
		return Arrays.toString(arr);
	}
}
