package ch07;

public class DoubleTest2 {

	public static void main(String[] args) {
		double dnum = 1;
		// �ε� �Ҽ��� ��Ŀ����� �����ΰ� 0�� ǥ���� �� ���� ������ �ణ�� ���� �߻�
		for(int i=0;i<10000;i++) {
			dnum = dnum + 0.1;
		}
		System.out.println(dnum);
	}
}
