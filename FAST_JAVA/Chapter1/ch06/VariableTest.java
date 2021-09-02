package ch06;

public class VariableTest {
	public static void main(String[] args) {
		byte bnum = 127;
		System.out.println(bnum);
		
//		int num = 12345678910; // error - out of range int
//		long lNum = 12345678; // 작은 수가 큰 공간에 들어가는 건 괜찮음
		long lNum = 12345678910L;
		System.out.println(lNum);
	}

}
