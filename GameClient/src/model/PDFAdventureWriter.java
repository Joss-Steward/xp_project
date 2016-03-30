package model;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

/**
 * Creates a PDF file of the current players triggered adventures
 * @author Merlin
 *
 */
public class PDFAdventureWriter
{

	/**
	 * Creates a file containing the player's currently triggered adventures
	 * @param fileTitle the title of the file we should create
	 */
	public void createPDFOfTriggeredExternalAdventures(String fileTitle)
	{
		PDDocument doc = new PDDocument();
		for (ClientPlayerQuest q : ClientPlayerManager.getSingleton().getThisClientsPlayer().getQuests())
		{
			for (ClientPlayerAdventure a : q.getAdventureList())
			{

				PDPage page = new PDPage(PDRectangle.A4);
				page.setRotation(90);
				doc.addPage(page);

				PDFont fancyFont = PDType1Font.TIMES_BOLD_ITALIC;
				PDFont font = PDType1Font.HELVETICA;
				PDRectangle pageSize = page.getMediaBox();
				float pageWidth = pageSize.getWidth();
				PDPageContentStream contents;
				try
				{

					contents = new PDPageContentStream(doc, page);
					// add the rotation using the current transformation matrix
					// including a translation of pageWidth to use the lower
					// left corner as 0,0 reference
					contents.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

					contents.beginText();
					contents.newLineAtOffset(150, 450);
					contents.setFont(font, 12);
					contents.showText(a.getAdventureDescription());					
					contents.endText();
					
					contents.beginText();
					contents.newLineAtOffset(150, 400);
					contents.setFont(fancyFont, 18);
					contents.showText("and will reward you with " + a.getAdventureXP() + " Experience Points for successful completion");
					contents.endText();
					
					contents.beginText();
					contents.newLineAtOffset(400, 100);
					contents.setFont(fancyFont, 14);
					contents.showText("This adventure is part of the quest named _________");
					contents.endText();					
					
					contents.close();

				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		try
		{
			doc.save(fileTitle);
			doc.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
