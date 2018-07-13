package org.fuin.code2svg.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;

public class PieceOfTextTest {

    @Test
    public void testPieceOfText() {
        
        final PojoClass pc = PojoClassFactory.getPojoClass(PieceOfText.class);
        final ValidatorBuilder pv = ValidatorBuilder.create();

        pv.with(new NoPublicFieldsRule());
        pv.with(new NoFieldShadowingRule());

        pv.with(new DefaultValuesNullTester());
        pv.with(new GetterTester());

        pv.build().validate(pc);
        
    }

    @Test
    public void testOverlaps() {
        
        // PREPARE
        final PieceOfText testee = new PieceOfText("345", 3, 5);
        
        // TEST & VERIFY
        assertThat(testee.overlaps(0, 2)).as("before").isFalse();
        assertThat(testee.overlaps(6, 8)).as("after").isFalse();
        assertThat(testee.overlaps(4, 4)).as("inside").isTrue();
        assertThat(testee.overlaps(3, 4)).as("left inside").isTrue();
        assertThat(testee.overlaps(4, 5)).as("right inside").isTrue();
        assertThat(testee.overlaps(2, 3)).as("left overlap").isTrue();
        assertThat(testee.overlaps(5, 6)).as("right overlap").isTrue();
        
        
    }

}
