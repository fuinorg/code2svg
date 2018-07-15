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

import org.custommonkey.xmlunit.XMLAssert;
import org.fuin.code2svg.core.NumberElement;
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
 * Tests for {@link NumberElement}.
 */
public class NumberElementTest {

    // CHECKSTYLE:OFF

    @Test
    public final void testPojoStructureAndBehavior() {

        final PojoClass pc = PojoClassFactory.getPojoClass(NumberElement.class);
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
        final NumberElement testee = new NumberElement("number", "fill: rgb(42, 0, 255)");

        // TEST
        final String result = JaxbUtils.marshal(testee, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        // VERIFY
        final String expected = "<number-element name =\"number\" css=\"fill: rgb(42, 0, 255)\" />";
        XMLAssert.assertXMLEqual(JaxbUtils.XML_PREFIX + expected, result);

    }

    @Test
    public final void testUnmarshal() throws Exception {

        // PREPARE
        final String xml = "<number-element name =\"number\" css=\"fill: rgb(42, 0, 255)\" />";

        // TEST
        final NumberElement testee = JaxbUtils.unmarshal(xml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        // VERIFY
        assertThat(testee).isNotNull();
        assertThat(testee.getName()).isEqualTo("number");
        assertThat(testee.getCSS()).isEqualTo("fill: rgb(42, 0, 255)");
        assertThat(testee.getPattern()).isNotNull();
        assertThat(testee.matcher("whatever")).isNotNull();

    }

    @Test
    public final void testCreateDoubleQuotes() {

        // TEST
        final NumberElement testee = new NumberElement("number", "fill: rgb(42, 0, 255)");

        // VERIFY
        assertThat(testee.getName()).isEqualTo("number");
        assertThat(testee.getCSS()).isEqualTo("fill: rgb(42, 0, 255)");
        assertThat(testee.getPattern()).isNotNull();

    }

    @Test
    public final void testCreateSingleQuotes() {

        // TEST
        final NumberElement testee = new NumberElement("number", "fill: rgb(42, 0, 255)");

        // VERIFY
        assertThat(testee.getName()).isEqualTo("number");
        assertThat(testee.getCSS()).isEqualTo("fill: rgb(42, 0, 255)");
        assertThat(testee.getPattern()).isNotNull();

    }
    
    // CHECKSTYLE:ON

}