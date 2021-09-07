package ch09;

public class LocalVariableType {

	public static void main(String[] args) {
		var i = 10;
		var j = 10.0;
		var str = "test";
		
		System.out.println(i);
		System.out.println(j);
		System.out.println(str);
		
		var str2 = str;
		System.out.println(str2);
		
		str = "hello";
//		str = 3; // script 언어에서는 다른 타입 대입 가능
	}
}
