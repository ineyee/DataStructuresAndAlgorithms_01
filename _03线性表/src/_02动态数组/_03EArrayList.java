package _02动态数组;

/**
 * 前面的整型动态数组已经实现得差不多了，现在我们把那个整型动态数组扩展为泛型类型动态数组——即任意类型动态数组，
 * 要把这个类搞成一个泛型类，泛型的名字我们不取T而取为E，代表动态数组内部element的类型，先用E把Integer替了再说。
 * 替换了之后，需要修改的地方如下：
 * 
 * 1、唯一报错的地方就是初始化基本数组的时候E[] newElements = new E[newCapacity];，因为E不是个具体的类型，
 * 所以语法不支持直接这么写，那我们就绕一下，因为Java里所有的类都继承自Object，既然我们的动态数组里面就是放引用
 * 类型，那创建一个Object数组不就完事了嘛，然后再强转成泛型的那个类型：E[] newElements = (E[]) new Object[newCapacity];
 * 
 * 2、关于数组里能不能存储null的设计：Java官方的动态数组里设计能存储null，OC官方的数组里设计不能存储nil或者null，
 * Java里null调用方法会崩溃，OC里给nil或者null发送消息是不会崩溃的，我们此处跟Java官方保持一致，动态数组里可以存储null
 * 
 * 3、改成泛型后，关于内存布局及内存释放的细节
 * 之前存整型的时候，堆区的基本数组里直接存储的就是整形的值，但改成存任意对象类型之后，堆区的基本数据里存储可不是对象喔，
 * 比方说我们要存person对象，怎么可能直接把一个一个的person对象直接存在基本数组里嘛，而是存放的是person对象的地址，也
 * 就是说泛型动态数组的基本数组其实是个指针数组，里面存放的是一个一个对象的内存地址，然后这些内存地址才分别指向堆区里一个
 * 一个的person对象
 * 
 * 如果是这样的内存布局的话，那我们的clear和remove方法就有问题了，之前我们的clear是直接把size = 0保留基本数组的内存，并且
 * 基本数组里的数据也不专门浪费时间去搞，以便后面还要使用基本数组，但是现在不能这么搞了，基本数组的内存当然可以继续保留以便后用，
 * 但是基本数组里的数据不能保留了，因为里面的数据是指针，一旦保留就以为这些指针指向的很多对象的内存也是保留的，之前不处理是因为
 * int本身就是存储在基本数组里的，既然我们已经保留了基本数组的内存，那不管你把那个不用的数置为0还是null都还是要占用那块内存的，
 * 所以还不如不花时间耗性能去管它，也就是说这里只有基本数组的内存消耗、没有额外的内存消耗，而现在对象的话有额外的堆内存分配，所以
 * 就不建议空间换时间了，还是要把基本数组里的元素都置位null以便那些对象都能顺利释放内存。remove也是同理，我们只需要把最后一个元素
 * 置为null既可以了。
 * 
 * 4、==和equals的区别
 * ==一般用来比较两个基本数据类型的数据是否相等，它用在对象类型时是判断两个对象的内存地址是否一样，这可能并不符合我们的预期，比如
 * 我们创建了两个person对象，person1和person2所有的数据都是一样的，那我们就应该把它们判定为相等，但是用==来判断它们可不相等，因为
 * 它们是堆区两块不同的内存，因此建议对象用equals方法来判断相等不相等，而且我们可以在Person类里重写equals方法来自定义相等的条件，
 * 所以indexOf方法里的判断要改一改，不能：elements[i] == element这么写了，而是改成：elements[i].equals(element)，但是我们
 * 上面设计数组里可以存储null，那如果elements[i] == null，调用equals方法不就崩了嘛，因此还得做一些健壮性处理
 * 
 * @author yiyi
 *
 * @param <E>
 */
public class _03EArrayList<E> {
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
	private E[] elements;
	
	/**
	 * 我们提供一个带参的构造方法，以便外界在创建动态数组时想主动设置初始容量，这个方法
	 * 就是这个类的万能构造方法
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public _03EArrayList(int capacity) {
		if (capacity < DEFAULT_CAPACITY) {
			capacity = DEFAULT_CAPACITY;
		}
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}
	
	/**
	 * 自定义了带参的构造方法后，系统就不会帮我们自动生成无参的构造方法了，所以我们这里
	 * 再提供一个无参构造方法供外界使用，外界也许不关心什么容量不容量问题呢，就像我们平常
	 * 使用数组的时候那样，只不过默认容量给个10，调用一下全能构造方法即可
	 */
	public _03EArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * 添加元素到最后面
	 * @param element
	 */
	public void add(E element) {
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
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		// 当前数组的长度为size，所以我们每次添加元素时，只需要确保数组容量比当前长度大1，能放进去新元素就行
		ensureCapacity(size + 1);
		
		/* 
		 * 添加元素到指定的位置其实就是把index到size - 1这几个元素依次往后移动一位
		 * 然后再把element放到index的位置
		 * 
		 * 但是这个操作得从尾部往前面倒着来移，不然后面的元素可能被覆盖掉
		 */
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
		/*
		 * 删除一个元素其实就是把这个元素的index + 1到size - 1这几个元素依次往前移动一位，
		 * 让index + 1这个元素覆盖掉这个元素就可以了
		 * 
		 * 至于移动后最后一个元素肯定还保留的是原来的值，那我们需不需要把它置为0或者null呢？可以但没必要，
		 * 因为所有的元素前移后，我们肯定会让size--，那外界其实就访问不到那个保留原来值的最后一个元素了，
		 * 所以那块内存保留原来的数据也不会出现数据错乱，如果我们把这块内存置为0或者null，反而增加了对内存的操作，
		 * 时间上反而会浪费，还不如放着不管它
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
	public E remove(int index) {
		rangeCheck(index);
		
		/*
		 * 思路见void remove(Integer element)这个方法里的描述
		 */
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
		
		/*
		 * 基本数组可以通过index直接覆盖掉指定位置的元素，
		 * 所以直接根据index修改就可以了
		 */
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
	 * 
	 * 因为基本数组在初始化时指定容量后就不可变了，所以当外界使用我们的动态数组一直add时就有可能出现数组容量
	 * 不够的情况，因此我们需要对数组进行扩容，扩容的地方当然就是add里，因为只有这个操作才有可能导致容量不够
	 * 
	 * 扩容的思路其实很简单，那就是开辟一个容量更大的基本数组，然后把旧基本数组里的元素一个一个地搬到这个大基本
	 * 数组里即可，同时由于我们会把elements属性指向这个大基本数组，这样之前的小基本数组就没人指向、它的内存就能
	 * 销毁掉了。至于大基本数组的容量你要搞成多少，这个看你自己的设计，例如Java官方的扩容比例是1.5，而OC官方
	 * 的扩容比例是1.6等，它们都是按比例扩容的，我们也可以参考这种做法，而不要每次扩容加一个数，这样扩容肯定会
	 * 很频繁，浪费时间
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) { // 如果容量够用，则什么都不做
			return;
		}
		
		/*
		 * 如果容量不够用，则把容量扩容为原来的1.5倍，但是这里最好不要用乘以1.5来做，因为：
		 * 1、new int[capacity]这个地方的capacity只能是个整数，所以要是乘以1.5结果是个double，不能直接放进去
		 * 2、乘法运算远不如位运算和加法运算的效率高，一个数右移一位就是除以2
		 */
//		double newCapacity = oldCapacity * 1.5;
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