/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.code2svg.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.XMLAssert;
import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;

/**
 * Tests for {@link Code2SvgConfig}.
 */
public class Code2SvgConfigTest {

    // CHECKSTYLE:OFF

    @Test
    public final void testPojoStructureAndBehavior() {

        final PojoClass pc = PojoClassFactory.getPojoClass(Code2SvgConfig.class);
        final ValidatorBuilder pv = ValidatorBuilder.create();

        pv.with(new NoPublicFieldsRule());
        pv.with(new NoFieldShadowingRule());

        pv.with(new DefaultValuesNullTester());
        pv.with(new GetterTester());

        pv.build().validate(pc);

    }

    @Test
    public final void testMarshal() throws Exception {

        // PREPARE
        final RegExprElement el = new RegExprElement("string", "fill: rgb(42, 0, 255)", "\".*?\"");
        final Code2SvgConfig testee = new Code2SvgConfig.Builder().fileExtension(".ddd").addElement(el).build();

        // TEST
        final String result = JaxbUtils.marshal(testee, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());
        System.out.println(result);

        // VERIFY
        final String expected = "<code2svg file-extension=\".ddd\" text-css=\"font-size: 11pt; font-family: monospace\"><reg-expr-element name =\"string\" css=\"fill: rgb(42, 0, 255)\" pattern=\"&quot;.*?&quot;\" /></code2svg>";
        XMLAssert.assertXMLEqual(JaxbUtils.XML_PREFIX + expected, result);

    }

    @Test
    public final void testUnmarshalSingleElement() throws Exception {

        // PREPARE
        final RegExprElement expectedEl = new RegExprElement("string", "fill: rgb(42, 0, 255)", "\".*?\"");
        final String xml = "<code2svg><reg-expr-element name =\"string\" css=\"fill: rgb(42, 0, 255)\" pattern=\"&quot;.*?&quot;\" /></code2svg>";

        // TEST
        final Code2SvgConfig testee = JaxbUtils.unmarshal(xml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        // VERIFY
        assertThat(testee).isNotNull();
        assertThat(testee.getElements()).containsOnly(expectedEl);

    }

    @Test
    public final void testUnmarshalMultipleElements() throws Exception {

        // PREPARE
        final URL url = Code2Svg.class.getResource("/code-2-svg.xml");
        final String xml = Utils4J.readAsString(url, "utf-8", 1024);

        final List<String> keywords = new ArrayList<>();
        keywords.add("acceptable");
        keywords.add("aggregate");
        keywords.add("aggregate-id");
        keywords.add("automatic");
        keywords.add("base");
        keywords.add("business-rules");
        keywords.add("cid|consistency");
        keywords.add("constraint");
        keywords.add("constructor");
        keywords.add("context");
        keywords.add("days");
        keywords.add("deprecated");
        keywords.add("detection");
        keywords.add("element");
        keywords.add("entity");
        keywords.add("entity-id");
        keywords.add("enum");
        keywords.add("event");
        keywords.add("examples");
        keywords.add("exception");
        keywords.add("false");
        keywords.add("fires");
        keywords.add("generics");
        keywords.add("hours");
        keywords.add("identifier");
        keywords.add("identifies");
        keywords.add("import");
        keywords.add("input");
        keywords.add("instances");
        keywords.add("invariants");
        keywords.add("label");
        keywords.add("manually");
        keywords.add("message");
        keywords.add("method");
        keywords.add("millis");
        keywords.add("minutes");
        keywords.add("namespace");
        keywords.add("never");
        keywords.add("null");
        keywords.add("nullable");
        keywords.add("preconditions");
        keywords.add("prompt");
        keywords.add("ref");
        keywords.add("resolution");
        keywords.add("returns");
        keywords.add("root");
        keywords.add("seconds");
        keywords.add("service");
        keywords.add("slabel");
        keywords.add("strong");
        keywords.add("tooltip");
        keywords.add("true");
        keywords.add("type");
        keywords.add("value-object");
        keywords.add("weak");
        keywords.add("workflow");

        final Element[] elements = new Element[] { new RegExprElement("whatever", "fill: red", "begin.*end"),
                new StringElement("string", "fill: rgb(42, 0, 255)", false),
                new MultiLineCommentElement("ml-comment", "fill: rgb(63, 127, 95)"),
                new SingleLineCommentElement("sl-comment", "fill: rgb(63, 127, 95)"),
                new NumberElement("number", "fill: rgb(125, 125, 125)"),
                new KeywordElement("keyword", "fill: rgb(127, 0, 85); font-weight: bold", keywords) };

        // TEST
        final Code2SvgConfig testee = JaxbUtils.unmarshal(xml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        // VERIFY
        assertThat(testee).isNotNull();
        assertThat(testee.getElements()).containsOnly(elements);

    }

    // CHECKSTYLE:ON

}
