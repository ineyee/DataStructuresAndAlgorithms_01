package _02动态数组;

/**
 * 动态数组
 * 
 * @author yiyi
 *
 * @param <E>
 */
public class ArrayList<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;
	
	/**
	 * 数组的长度
	 */
	private int size;
	
	/**
	 * 所有的元素
	 */
	private E[] elements;
	
	/**
	 * 万能构造方法
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		if (capacity < DEFAULT_CAPACITY) {
			capacity = DEFAULT_CAPACITY;
		}
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}
	
	/**
	 * 无参构造方法了
	 */
	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * 添加元素到最后面
	 * @param element
	 */
	public void add(E element) {
		add(size, element);
	};
	
	/**
	 * 添加元素到指定的位置
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		ensureCapacity(size + 1);
		
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		size++;
	};
	
	/**
	 * 删除一个元素，第一个匹配到
	 * @param element
	 */
	public void remove(E element) {
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
	public E remove(int index) {
		rangeCheck(index);
		
		E old = elements[index];
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
		elements[size] = null;
		return old;
	};
	
	/**
	 * 修改指定位置的元素
	 * @param index
	 * @param element
	 * @return 修改前的元素
	 */
	public E set(int index, E element) {
		rangeCheck(index);
		
		E old = elements[index];
		elements[index] = element;
		return old;
	};
	
	/**
	 * 获取指定位置的元素
	 * @param index
	 * @return
	 */
	public E get(int index) {
		rangeCheck(index);
		
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
	public int indexOf(E element) {
		if (element == null) { // 外界想找数组里null的下标
			for (int i = 0; i < size; i++) {				
				if (elements[i] == null) {
					return i;
				}
			}
		} else { // 正常查找
			for (int i = 0; i < size; i++) { 				
				if (element.equals(elements[i])) {
					return i;
				}
			}
		}
		
		return ELEMENT_NOT_FOUND;
	};
	
	/**
	 * 清空数组
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
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
	public boolean contains(E element) {
		return indexOf(element) != ELEMENT_NOT_FOUND;
	};
	
	/**
	 * 因为上面有很多地方都编写了检查数据是否越界的代码，所以这里抽取一下
	 */
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			outOfBoundsException(index, size);
		}
	}
	
	/**
	 * add的地方稍有不同，因为我们允许往size也就是数组的尾部添加元素，所以单独写一个
	 */
	private void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			outOfBoundsException(index, size);
		}
	}
	
	/**
	 * 又因为两个rangeCheck都写到了这个重复的代码，所以抽一下
	 */
	private void outOfBoundsException(int index, int size) {
		throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
	}
	
	/**
	 * 确保动态数组最少有capacity的容量
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) { // 如果容量够用，则什么都不做
			return;
		}
		
		// 如果容量不够用，则把容量扩容为原来的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		// 开辟大基本数组
		E[] newElements = (E[]) new Object[newCapacity];
		// 一个一个移动元素
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		// elements指向大基本数组，保大基本数据的命，同时消耗小基本数组
		elements = newElements;
		
		System.out.println("动态数组库容了，扩容前容量：" + oldCapacity + "，扩容后容量：" + newCapacity);
	}
	
	@Override
	public String toString() {
		/*
		 * Java里打印一个对象其实就是在调用这个对象的toString方法，就像OC
		 * 里的description方法一样，我们可以重写toString方法来自定义怎么
		 * 打印这个对象
		 */
		StringBuilder sb = new StringBuilder(); 
		sb.append("size = " + size + ", ");
		sb.append("elements = [\n");
		for (int i = 0; i < size; i++) {
			E temp = elements[i];
			sb.append("  ");
			sb.append(temp);
			sb.append(",\n");
		}
		sb.append("]");
		
		return sb.toString();
	}
}