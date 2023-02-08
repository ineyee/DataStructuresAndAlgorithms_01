package _02动态数组;

/**
 * 一个只能存储整型的动态数组
 * 
 * Java里一共有4个级别的访问权限，从高到低依次是：
 * 1、public：在项目里的任何地方————即在项目里的所有包中都能访问
 * 2、protected：在自己的包中能访问 + 子类中能访问（因为子类可能跟父类不在同一个包里）
 * 3、无修饰符（默认为包私有权限package-private）：仅在自己的包中能访问
 * 4、private：仅在自己的类中能访问
 * 那我们编写了这么一个整型动态数组，当然是希望在项目里的所有包中都能访问，所以这个类应该定义成public的
 * 同时这个类里面的方法我们也是希望在项目里的所有包中都能访问，所以也应该定义成public的
 * 
 * @author yiyi
 *
 */
public class _02IntegerArrayList {
	/**
	 * 添加元素到最后面
	 * @param element
	 */
	public void add(Integer element) {
		
	};
	
	/**
	 * 添加元素到指定的位置
	 * @param index
	 * @param element
	 */
	public void add(int index, Integer element) {
		
	};
	
	/**
	 * 删除第一个匹配到的元素
	 * @param element
	 */
	public void remove(Integer element) {
		
	};
	
	/**
	 * 删除指定位置的元素
	 * @param index
	 * @return 删除掉的元素
	 */
	public Integer remove(int index) {
		return 0;
	};
	
	/**
	 * 修改指定位置的元素
	 * @param index
	 * @param element
	 * @return 修改前的元素
	 */
	public Integer set(int index, Integer element) {
		return 0;
	};
	
	/**
	 * 获取指定位置的元素
	 * @param index
	 * @return
	 */
	public Integer get(int index) {
		return 0;
	};
	
	/**
	 * 获取数组的长度
	 * @return
	 */
	public int size() {
		return 0;
	};
	
	/**
	 * 获取一个元素的index
	 * @param element
	 * @return
	 */
	public int indexOf(Integer element) {
		return 0;
	};
	
	/**
	 * 清空数组
	 */
	public void clear() {
		
	};
	 
	/**
	 * 数组是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return false;
	};
	
	/**
	 * 数组是否包含某个元素
	 * @param element
	 * @return
	 */
	public boolean contains(Integer element) {
		return false;
	};
}

/*
 * 我们先从简单的开始，先写一个只能存储整型的动态数组（注意我们在实际开发中使用的动态数组都是只能存储对象类型，所以这里的
 * 整型是Integer类型，而不是int类型，Integer类型就是int类型对应的对象类型，也叫包装类，Integer = int + null），然
 * 后再把这个动态数组扩展为能存储任意类型的数据。
 */

/*
 * 第一步：首先从对外的角度思考这个类应该提供一些什么接口
 * 
 * 增：
 * 1、void add(Integer element); // 添加元素到最后面
 * 2、void add(int index, Integer element); // 添加元素到指定的位置
 * 
 * 删：
 * 1、void remove(Integer element); // 删除第一个匹配到的元素
 * 2、Integer remove(int index); // 删除指定位置的元素
 * 
 * 改：
 * Integer set(int index, Integer element); // 修改指定位置的元素
 * 
 * 查：
 * Integer get(int index); // 获取指定位置的元素
 * 
 * 其它一些常用的：
 * 1、int size(); // 获取数组的长度
 * 2、int indexOf(Integer element); // 获取一个元素的index
 * 3、void clear(); // 清空数组
 * 4、boolean isEmpty(); // 数组是否为空
 * 5、boolean contains(Integer element); // 数组是否包含某个元素
 */