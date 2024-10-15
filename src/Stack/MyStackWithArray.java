package Stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStackWithArray<E> {
	private static final int DEFAULT_CAPACITY = 6; // 최소 용량 크기
	private Object[] arr; // 요소를 담을 내부 배열
	private int top; // 스택의 가장 마지막 요소를 가리키는 포인터(인덱스), 초기값은 -1
	
	// 생성자
	public MyStackWithArray() {
		this.arr = new Object[DEFAULT_CAPACITY]; // 6 용량으로 내부 배열 생성
		this.top = -1;
	}
	
	// 스택의 공간을 검사하는 메소드
	public boolean isFull() {
		// 마지막 요소 위치인 top이 (배열 길이 -1)과 같은 경우 끝까지 차있는 것과 같음
		return top == arr.length - 1;
	}
	
	// 스택의 공간을 검사하는 메소드
	public boolean isEmpty() {
		// 마지막 요소 위치인 top이 -1을 가리키는 경우, 배열이 비어있는 것과 같음
		return top == -1;
	}
	
	// 클래스 내부에서만 실행되는 메소드이니 private
	private void resize() {
		int arr_capacity = arr.length - 1; // 현재 배열의 용량에 -1 한 값을 얻음 (배열 인덱스는 0부터 시작하니까)
		
		// 용량이 꽉찬 경우
		if (top == arr_capacity) {
			// 넉넉하게 공간을 유지하기 위해 현재 용량의 두 배로 설정
			int new_capacity = arr.length * 2;
			
			// 복사할 배열을 new_capacity 용량 만큼 설정하고 arr 원소들을 전체 복사
			arr = Arrays.copyOf(arr, new_capacity);
			return ;
		}
		
		// 용량에 비해 데이터 양이 적은 경우 (최적화)
		if (top < (arr_capacity / 2)) {
			// 기존 용량의 반을 설정
			int half_capacity = arr.length / 2;
			
			// half_capacity와 default 용량 중 큰 걸 복사
			arr = Arrays.copyOf(arr, Math.max(half_capacity, DEFAULT_CAPACITY));
			return;
		}
	}
	
	// 데이터를 추가하는 메소드
	public E push(E value) {
		// 1. 배열이 꽉 차있는지 검사
		if (isFull()) resize(); // 꽉 찼으면 배열 리사이징 실행
		
		// 2. 원소를 추가할 예정이니 마지막 요소 위치인 top을 1 증가시킴
		top++;
		
		// 3. 그리고 해당 배열 위치에 요소를 추가
		arr[top] = value;
		
		// 4. 넣은 요소의 값을 반환
		return value;
	}
	
	// 데이터를 제거하는 메소드
	// Object 배열인 arr에서 가져오게 되는 요소가 Object 타입이라 제네릭 E 타입으로 형변환해줘야 하는데
	// 형변환 하는 과정에서 에디터에서 경고창이 뜸 -> 제네릭 자체가 확인되지 않은 모호한 타입이기 때문
	// 어차피 스택 클래스에서 제네릭 타입으로만 요소를 다루기 때문에 형 안정성이 확보되므로
	// ClassCastException이 뜨지 않으니 이 경고들을 무시하겠다는 의미의 어노테이션 @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public E pop() {
		// 1. 배열이 비어있으면 예외 발생
		if (isEmpty()) throw new EmptyStackException();
		
		// 2. 먼저 arr[top] 원소를 백업
		E value = (E) arr[top];
		
		// 3. 해당 위치의 요소를 삭제
		arr[top] = null;
		
		// 4. top을 1 감소 시킴
		top--;
		
		// 5. 혹시 들어있는 요소에 비해 빈 공간이 많으면 최적화를 위해 리사이징 해줌
		resize();
		
		// 6. 백업한 요소를 반환
		return value;
	}
	
	// 스택의 마지막 원소의 값만 확인하고 꺼내지는 않는 메소드
	@SuppressWarnings("unchecked")
	public E peek() {
		// 1. 배열이 비어있으면 예외 발생
		if (isEmpty()) throw new EmptyStackException();
		
		// 2. 스택의 마지막 원소 값만 반환 (삭제 x)
		return (E) arr[top];
	}
	
	// 스택의 search 동작은 리스트의 indexOf와 비슷하지만 약간 다름
	// search의 반환 숫자는 스택의 상단의 위치로부터 얼만큼 떨어져 있는 지에 대한 상대적 위치 값
	// 즉, 맨 마지막 데이터가 1이라는 의미는 첫 번째로 꺼내질 요소라는 뜻
	public int search(E value) {
		// 스택의 top으로부터 아래로 순회하여 찾고자 하는 값의 위치를 구함
		for (int i = top; i >= 0; i--) {
			if (arr[i].equals(value)) {
				return top - i + 1;
			}
		}
		
		// 만일 찾고자 하는 값이 없다면 -1을 반환
		return -1;
	}
	
	// 스택 배열을 콘솔 화면에 출력하기 위한 메소드
	@Override
	public String toString() {
		return Arrays.toString(arr);
	}
}
