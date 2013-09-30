package communication.messages;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import tmxfiles.TMXMapReader;



/**
 * @author Merlin
 * 
 */
public class MapFileMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient File file;
	private byte[] contents;
	private ArrayList<String> imageFileTitles;
	private ArrayList<byte[]> imageFiles;

	/**
	 * @param fileTitle the name of the file we want to send
	 * @throws IOException if the file doesn't exist or is poorly formatted
	 */
	public MapFileMessage(String fileTitle) throws IOException
	{
		file = new File(fileTitle);
		contents = readInFile(fileTitle);
		TMXMapReader mapFileReader = new TMXMapReader(fileTitle);
		imageFileTitles = mapFileReader.getImageFileTitles();
		imageFiles = new ArrayList<byte[]> ();
		ArrayList<String> modifiedTitles = new ArrayList<String>();
		String absolutePath = file.getAbsolutePath();
		String path = absolutePath.substring(0, absolutePath.lastIndexOf('\\')+1);
		for (String imgTitle:imageFileTitles)
		{
			String absoluteImgFileTitle = path + imgTitle;
			modifiedTitles.add(absoluteImgFileTitle);
			imageFiles.add(readInFile(absoluteImgFileTitle));
		}
	}

	private byte[] readInFile(String fileTitle) throws IOException
	{
		File thisfile = new File(fileTitle);
		byte[] results = new byte[(int) thisfile.length()];
		DataInputStream dis;

		dis = new DataInputStream(new FileInputStream(thisfile));
		dis.readFully(results);
		dis.close();
		
		return results;
	}
	/**
	 * @return a string describing this message
	 */
	public String toString()
	{
		return "FileMessage: number of bytes = " + contents.length + " number of tilesets = " + imageFileTitles.size();
	}

	/**
	 * @return the title of the file we are sending
	 */
	public String getFileTitle()
	{
		return file.getName();
	}

	/**
	 * @return the contents of the file we are sending
	 */
	public byte[] getContents()
	{
		return contents;
	}

	/**
	 * @return the relative titles of the images referenced by this tmx file
	 */
	public ArrayList<String> getImageFileTitles()
	{
		return imageFileTitles;
	}

	/**
	 * @return the contents of the images referenced by this tmx file
	 */
	public ArrayList<byte[]> getImageFiles()
	{
		return imageFiles;
	}

}
