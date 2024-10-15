package LinearList;

import java.util.Arrays;

public class MyArrayList<E> implements MyList<E> {
	private static final int DEFAULT_CAPACITY = 5; // 생성자로 배열이 생성될 때 기본 용량
	private static final Object[] EMPTY_ELEMENTDATA = {}; // 빈 배열
	
	private int size; // elementData 배열의 총 개수(크기)를 나타내는 변수
	Object[] elementData; // 자료를 담을 배열
	
	// 생성자 (초기 공간 할당 x)
	public MyArrayList() {
		this.elementData = new Object[DEFAULT_CAPACITY]; // 디폴트 용량으로 초기화
		this.size = 0;
	}
	
	// 생성자 (초기 공간 할당 o)
	// size는 배열에 담긴 모든 요소 개수(크기)를 뜻하는 개념이고, capacity는 배열의 전체 공간 용량을 뜻함
	public MyArrayList(int capacity) {
		// 파라미터의 값이 양수일 경우 그대로 용량으로 배열을 생성
		if (capacity > 0) {
			this.elementData = new Object[capacity];
		}
		// 파라미터의 값이 0일 경우 인자를 주지 않고 인스턴스화 한 것과 같으니 디폴트 용량으로 초기화
		else if (capacity == 0) {
			this.elementData = new Object[DEFAULT_CAPACITY];
		}
		// 파라미터의 값을 음수로 설정할 경우 예외를 발생시키도록 안전하게 설계
		else if (capacity < 0) {
			throw new RuntimeException(new IllegalAccessException("리스트 용량을 잘못 설정 하였습니다.")); // Checked 예외를 Unchecked 예외로 변환
		}
		
		this.size = 0;
	}
	
	// 리스트는 가변 배열 (배열과의 가장 큰 차이점)
	// 클래스 내부에서만 실행되는 메소드이니 private
	// 리스트에 요소 추가, 삭제 등의 동작이 일어날 때 기본적으로 호출
	// 배열의 크기(size)와 배열의 용량(capacity)을 비교하여 메모리 최적화에 도움
	private void resize() {
		int element_capacity = elementData.length; // 현재 배열의 크기를 얻음
		
		// 용량이 꽉 찬 경우
		if (element_capacity == size) {
			int new_capacity = element_capacity * 2; // 넉넉하게 공간을 유지하기 위해 현재 용량의 두 배로 설정
			
			elementData = Arrays.copyOf(elementData, new_capacity); // 복사할 배열을 new_capacity 용량만큼 설정하고 elementData 원소들을 전체 복사해서 넣고 반환 (빈공간은 null)
			return;
		}
		
		// 용량에 비해 데이터 양이 적은 경우
		// 크기 계산 기준은 현재 배열 원소 개수(size)가 현재 배열 용량(capacity)의 절반 보다 작을 경우
		if ((element_capacity / 2) > size) {
			int half_capacity = element_capacity / 2;
			
			elementData = Arrays.copyOf(elementData, Math.max(half_capacity, DEFAULT_CAPACITY));
			return;
		}
		
		// 들어있는 데이터가 하나도 없을 경우 (빈 배열일 경우)
		if (Arrays.equals(elementData, EMPTY_ELEMENTDATA)) {
			elementData = new Object[DEFAULT_CAPACITY]; // 기본 용량으로 초기화
			return;
		}
	}
	
	// 가장 끝부분에 추가
	@Override
	public boolean add(Object value) {
		resize(); // 현재 배열이 꽉 차있는 상태이면 리사이징
		
		elementData[size] = value; // size가 원소의 개수이고, 배열의 인덱스는 0부터 시작하니 결국 추가할 수 있는 마지막 위치를 가리키게 된다.
		size++;
		return true;
	}
	
	// 특정 위치에 추가
	@Override
	public void add(int index, Object value) {
		// 인덱스가 음수이거나, 배열 크기(size)를 벗어난 경우 예외 발생 (리스트는 데이터가 연속되어야 함)
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		// 인덱스가 마지막 위치일 경우
		if (index == size) {
			add(value); // 그냥 추가
		}
		// 인덱스가 중간 위치를 가리킬 경우
		else {
			resize(); // 현재 배열이 꽉 차있는 상태이면 리사이징
			
			// 루프 변수에 배열 크기를 넣고, index 위치까지 순회해서 요소들 한 칸씩 뒤로 밀어 빈 공간 만들기
			for (int i = size; i > index; i--) {
				elementData[i] = elementData[i - 1];
			}
			
			elementData[index] = value; // index 위치에 요소 할당
			size++;
		}
	}
	
	// 순차대로 검색해서 위치 반환
	// 만일 찾고자 하는 값이 배열에 중복으로 들어있으면 가장 먼저 검색된 요소의 위치 반환
	// 만일 찾고하 하는 값이 없을 경우 -1 반환
	@Override
	public int indexOf(Object value) {
		// 매개변수가 null일 경우 (null 비교는 동등연산자로 행하기 때문에 비교 로직을 분리)
		if (value == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null) {
					return i; // 인덱스 반환
				}
			}
		}
		
		// 매개변수가 실질적인 값일 경우
		else {
			for (int i = 0; i < size; i++) {
				if (elementData[i].equals(value)) {
					return i; // 인덱스 반환
				}
			}
		}
		
		return -1; // 찾은 값이 없을 경우
	}
	
	// 역순으로 검색해서 위치 반환
	// 만일 찾고자 하는 값이 배열에 중복으로 들어있으면 가장 먼저 검색된 요소의 위치 반환
	// 만일 찾고하 하는 값이 없을 경우 -1 반환
	@Override
	public int lastIndexOf(Object value) {
		if (value == null) {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i] == null) {
					return i; // 인덱스 반환
				}
			}
		} else {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i].equals(value)) {
					return i; // 인덱스 반환
				}
			}
		}
		
		return -1; // 찾은 값이 없을 경우
	}
	
	// 특정 인덱스의 요소를 삭제
	@Override
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		// 1. 인덱스가 음수이거나, size 보다 같거나 클 경우 (size와 같다는 말은 요소 위치가 빈 공간이라는 말)
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		// 2. 반환할 값 백업
		E element = (E) elementData[index];
		
		// 3. 요소 제거 (명시적으로 요소를 null로 처리해주어야 GC가 수거해감)
		elementData[index] = null;
		
		// 4. 배열 요소 이동 (삭제한 요소의 뒤에 있는 모든 요소들을 한 칸씩 당겨옴)
		for (int i = index; i < size - 1; i++) {
			elementData[i] = elementData[i + 1];
			elementData[i] = null;
		}
		
		// 5. 요소를 제거했으니 size도 감소
		size--;
		
		// 6. 현재 배열이 capacity가 너무 남아도는 상태이면 리사이징
		resize();
		
		// 7. 백업한 삭제된 요소를 반환
		return element;
	}
	
	// 인덱스로 위치를 찾아서 그 요소를 삭제하는 것이 아닌, 요소 자체를 뒤져서 찾아 삭제하는 메소드
	@Override
	public boolean remove(Object value) {
		// 1. 먼저 해당 요소가 몇 번째 위치에 존재하는지 인덱스를 얻어온다.
		int idx = indexOf(value);
		
		// 2. 만약 값이 -1 이면 삭제하고자 하는 값이 없는 것이니 그대로 메소드를 종료한다.
		if (idx == -1) return false;
		
		// 3. 인덱스를 찾았으면 그대로 remove() 메소드를 넘겨 삭제한다. (remove() 에서 요소 삭제 및 size 감소 리사이징 처리)
		remove(idx);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return (E) elementData[index]; // 요소 값 반환 (형변환 필요)
	}
	
	@Override
	public void set(int index, Object value) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		elementData[index] = value; // 요소 교체
	}
	
	// 들어있는 요소의 개수를 반환하는 메소드
	@Override
	public int size() {
		return size;
	}
	
	// 리스트가 비어있는지 아닌지 확인하는 메소드
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	// 모든 요소를 지우는 메소드
	@Override
	public void clear() {
		elementData = new Object[DEFAULT_CAPACITY]; // 기본 용량으로 초기화
		size = 0; // 모든 요소를 지웠으니 size도 초기화
	}
	
	// 사용자가 찾고자 하는 요소가 존재하는지 여부를 반환하는 메소드
	@Override
	public boolean contains(Object value) {
		// 인덱스 값이 0보다 크면 곧 요소의 위치로서 요소가 존재한다는 것이고, 0보다 작으면 -1로서 요소가 존재하지 않는다는 것이다.
		return indexOf(value) >= 0 ? true : false;
	}
	
	// ArrayList 컬렉션 객체를 그대로 print 했을 때 별다른 작업 없이 배열로 예쁘게 출력되는 이유
	// Object 클래스의 toString() 메소드를 재정의해서 예쁘게 출력 되도록 함
	@Override
	public String toString() {
		return Arrays.toString(elementData);
	}
}
