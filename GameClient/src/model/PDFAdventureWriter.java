package model;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

/**
 * Creates a PDF file of the current players triggered adventures
 * 
 * @author Merlin
 *
 */
public class PDFAdventureWriter
{

	/**
	 * Creates a file containing the player's currently triggered adventures
	 * 
	 * @param fileTitle
	 *            the title of the file we should create
	 */
	public void createPDFOfTriggeredExternalAdventures(String fileTitle)
	{
		PDDocument doc = new PDDocument();
		for (ClientPlayerQuest q : ClientPlayerManager.getSingleton()
				.getThisClientsPlayer().getQuests())
		{
			for (ClientPlayerAdventure a : q.getAdventureList())
			{
				if (a.isRealLifeAdventure())
				{
					PDPage page = new PDPage(PDRectangle.LETTER);
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
						// add the rotation using the current transformation
						// matrix
						// including a translation of pageWidth to use the lower
						// left corner as 0,0 reference
						contents.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

						// Place the image as the background
						PDImageXObject pdImage = PDImageXObject.createFromFile(
								"AdventureTemplate.jpg", doc);
						contents.saveGraphicsState();
						contents.drawImage(pdImage, 0, 0);
						contents.restoreGraphicsState();

						// Quest name
						contents.beginText();
						contents.newLineAtOffset(75, 545);
						contents.setFont(fancyFont, 14);
						contents.showText("Part of quest titled '" + q.getQuestTitle()
								+ "'");
						contents.endText();

						// Adventure description
						contents.beginText();
						contents.newLineAtOffset(100, 425);
						contents.setFont(font, 12);
						contents.showText(a.getAdventureDescription());
						contents.endText();

						// Experience points
						contents.beginText();
						contents.newLineAtOffset(430, 340);
						contents.setFont(fancyFont, 18);
						contents.showText(String.valueOf(a.getAdventureXP()));
						contents.endText();

						// Witness title
						contents.beginText();
						contents.newLineAtOffset(580, 85);
						contents.setFont(fancyFont, 18);
						contents.showText(a.getWitnessTitle());
						contents.endText();

						contents.close();

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
