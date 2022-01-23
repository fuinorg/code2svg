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
import org.fuin.utils4j.JaxbUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;

/**
 * Tests for {@link FileConfig}.
 */
public class FileConfigTest {

    // CHECKSTYLE:OFF

    @Test
    @Ignore("Investigate why: IllegalArgumentException: Unknown flag 0xa3895115 at java.base/java.util.regex.Pattern.<init>(Pattern.java:1409)")
    public final void testPojoStructureAndBehavior() {

        final PojoClass pc = PojoClassFactory.getPojoClass(FileConfig.class);
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
        final FileConfig testee = new FileConfig(".*/abc\\.ddd", 800, 600);

        // TEST
        final String result = JaxbUtils.marshal(testee, Code2SvgUtils.JAXB_CLASSES);

        // VERIFY
        final String expected = "<file-config name=\".*/abc\\.ddd\" width=\"800\" height =\"600\" />";
        XMLAssert.assertXMLEqual(JaxbUtils.XML_PREFIX + expected, result);

    }

    @Test
    public final void testMarshalNoHeight() throws Exception {

        // PREPARE
        final FileConfig testee = new FileConfig(".*/abc\\.ddd", 800, null);

        // TEST
        final String result = JaxbUtils.marshal(testee, Code2SvgUtils.JAXB_CLASSES);

        // VERIFY
        final String expected = "<file-config name=\".*/abc\\.ddd\" width=\"800\" />";
        XMLAssert.assertXMLEqual(JaxbUtils.XML_PREFIX + expected, result);

    }

    @Test
    public final void testMarshalNoWidth() throws Exception {

        // PREPARE
        final FileConfig testee = new FileConfig(".*/abc\\.ddd", null, 600);

        // TEST
        final String result = JaxbUtils.marshal(testee, Code2SvgUtils.JAXB_CLASSES);

        // VERIFY
        final String expected = "<file-config name=\".*/abc\\.ddd\" height=\"600\" />";
        XMLAssert.assertXMLEqual(JaxbUtils.XML_PREFIX + expected, result);

    }

    @Test
    public final void testUnmarshal() throws Exception {

        // PREPARE
        final String xml = "<file-config name=\".*/abc\\.ddd\" width=\"800\" height =\"600\" />";

        // TEST
        final FileConfig testee = JaxbUtils.unmarshal(xml, Code2SvgUtils.JAXB_CLASSES);

        // VERIFY
        assertThat(testee).isNotNull();
        assertThat(testee.getName()).isEqualTo(".*/abc\\.ddd");
        assertThat(testee.getWidth()).isEqualTo(800);
        assertThat(testee.getHeight()).isEqualTo(600);

    }

    // CHECKSTYLE:ON

}
