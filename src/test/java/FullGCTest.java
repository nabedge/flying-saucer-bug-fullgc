import com.lowagie.text.DocumentException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FullGCTest {

    private final String minchoFont = "classpath:ipaexm.ttf"; // IPA "Mincho" Japanese font
    private final String gothicFont = "classpath:ipaexg.ttf"; // IPA "Gothic" Japanese font

    @Test
    public void test_01_good_with_other_words() throws Exception {
        String template = "classpath:template01.xhtml";
        ITextRenderer renderer = createRenderer(minchoFont, template);
        renderer.layout();
        createPdf(renderer);
    }

    @Test
    public void test_02_good_with_other_font() throws Exception {
        String template = "classpath:template02.xhtml";
        ITextRenderer renderer = createRenderer(gothicFont, template);
        renderer.layout();
        createPdf(renderer);
    }

    @Test
    public void test_03_good_with_other_font_size() throws Exception {
        String template = "classpath:template03.xhtml";
        ITextRenderer renderer = createRenderer(minchoFont, template);
        renderer.layout();
        createPdf(renderer);
    }

    @Test
    public void test_04_good_without_break_word() throws Exception {
        String template = "classpath:template04.xhtml";
        ITextRenderer renderer = createRenderer(minchoFont, template);
        renderer.layout();
        createPdf(renderer);
    }

    @Test
    public void test_05_good_with_float_none() throws Exception {
        String template = "classpath:template05.xhtml";
        ITextRenderer renderer = createRenderer(minchoFont, template);
        renderer.layout();
        createPdf(renderer);
    }

    @Test
    public void test_09_bad() throws Exception {
        String template = "classpath:template09.xhtml";
        ITextRenderer renderer = createRenderer(minchoFont, template);
        renderer.layout();
        createPdf(renderer);
    }

    private ITextRenderer createRenderer(String fontFileLocation, String templateFileLocation)
        throws IOException, DocumentException {

        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();

        String fontPath = ResourceUtils.getURL(fontFileLocation).toExternalForm();
        fontResolver.addFont(fontPath, "Identity-H", false);

        File template = ResourceUtils.getFile(templateFileLocation);
        FileInputStream fis = new FileInputStream(template);

        Document dom = XMLResource.load(fis).getDocument();
        renderer.setDocument(dom, null);

        return renderer;
    }

    private void createPdf(ITextRenderer renderer) throws DocumentException, IOException {
        String uuid = UUID.randomUUID().toString();
        FileOutputStream fos = new FileOutputStream("/tmp/result-" + uuid +".pdf");
        renderer.createPDF(fos);
        fos.flush();
        fos.close();
    }

}
