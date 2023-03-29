/*
 * Copyright 2002-2009 Andy Clark, Marc Guillemot
 * Copyright 2017-2023 Ronald Brill
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.htmlunit.cyberneko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.htmlunit.cyberneko.filters.DefaultFilter;
import org.htmlunit.cyberneko.xerces.util.XMLStringBuffer;
import org.htmlunit.cyberneko.xerces.xni.Augmentations;
import org.htmlunit.cyberneko.xerces.xni.QName;
import org.htmlunit.cyberneko.xerces.xni.XMLAttributes;
import org.htmlunit.cyberneko.xerces.xni.XNIException;
import org.htmlunit.cyberneko.xerces.xni.parser.XMLDocumentFilter;
import org.htmlunit.cyberneko.xerces.xni.parser.XMLInputSource;
import org.htmlunit.cyberneko.xerces.xni.parser.XMLParserConfiguration;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link HTMLScanner}.
 * @author Marc Guillemot
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
public class HTMLScannerTest {

    @Test
    public void isEncodingCompatible() {
        final HTMLScanner scanner = new HTMLScanner(new HTMLConfiguration());
        assertTrue(scanner.isEncodingCompatible("ISO-8859-1","ISO-8859-1"));
        assertTrue(scanner.isEncodingCompatible("UTF-8","UTF-8"));
        assertTrue(scanner.isEncodingCompatible("UTF-16","UTF-16"));
        assertTrue(scanner.isEncodingCompatible("US-ASCII","ISO-8859-1"));
        assertTrue(scanner.isEncodingCompatible("UTF-8","ISO-8859-1"));

        assertFalse(scanner.isEncodingCompatible("UTF-8","UTF-16"));
        assertFalse(scanner.isEncodingCompatible("ISO-8859-1","UTF-16"));
        assertFalse(scanner.isEncodingCompatible("UTF-16","Cp1252"));
    }

    @Test
    public void evaluateInputSource() throws Exception {
        final String string = "<html><head><title>foo</title></head>"
            + "<body>"
            + "<script id='myscript'>"
            + "  document.write('<style type=\"text/css\" id=\"myStyle\">');"
            + "  document.write('  .nwr {white-space: nowrap;}');"
            + "  document.write('</style>');"
            + "  document.write('<div id=\"myDiv\"><span></span>');"
            + "  document.write('</div>');"
            + "</script>"
            + "<div><a/></div>"
            + "</body></html>";
        final HTMLConfiguration parser = new HTMLConfiguration();
        final EvaluateInputSourceFilter filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expectedString = {"(html", "(head", "(title", ")title", ")head", "(body", "(script",
            ")script", "~inserting", "(style", "~inserting", "~inserting", ")style", "~inserting",
            "(div", "(span", ")span", "~inserting", ")div", "(div", "(a", ")a", ")div", ")body", ")html"};
        assertEquals(Arrays.asList(expectedString), filter.collectedStrings);
    }

    /**
     * Ensure that the current locale doesn't affect the HTML tags.
     * see issue https://sourceforge.net/tracker/?func=detail&atid=952178&aid=3544334&group_id=195122
     * @throws Exception on error
     */
    @Test
    public void locale() throws Exception {
        final Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(new Locale("tr", "TR"));
            final String string = "<html><head><title>foo</title></head>"
                + "<body>"
                + "</body></html>";
            final HTMLConfiguration parser = new HTMLConfiguration();
            final EvaluateInputSourceFilter filter = new EvaluateInputSourceFilter(parser);
            parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
            final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
            parser.parse(source);

            final String[] expectedString = {"(html", "(head", "(title", ")title", ")head", "(body", ")body", ")html"};
            assertEquals(Arrays.asList(expectedString).toString(), filter.collectedStrings.toString());
        }
        finally {
            Locale.setDefault(originalLocale);
        }
    }

    /**
     * Tests handling of xml declaration when used with Reader.
     * Following test caused NPE with release 1.9.11.
     * Regression test for [ 2503982 ] NPE when parsing from a CharacterStream
     */
    @Test
    public void changeEncodingWithReader() throws Exception {
        final String string = "<?xml version='1.0' encoding='UTF-8'?><html><head><title>foo</title></head>"
            + "</body></html>";

        final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "ISO8859-1");
        final HTMLConfiguration parser = new HTMLConfiguration();
        parser.parse(source);
    }

    private static class EvaluateInputSourceFilter extends DefaultFilter {

       private final List<String> collectedStrings = new ArrayList<>();
       private static int counter = 1;
       protected final HTMLConfiguration fConfiguration;

       public EvaluateInputSourceFilter(HTMLConfiguration config) {
           fConfiguration = config;
       }

       @Override
       public void startElement(QName element, XMLAttributes attrs, Augmentations augs) throws XNIException {
           collectedStrings.add("(" + element.rawname);
       }

       @Override
       public void endElement(QName element, Augmentations augs) throws XNIException {
           collectedStrings.add(")" + element.rawname);
           if (element.localpart.equalsIgnoreCase("SCRIPT")) {
               // act as if evaluation of document.write would insert the content
               insert("<style type=\"text/css\" id=\"myStyle\">");
               insert("  .nwr {white-space: nowrap;}");
               insert("</style>");
               insert("<div id=\"myDiv\"><span></span>");
               insert("</div>");
           }
       }

        private void insert(final String string) {
            collectedStrings.add("~inserting");
            final XMLInputSource source = new XMLInputSource(null, "myTest" + counter++, null,
                                                      new StringReader(string), "UTF-8");
            fConfiguration.evaluateInputSource(source);
       }

    }

    @Test
    public void reduceToContent() {
        XMLStringBuffer buffer = new XMLStringBuffer("<!-- hello-->");

        HTMLScanner.reduceToContent(buffer, "<!--", "-->");
        assertEquals(" hello", buffer.toString());

        buffer = new XMLStringBuffer("  \n <!-- hello-->\n");
        HTMLScanner.reduceToContent(buffer, "<!--", "-->");
        assertEquals(" hello", buffer.toString());

        buffer = new XMLStringBuffer("hello");
        HTMLScanner.reduceToContent(buffer, "<!--", "-->");
        assertEquals("hello", buffer.toString());

        buffer = new XMLStringBuffer("<!-- hello");
        HTMLScanner.reduceToContent(buffer, "<!--", "-->");
        assertEquals("<!-- hello", buffer.toString());

        buffer = new XMLStringBuffer("<!--->");
        HTMLScanner.reduceToContent(buffer, "<!--", "-->");
        assertEquals("<!--->", buffer.toString());
    }

    /**
     * Regression test for bug 2933989.
     * @throws Exception on error
     */
    @Test
    public void infiniteLoop() throws Exception {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("<html>\n");
        for (int x = 0; x <= 2005; x++) {
            buffer.append((char) (x % 10 + '0'));
        }

        buffer.append("\n<noframes>- Generated in 1<1ms -->");

        final XMLParserConfiguration parser = new HTMLConfiguration() {
            @Override
            protected HTMLScanner createDocumentScanner() {
                return new InfiniteLoopScanner();
            }
        };
        final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(buffer.toString()), "UTF-8");
        parser.parse(source);
    }

    static class InfiniteLoopScanner extends HTMLScanner {
        InfiniteLoopScanner() {
            super(new HTMLConfiguration());
            fContentScanner = new MyContentScanner();
        }

        class MyContentScanner extends HTMLScanner.ContentScanner {

            @Override
            protected void scanComment() throws IOException {
                // bug was here: calling nextContent() at the end of the buffer/input
                nextContent(30);
                super.scanComment();
            }
        }
    }

    /**
     * @throws Exception on error
     */
    @Test
    public void elementNameNormalization() throws Exception {
        // not set
        final String string = "<HTML><Head><tiTLE>foo</tiTLE></hEaD><Body></BOdy></htMl>";

        HTMLConfiguration parser = new HTMLConfiguration();
        EvaluateInputSourceFilter filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expectedString = {"(HTML", "(Head", "(tiTLE", ")tiTLE", ")Head", "(Body", ")Body", ")HTML"};
        assertEquals(Arrays.asList(expectedString).toString(), filter.collectedStrings.toString());

        // upper
        parser = new HTMLConfiguration();
        filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/names/elems", "upper");
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expectedStringUpper = {"(HTML", "(HEAD", "(TITLE", ")TITLE", ")HEAD", "(BODY", ")BODY", ")HTML"};
        assertEquals(Arrays.asList(expectedStringUpper).toString(), filter.collectedStrings.toString());

        // upper
        parser = new HTMLConfiguration();
        filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expectedStringLower = {"(html", "(head", "(title", ")title", ")head", "(body", ")body", ")html"};
        assertEquals(Arrays.asList(expectedStringLower).toString(), filter.collectedStrings.toString());
    }

    /**
     * Regression test for an oom exception in versions < 2.60.
     * @throws Exception on error
     */
    @Test
    public void invalidProcessingInstruction() throws Exception {
        final String string = "<!--?><?a/";

        final HTMLConfiguration parser = new HTMLConfiguration();
        final EvaluateInputSourceFilter filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expected = {"(HTML", "(head", ")head", "(body", ")body", ")html"};
        assertEquals(Arrays.asList(expected).toString(), filter.collectedStrings.toString());
    }

    /**
     * Regression test for an index out of bounds exception in versions < 2.60.
     * @throws Exception on error
     */
    @Test
    public void invalidProcessingInstruction2() throws Exception {
        final String string = "<?ax\r";

        final HTMLConfiguration parser = new HTMLConfiguration();
        final EvaluateInputSourceFilter filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expected = {"(HTML", "(head", ")head", "(body", ")body", ")html"};
        assertEquals(Arrays.asList(expected).toString(), filter.collectedStrings.toString());
    }

    /**
     * Regression test for an index out of bounds exception in versions < 2.60.
     * @throws Exception on error
     */
    @Test
    public void invalidProcessingInstruction3() throws Exception {
        final String string = "<?a x\r";

        final HTMLConfiguration parser = new HTMLConfiguration();
        final EvaluateInputSourceFilter filter = new EvaluateInputSourceFilter(parser);
        parser.setProperty("http://cyberneko.org/html/properties/filters", new XMLDocumentFilter[] {filter});
        final XMLInputSource source = new XMLInputSource(null, "myTest", null, new StringReader(string), "UTF-8");
        parser.parse(source);

        final String[] expected = {"(HTML", "(head", ")head", "(body", ")body", ")html"};
        assertEquals(Arrays.asList(expected).toString(), filter.collectedStrings.toString());
    }
}