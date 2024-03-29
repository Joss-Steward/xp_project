package communication;

import java.util.ArrayList;
import java.util.Map;

import org.reflections.Reflections;

import com.google.common.collect.Multimap;

/**
 * Finds all of the classes in a package that extend a given type
 * 
 * @author Merlin
 * 
 */
public abstract class TypeDetector
{

	/**
	 * Get all of the types that extend a given type in this package
	 * 
	 * @param type the type that we are detecting
	 * @return all of the types that extend the given type
	 * 
	 */
	protected ArrayList<Class<?>> detectAllExtendersInPackage(Class<?> type)
	{
		return detectAllExtendersInPackage(type, this.getClass().getPackage().getName());
	}

	/**
	 * @param type the type we are interested in
	 * @param packageName the name of the package we should search
	 * @return the types
	 * 
	 */
	protected ArrayList<Class<?>> detectAllExtendersInPackage(Class<?> type, String packageName)
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
	 * Detect all of the types that implement a given type within the current
	 * package
	 * 
	 * @param type the type we are interested in
	 * @return the types we find
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
	 * Checks to see if a class that we are trying to register extends another
	 * class - either directly or as a descendent further down the inheritance
	 * hierarchy
	 * 
	 * @param classToRegister the class we are trying to register
	 * @param classType the type we want to check
	 * @return true if the class being registered extends the given classType
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
	 * Checks to see if a class being registered implements a given interface
	 * 
	 * @param classToRegister the class we are registeringa
	 * @param interfaceType the interface we want to check
	 * @return true if the class implements that interface
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
	 * looks recursively to see if a class implements a given interface
	 * 
	 * @param item the class we are checking
	 * @param interfaceType the interface we want to look for
	 * @return true if the class implements the interface
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
