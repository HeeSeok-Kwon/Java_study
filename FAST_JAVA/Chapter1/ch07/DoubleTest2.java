package ch07;

public class DoubleTest2 {

	public static void main(String[] args) {
		double dnum = 1;
		// 부동 소수점 방식에서는 지수부가 0을 표현할 수 없기 때문에 약간의 오차 발생
		for(int i=0;i<10000;i++) {
			dnum = dnum + 0.1;
		}
		System.out.println(dnum);
	}
}
