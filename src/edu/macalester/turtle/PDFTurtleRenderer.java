package edu.macalester.turtle;

import java.io.FileOutputStream;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFTurtleRenderer implements TurtleObserver {
    private final Document document;
    private final PdfContentByte canvas;

    public PDFTurtleRenderer(String filename, double pageWidth, double pageHeight) {
        document = new Document(new Rectangle((float) pageWidth, (float) pageHeight));
        PdfWriter writer;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        } catch (Exception e) {
            // Throw as runtime exception so students don't have to deal with catching it
            throw new RuntimeException(e);
        }
        document.open();
        canvas = writer.getDirectContentUnder();
        canvas.setLineCap(PdfContentByte.LINE_CAP_ROUND);
        canvas.setLineJoin(PdfContentByte.LINE_JOIN_ROUND);
        // PDF uses math-style y-axis (positive is up), so flip it vertically
        canvas.transform(new AffineTransform(1, 0, 0, -1, 0, document.top() + document.bottom()));
        writer.setCompressionLevel(0);
    }
    
    public void close() {
        if(document.isOpen())
            document.close();
    }
    
    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public void turtleMoved(Turtle turtle, double x0, double y0, double x1, double y1) {
        if(!turtle.isPenDown())
            return;
        
        canvas.setColorStroke(new BaseColor(turtle.getColor().getRGB()));
        canvas.setLineWidth((float) turtle.getPenWidth());
        canvas.moveTo((float) x0, (float) y0);
        canvas.lineTo((float) x1, (float) y1);
        canvas.closePathStroke();
    }

    @Override
    public void turtleTurned(Turtle turtle, double oldDir, double newDir) {
         // ignore
    }

    @Override
    public void turtleChanged(Turtle turtle) {
         // ignore
    }
}
