package communication.messages;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Merlin
 * 
 */
public class FileMessage implements Message
{

	private File file;
	private byte[] fileData;

	/**
	 * @param fileTitle the name of the file we want to send
	 * @throws IOException if the file is missing or we can't read it
	 */
	public FileMessage(String fileTitle) throws IOException
	{
		file = new File(fileTitle);
		fileData = new byte[(int) file.length()];
		DataInputStream dis;

		dis = new DataInputStream(new FileInputStream(file));
		dis.readFully(fileData);
		dis.close();

	}

	/**
	 * @return a string describing this message
	 */
	public String toString()
	{
		return "FileMessage: title = " + file.getName();
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
		return fileData;
	}

}
