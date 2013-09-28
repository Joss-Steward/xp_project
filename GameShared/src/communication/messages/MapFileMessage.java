package communication.messages;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

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

	/**
	 * @param fileTitle the name of the file we want to send
	 * @throws IOException if the file is missing or we can't read it
	 */
	public MapFileMessage(String fileTitle) throws IOException
	{
		file = new File(fileTitle);
		contents = new byte[(int) file.length()];
		DataInputStream dis;

		dis = new DataInputStream(new FileInputStream(file));
		dis.readFully(contents);
		dis.close();

	}

	/**
	 * @return a string describing this message
	 */
	public String toString()
	{
		return "FileMessage: number of bytes = " + contents.length;
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

}
