package com.xr.question3;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 用java基础库（反射）实现一个简单IOC容器。
 * @author Administrator
 *
 */
public class SpringIOC {

	/**
	 * 配置文件地址
	 */
	private static final String CONFIGURATION_PATH = "src/main/resources/applicationContext.xml";

	/**
	 * ioc容器
	 */
	private static Map<String, Object> ioc = new HashMap<>();

	static {
		initialization();
	}

	/**
	 * 从 ioc 容器中获取指定 bean
	 *
	 * @param name
	 *            需要获取的 bean 的 id, 对应 XML 配置文件中的 bean id
	 * @return bean
	 */
	public static Object getBean(String name) {
		return ioc.get(name);
	}

	/**
	 * 初始化容器
	 */
	private static void initialization() {
		Document document = null;

		try {
			DocumentBuilderFactory bdf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = bdf.newDocumentBuilder();
			document = documentBuilder.parse(CONFIGURATION_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}

		NodeList beanNodes = document.getElementsByTagName("bean");

		for (int i = 0; i < beanNodes.getLength(); i++) {
			Node node = beanNodes.item(i);
			reloadBean(node);
		}
	}

	/**
	 * 装载 benn
	 *
	 * @param beanNode
	 *            xml 文件 bean 根节点
	 */
	private static void reloadBean(Node beanNode) {
		Element bean = (Element) beanNode;

		String id = bean.getAttribute("id"); // IOC 容器中 bean 的名字
		String beanClass = bean.getAttribute("class"); // 全类名

		// 每个 bean 节点下的全部 property 节点
		NodeList childNodes = beanNode.getChildNodes();
		Map<String, String> attributeMap = reloadAttribute(childNodes);

		// 使用反射构造 bean 对象
		Object instance = creatBean(beanClass, attributeMap);

		// 将所有的 bean 对象放入容器中
		ioc.put(id, instance);
	}

	/**
	 * 加载 bean 的属性值
	 *
	 * @param attributeNodes
	 *            所有的属性 property 节点
	 * @return Map 属性的名字和值集合
	 */
	private static Map<String, String> reloadAttribute(NodeList attributeNodes) {
		Map<String, String> keyValue = new HashMap<>();
		for (int i = 0; i < attributeNodes.getLength(); i++) {
			Node filed = attributeNodes.item(i);
			if (filed.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) filed;
				String fileName = element.getAttribute("name");
				String value = element.getFirstChild().getNodeValue();
				keyValue.put(fileName, value);
			}
		}
		return keyValue;
	}

	/**
	 * 构造bean对象
	 *
	 * @param className
	 *            全类名
	 * @param attributes
	 *            每个对象的属性和
	 * @return Object 构造完成的 bean 对象
	 */
	private static Object creatBean(String className,
			Map<String, String> attributes) {
		Object instance = null;
		try {
			Class<?> clazz = Class.forName(className);
			instance = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				setFiledValue(instance, field, attributes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	/**
	 * 为实例对象的属性赋值
	 *
	 * @param instance
	 *            实例对象
	 * @param field
	 *            属性字段对象
	 * @param attributes
	 *            属性名与属性值的 Map 集合
	 */
	private static void setFiledValue(Object instance, Field field,
			Map<String, String> attributes) {
		// 忽略 field 权限检查
		field.setAccessible(true);

		String type = field.getType().toString();
		String name = field.getName();

		try {
			switch (type) {
			case "char":
				field.setChar(instance, attributes.get(name).charAt(0));
				break;

			case "class java.lang.Boolean":
			case "boolean":
				field.setBoolean(instance,
						Boolean.parseBoolean(attributes.get(name)));
				break;

			case "class java.lang.Byte":
			case "byte":
				field.setByte(instance, Byte.parseByte(attributes.get(name)));
				break;

			case "class java.lang.Float":
			case "float":
				field.setFloat(instance, Float.parseFloat(attributes.get(name)));
				break;

			case "class java.lang.Integer":
			case "int":
				field.setInt(instance, Integer.parseInt(attributes.get(name)));
				break;

			case "class java.lang.Long":
			case "long":
				field.setLong(instance, Long.parseLong(attributes.get(name)));
				break;

			case "class java.lang.Short":
			case "short":
				field.setShort(instance, Short.parseShort(attributes.get(name)));
				break;

			default:
				field.set(instance, attributes.get(name));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		// 不使用 new 的方式创建对象, 从容器中获取
		Student stu1 = (Student) SpringIOC.getBean("stu3");
		// 调用学生类的方法,打印信息
		System.out.println(stu1.toString());
	}

}
