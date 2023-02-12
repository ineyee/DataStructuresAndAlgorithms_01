package _02动态数组;

public class Test {
	public static void main(String[] args) {
		_02IntegerArrayList arrayList2 = new _02IntegerArrayList();
		arrayList2.add(11);
		arrayList2.add(22);
		arrayList2.add(33);
		arrayList2.add(44);
		arrayList2.add(55);
		arrayList2.add(66);
		System.out.println(arrayList2);
		
		arrayList2.remove(0);
		arrayList2.remove(4);
		System.out.println(arrayList2);
		
		arrayList2.set(0, 1000);
		arrayList2.set(3, 2000);
		System.out.println(arrayList2);
		
		System.out.println(arrayList2.get(1));
		
		arrayList2.add(111);
		arrayList2.add(222);
		arrayList2.add(333);
		arrayList2.add(444);
		arrayList2.add(555);
		arrayList2.add(666);
		arrayList2.add(777);
		arrayList2.add(888);
		arrayList2.add(999);
		System.out.println(arrayList2);
	}
}
