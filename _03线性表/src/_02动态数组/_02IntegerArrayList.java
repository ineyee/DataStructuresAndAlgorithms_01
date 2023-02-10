package _02动态数组;

/**
 * 整型动态数组，特点：
 * 1、可以存储Integer类型 = int类型 + null
 * 2、元素有序
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
	 * 2、按照Java官方的规范，常量是写在类的最上面的，然后是属性，然后是构造方法，然后是公开方法、私有方法
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
		/*
		 * 暂时不考虑数组扩容问题
		 * 
		 * 这两行代码虽然能实现效果，但从代码复用的角度考虑
		 * 方法重载的若干方法之间，参数少的那个方法可以通过调用参数多的那个方法来实现
		 */
//		elements[size] = element;
//		size++;
		
		/*
		 * 所以这里我们修改为调用void add(int index, Integer element)方法来实现
		 */
		add(size, element);
	};
	
	/**
	 * 添加元素到指定的位置
	 * @param index
	 * @param element
	 */
	public void add(int index, Integer element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
		}
		
		/*
		 * 暂时不考虑扩容问题
		 * 
		 * 添加元素到指定的位置其实就是把index到size - 1这几个元素依次往后移动一位
		 * 然后再把element放到index的位置
		 * 
		 * 但是这个操作得从尾部往前面倒着来移，不然后面的元素可能被覆盖掉
		 */
		for (int i = size - 1; i <= index; i--) {
			elements[i - 1] = elements[i];
		}
		elements[index] = element;
		size++;
	};
	
	/**
	 * 删除一个元素，第一个匹配到
	 * @param element
	 */
	public void remove(Integer element) {
		/*
		 * 删除一个元素其实就是把这个元素的index + 1到size - 1这几个元素依次往前移动一位，
		 * 让index + 1这个元素覆盖掉这个元素就可以了
		 * 
		 * 至于移动后最后一个元素肯定还保留的是原来的值，那我们需不需要把它置为null呢？可以但没必要，
		 * 因为所有的元素前移后，我们肯定会让size--，那外界其实就访问不到那个保留原来值的最后一个元素了，
		 * 所以那块内存保留原来的数据也不会出现数据错乱，如果我们把这块内存置为null，反而增加了对内存的操作，
		 * 性能上反而会下降，还不如放着不管它
		 * 
		 * 有了这个思路，我们发现得首先找到这个元素所在的index，然后再做操作，所以这个好像转化为了对
		 * int indexOf(Integer element)和Integer remove(int index)这两个方法的调用了
		 * 因此我们先去实现Integer remove(int index)这个方法
		 */
		int index = indexOf(element);
		if (index != ELEMENT_NOT_FOUND) {
			remove(index);
		}
	};
	
	/**
	 * 删除指定位置的元素
	 * @param index
	 * @return 删除掉的元素
	 */
	public Integer remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
		}
		
		/*
		 * 思路见void remove(Integer element)这个方法里的描述
		 */
		Integer old = elements[index];
		for (int i = index; i < size - 1; i++) {
			elements[index] = elements[index + 1];
		}
		size--;
		return old;
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
		
		/*
		 * 基本数组可以通过index直接覆盖掉指定位置的元素，
		 * 所以直接根据index修改就可以了
		 */
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
		
		/*
		 * 基本数组可以通过index直接获取指定位置的元素，
		 * 所以直接根据index获取就可以了
		 */
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
	 * 获取一个元素的index，第一个匹配到
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
		/*
		 * 直接把size赋值成0就可以了，虽然浪费了点内存空间，但是省了频繁操作内存的时间
		 */
		size = 0;
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