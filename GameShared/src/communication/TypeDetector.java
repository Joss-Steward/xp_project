package communication;

import java.util.ArrayList;
import java.util.Map;

import org.reflections.Reflections;

import com.google.common.collect.Multimap;


/**
 * @author Merlin
 *
 */
public abstract class TypeDetector
{

	
	/**
	 * 
	 */
	protected ArrayList<Class<?>> detectAllExtendersInPackage(Class<?> type)
	{
		ArrayList<Class<?>> results = new ArrayList<Class<?>>();
		Reflections reflections = new Reflections(this.getClass().getPackage().getName());
	
		Multimap<String, String> mmap = reflections.getStore().getStoreMap().get("SubTypesScanner");
		for (Map.Entry<String, String> entry : mmap.entries())
		{
			// skip over private classes that are inside another class
			if (!entry.getValue().contains("$"))
			{
				try
				{
					Class<?> classToRegister = Class.forName(entry.getValue());
					if (extendsClass(classToRegister, type))
					{
						results.add(classToRegister);
					}
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				} 
			}
		}
		return results;
	}
	/**
	 * 
	 */
	protected ArrayList<Class<?>> detectAllImplementorsInPackage(Class<?> type)
	{
		ArrayList<Class<?>> results = new ArrayList<Class<?>>();
		Reflections reflections = new Reflections(this.getClass().getPackage().getName());
	
		Multimap<String, String> mmap = reflections.getStore().getStoreMap().get("SubTypesScanner");
		for (Map.Entry<String, String> entry : mmap.entries())
		{
			// skip over private classes that are inside another class
			if (!entry.getValue().contains("$"))
			{
				try
				{
					Class<?> classToRegister = Class.forName(entry.getValue());
					if (implementsInterface(classToRegister, type))
					{
						results.add(classToRegister);
					}
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				} 
			}
		}
		return results;
	}

	/**
	 * @param classToRegister
	 * @param classType
	 * @return
	 */
	protected boolean extendsClass(Class<?> classToRegister, Class<?> classType)
	{
		if (classToRegister.equals(classType) || classToRegister.getSuperclass().equals(classType))
		{
			return true;
		}
		Class<?> superclass = classToRegister.getSuperclass();
		if (!superclass.equals(Object.class))
		{
			return extendsClass(classToRegister.getSuperclass(), classType);
		}
		return false;
	}

	/**
	 * @param classToRegister
	 * @return
	 */
	private boolean implementsInterface(Class<?> classToRegister, Class<?> interfaceType)
	{
		if (classToRegister.equals(interfaceType))
		{
			return false;
		}
		Class<?>[] list = classToRegister.getInterfaces();
		boolean found = false;
		for (Class<?> item : list)
		{
			if (recursivelyImplements(item, interfaceType))
			{
				found = true;
			}
		}
		return found;
	
	}

	/**
	 * @param item
	 * @return
	 */
	private boolean recursivelyImplements(Class<?> item, Class<?> interfaceType)
	{
		if (item == interfaceType)
		{
			return true;
		}
		Class<?>[] list = item.getInterfaces();
		boolean found = false;
		for (Class<?> nextItem : list)
		{
			if (recursivelyImplements(nextItem, interfaceType))
			{
				found = true;
			}
		}
	
		return found;
	}

}
