package org.fuin.code2svg.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import javax.json.JsonObject;

import org.junit.Test;

public class ModelConfigParserTest {

    @Test
    public void testParseTag() {

        // PREPARE
        final RegExprElement el = new RegExprElement("string", "fill: rgb(42, 0, 255)", "\".*?\"");
        final Code2SvgConfig config = new Code2SvgConfig.Builder().fileExtension(".ddd").width(1).height(2).addElement(el).build();
        final ModelConfigParser testee = new ModelConfigParser(config);

        // TEST
        final Code2SvgConfig result = testee.parse(null, ModelConfigParser.CODE2SVG_KEY + "{\"width\": 800, \"height\": 1000}");

        // VERIFY
        assertThat(result.getFileExtension()).isEqualTo(".ddd");
        assertThat(result.getWidth()).isEqualTo(800);
        assertThat(result.getHeight()).isEqualTo(1000);
        assertThat(result.getElements()).containsOnly(el);

    }

    @Test
    public void testParseFileConfig() {

        // PREPARE
        final RegExprElement el = new RegExprElement("string", "fill: rgb(42, 0, 255)", "\".*?\"");
        final Code2SvgConfig config = new Code2SvgConfig.Builder().fileExtension(".ddd").width(1).height(2).addElement(el)
                .addFileConfig(new FileConfig(".*/abc\\.ddd", 800, 1000)).build();
        final ModelConfigParser testee = new ModelConfigParser(config);

        // TEST
        final Code2SvgConfig result = testee.parse(new File("/tmp/abc.ddd"), "");

        // VERIFY
        assertThat(result.getFileExtension()).isEqualTo(".ddd");
        assertThat(result.getWidth()).isEqualTo(800);
        assertThat(result.getHeight()).isEqualTo(1000);
        assertThat(result.getElements()).containsOnly(el);

    }

    @Test
    public void testExtractJson() {

        // PREPARE
        final String json = "{\"width\": 800, \"height\": 1000}";

        // TEST & VERIFY
        assertThat(ModelConfigParser.extractJson(ModelConfigParser.CODE2SVG_KEY + json)).isEqualTo(json);

    }

    @Test
    public void testParseJson() {

        // PREPARE
        final String json = "{\"width\": 800, \"height\": 1000}";

        // TEST
        final JsonObject jsonObj = ModelConfigParser.parseJson(json);

        // VERIFY
        assertThat(jsonObj.getInt("width")).isEqualTo(800);
        assertThat(jsonObj.getInt("height")).isEqualTo(1000);
        assertThat(jsonObj.get("notexisting")).isNull();

    }

}
