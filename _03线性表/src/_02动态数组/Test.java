package _02动态数组;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		arrayList.add(11);
		arrayList.add(22);
		arrayList.add(33);
		arrayList.add(11);
		System.out.println(arrayList.toString());
		
		_02IntegerArrayList arrayList2 = new _02IntegerArrayList();
		arrayList2.add(11);
		arrayList2.add(22);
		arrayList2.add(33);
		arrayList2.add(11);
		System.out.println(arrayList.toString());
	}
}
