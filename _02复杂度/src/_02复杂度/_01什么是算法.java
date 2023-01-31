package _02复杂度;

public class _01什么是算法 {
	public static void main(String[] args) {
		/*
		 * 一、什么是算法？ 算法其实很简单，它就是用于解决一个特定问题的一系列执行步骤，不要把它看得太高深了。
		 */
		System.out.println(add(100, 299));
		System.out.println(sum(3));
	}

	/**
	 * 求a与b的和
	 * 
	 * @param a
	 * @param b
	 * @return a与b的和
	 */
	public static int add(int a, int b) {
		return a + b;
	}

	/**
	 * 求1+2+3+...+n的和
	 * 
	 * @param n
	 * @return 1+2+3+...+n的和
	 */
	public static int sum(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += i;
		}
		return sum;
	}
}
