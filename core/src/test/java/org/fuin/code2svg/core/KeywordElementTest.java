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

import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.XMLAssert;
import org.fuin.utils4j.JaxbUtils;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;

/**
 * Tests for {@link KeywordElement}.
 */
public class KeywordElementTest {

    // CHECKSTYLE:OFF

    @Test
    public final void testPojoStructureAndBehavior() {

        final PojoClass pc = PojoClassFactory.getPojoClass(KeywordElement.class);
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
        final List<String> keywords = new ArrayList<>();
        keywords.add("one");
        keywords.add("two");
        keywords.add("three");
        final KeywordElement testee = new KeywordElement("keyword", "fill: rgb(42, 0, 255)", keywords);

        // TEST
        final String result = JaxbUtils.marshal(testee, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        // VERIFY
        final String expected = "<keyword-element name =\"keyword\" css=\"fill: rgb(42, 0, 255)\">" + "<keyword>one</keyword>"
                + "<keyword>two</keyword>" + "<keyword>three</keyword>" + "</keyword-element>";
        XMLAssert.assertXMLEqual(JaxbUtils.XML_PREFIX + expected, result);

    }

    @Test
    public final void testUnmarshal() throws Exception {

        // PREPARE
        final String xml = "<keyword-element name =\"keyword\" css=\"fill: rgb(42, 0, 255)\">" + "<keyword>one</keyword>"
                + "<keyword>two</keyword>" + "<keyword>three</keyword>" + "</keyword-element>";

        // TEST
        final KeywordElement testee = JaxbUtils.unmarshal(xml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        // VERIFY
        assertThat(testee).isNotNull();
        assertThat(testee.getName()).isEqualTo("keyword");
        assertThat(testee.getCSS()).isEqualTo("fill: rgb(42, 0, 255)");
        assertThat(testee.getKeywords()).containsOnly("one", "two", "three");
        assertThat(testee.getPattern()).isNotNull();
        assertThat(testee.matcher("123")).isNotNull();

    }

    @Test
    public final void testCreate() {

        // TEST
        final List<String> keywords = new ArrayList<>();
        keywords.add("one");
        keywords.add("two");
        keywords.add("three");
        final KeywordElement testee = new KeywordElement("keyword", "fill: rgb(42, 0, 255)", keywords);

        // VERIFY
        assertThat(testee.getName()).isEqualTo("keyword");
        assertThat(testee.getCSS()).isEqualTo("fill: rgb(42, 0, 255)");
        assertThat(testee.getPattern()).isNotNull();

    }

    // CHECKSTYLE:ON

}
