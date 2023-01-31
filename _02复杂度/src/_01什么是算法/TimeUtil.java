package _01什么是算法;

public class TimeUtil {
	@FunctionalInterface
	public interface Block {
		void putCodeIn();
	}
	
	public static void calculate(String msg, Block block) {
		long begin = System.currentTimeMillis();
		block.putCodeIn();
		long end = System.currentTimeMillis();
		System.out.println(msg + "耗时：" + (end - begin) + "毫秒");		
	}
}
