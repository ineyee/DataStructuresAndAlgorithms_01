package _01什么是算法;

public class Main {
	public static void main(String[] args) {
		/*
		 * 一、什么是算法？
		 * 
		 * 算法其实很简单，它就是用于解决一个特定问题的一系列执行步骤，比如说下面的两个例子：
		 * 
		 * （1）“求a与b的和”就是一个特定问题，而“a + b”这行代码就是解决这个特定问题的执行步骤， 因此“a + b”这行代码就是一个算法
		 * （2）“求1+2+3+...+n的和”也是一个特定问题，而sum方法里的几行代码就是解决这个特定问题的一系列执行步骤，
		 * 因此sum方法里的几行代码也是一个算法
		 * 
		 * 所以我们不能说一提到算法就觉得只有非常复杂的代码才配叫作算法，算法其实就是那段代码能解决问题就可以了， 少到一行代码，多到上万行代码都可以是算法。
		 */

		/*
		 * 二、使用不同的算法来解决同一个问题，效率可能会相差非常大。我们举个例子来验证一下这个说法：求第N个斐波那契数。
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
