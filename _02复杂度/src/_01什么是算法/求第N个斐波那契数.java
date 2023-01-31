package _01什么是算法;

import _01什么是算法.TimeUtil.Block;

public class 求第N个斐波那契数 {
	public static void main(String[] args) {
		/*
		 * 斐波那契数列：我们把F(0) = 0，F(1) = 1，F(N) = F(N - 1) + F(N - 2) (N >= 2, N属于正整数)这个
		 * 表达式所定义的数列称为斐波那契数列。
		 * 
		 * 说白了这个数列就是：0、1、1、2、3、5、8、13、21、34......，即第一个数固定为0，第二个数固定为1，从第 三个数开始都是前面两个数的和。
		 * 
		 * 求第N个斐波那契数有很多种算法，这里我们只演示两种：递归法和平推法。
		 */

		/*
		 * 递归法
		 * 
		 * 可见递归法的确能解决我们的需求，但是我们才求第48个斐波那契数就要很久才能计算出来， 这说明这个算法是存在很大性能问题的。
		 */
//		System.out.println(fib1(0)); // 0（秒算）
//		System.out.println(fib1(1)); // 1（秒算）
//		System.out.println(fib1(2)); // 1（秒算）
//		System.out.println(fib1(3)); // 2（秒算）
//		System.out.println(fib1(4)); // 3（秒算）
//		System.out.println(fib1(5)); // 5（秒算）
//		System.out.println(fib1(40)); // 102334155（要花个秒一两秒）
//		System.out.println(fib1(48)); // 4807526976（要很久才能计算出来）
		
		/*
		 * 平推法
		 * 
		 * 可见平推法也能解决我们的需求，而且它的性能要比递归法好得多。
		 */
//		System.out.println(fib2(0)); // 0（秒算）
//		System.out.println(fib2(1)); // 1（秒算）
//		System.out.println(fib2(2)); // 1（秒算）
//		System.out.println(fib2(3)); // 2（秒算）
//		System.out.println(fib2(4)); // 3（秒算）
//		System.out.println(fib2(5)); // 5（秒算）
//		System.out.println(fib2(40)); // 102334155（秒算）
//		System.out.println(fib2(48)); // 4807526976（秒算）
		
		/*
		 * 那这两种算法的性能差距究竟有多大呢？
		 * 
		 * 这里我们先简单通过统计两种算法的耗时来判定下它们之间的性能差距究竟有多大。
		 */
		TimeUtil.calculate("fib1", new Block() {
			@Override
			public void putCodeIn() {
				fib1(48); // fib1耗时：25427毫秒
			}
		});
		
		TimeUtil.calculate("fib2", new Block() {
			@Override
			public void putCodeIn() {
				fib2(48); // fib2耗时：0毫秒
			}
		});
	}

	/*
	 * 递归法
	 * 
	 * 其实由斐波那契数列的表达式我们就可以得出递归法的算法（注意一旦用到递归，就要写明退出递归的条件，否则程序将死循环）。
	 */
	public static long fib1(int n) {
		if (n <= 1) {
			return n;
		}

		return fib1(n - 1) + fib1(n - 2);
	}

	/*
	 * 平推法
	 * 
	 * 平推法的思路其实就是最笨的办法，一个一个斐波那契数地求，即不断地用前面两个数相加得到后面一个数，
	 * 然后再用最新的两个数相加得到后面一个数，最终得到第N个斐波那契数，即先求
	 * 
	 * 第0个斐波那契数是：0 
	 * 第1个斐波那契数是：1 
	 * 第2个斐波那契数是：1（ = 第1个斐波那契数1 + 第0个斐波那契数0）
	 * 第3个斐波那契数是：2（ = 第2个斐波那契数1 + 第1个斐波那契数0）
	 * 第4个斐波那契数是：3（ = 第3个斐波那契数2 + 第2个斐波那契数1） 
	 * ...
	 * 第N个斐波那契数是：xx（ = 第N-1个斐波那契数xx + 第N-2个斐波那契数xx）
	 */
	public static long fib2(int n) {
		// 第0个和第1个斐波那契数
		if (n <= 1) {
			return n;
		}

		// 前一个数（第N个斐波那契数很可能超出int的范围，所以用long）
		long previous = 0;
		// 后一个数
		long subsequent = 1;

		/*
		 * 当我们求第2个斐波那契数时，需要第1个和第0个斐波那契数相加，即做一次加法
		 * 当我们求第3个斐波那契数时，需要第1个和第0个斐波那契数相加得到第2个斐波那契数，即做一次加法，然后
		 * 还需要第2个和第1个斐波那契数相加才能得到第3个斐波那契数，即又做了一次加法
		 * 
		 * 所以求第N个斐波那契数时，一共需要做N-1次前一个数 + 后一个数这样的加法
		 */
		for (int i = 0; i < n - 1; i++) {
			// 求出最新的一个斐波那契数
			long sum = previous + subsequent;
			
			// 然后把刚才的后一个数作为下一次相加的前一个数，把最新的一个斐波那契数作为后一个数，去求下一个斐波那契数
			previous = subsequent;
			subsequent = sum;
		}
		
		// 当这样加完后，第N个斐波那契数就落在了subsequent上
		return subsequent;
	}
}
