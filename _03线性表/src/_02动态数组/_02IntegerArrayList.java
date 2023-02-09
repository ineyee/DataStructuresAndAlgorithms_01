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
 * 然后从封装的角度考虑，属性都应该搞成私有的，然后根据需要向外界暴露该私有属性的setter和getter方法
 * 
 * @author yiyi
 *
 */
public class _02IntegerArrayList {
	/**
	 * 1、Java里总是先有类才有其它东西，也就说所有的东西都必须定义在类内部
	 * 比方说这个类内部使用的私有常量，在其它语言里我们也许可以定义在这个文件的
	 * 头部，不一定非要定义在类内部，但在Java里就不行
	 * 
	 * 2、按照Java官方的规范，常量是写在类的最上面的，然后是属性，然后是构造方法，然后是方法
	 * 
	 * 3、因为是常量嘛，所以内存中只有一份就可以了，所以在Java里定义常量总是static final连用
	 * 
	 * 4、按照Java官方的规范，常量要全部大写，单词间用下划线隔开
	 * 
	 * 5、之所以要定义成常量，是为了避免硬编码
	 */
	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;
	
	/**
	 * 数组的长度
	 */
	private int size;
	
	/**
	 * 所有的元素
	 * 
	 * 动态数组用基本数组实现
	 */
	private Integer[] elements;
	
	/**
	 * 我们提供一个带参的构造方法，以便外界在创建动态数组时想主动设置初始容量，这个方法
	 * 就是这个类的万能构造方法
	 * @param capacity
	 */
	public _02IntegerArrayList(int capacity) {
		if (capacity < DEFAULT_CAPACITY) {
			capacity = DEFAULT_CAPACITY;
		}
		elements = new Integer[DEFAULT_CAPACITY];
	}
	
	/**
	 * 自定义了带参的构造方法后，系统就不会帮我们自动生成无参的构造方法了，所以我们这里
	 * 再提供一个无参构造方法供外界使用，外界也许不关心什么容量不容量问题呢，就像我们平常
	 * 使用数组的时候那样，只不过默认容量给个10，调用一下全能构造方法即可
	 */
	public _02IntegerArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
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
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
		}
		
		Integer old = elements[index];
		elements[index] = element;
		return old;
	};
	
	/**
	 * 获取指定位置的元素
	 * @param index
	 * @return
	 */
	public Integer get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
		}
		return elements[index];
	};
	
	/**
	 * 获取数组的长度
	 * @return
	 */
	public int size() {
		return size;
	};
	
	/**
	 * 获取一个元素的index
	 * @param element
	 * @return
	 */
	public int indexOf(Integer element) {
		for (int i = 0; i < size; i++) {
			if (elements[i] == element) {
				return i;
			}
		}
		return ELEMENT_NOT_FOUND;
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
		return size == 0;
	};
	
	/**
	 * 数组是否包含某个元素
	 * @param element
	 * @return
	 */
	public boolean contains(Integer element) {
		return indexOf(element) != ELEMENT_NOT_FOUND;
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