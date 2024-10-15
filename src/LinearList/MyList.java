package LinearList;

public interface MyList<T> {
	boolean add(T value); // 요소를 추가
	void add(int index, T value); // 요소를 특정 위치에 추가
	
	boolean remove(Object value); // 요소를 삭제
	T remove(int index); // 특정 위치에 있는 요소를 삭제
	
	T get(int index); // 요소 가져오기
	void set(int index, T value); // 특정 위치에 있는 요소를 새 요소로 대체
	
	boolean contains(Object value); // 특정 요소가 리스트에 있는지 여부를 확인
	int indexOf(Object value); // 특정 요소가 몇 번째 위치에 있는지를 반환 (순차 검색)
	int lastIndexOf(Object value); // 특정 요소가 몇 번째 위치에 있는지를 반환 (역순 검색)
	
	int size(); // 요소의 개수를 반환
	boolean isEmpty(); // 요소가 비어있는지
	
	public void clear(); // 요소를 모두 삭제
}
