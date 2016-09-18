package com.networknt.validator.parameter;

import com.networknt.validator.report.MessageResolver;
import org.junit.Test;

import static com.networknt.validator.ValidatorTestUtil.assertFail;
import static com.networknt.validator.ValidatorTestUtil.assertPass;
import static com.networknt.validator.ValidatorTestUtil.intParam;

public class IntegerParameterValidatorTest {

    private IntegerParameterValidator classUnderTest = new IntegerParameterValidator(new MessageResolver());

    @Test
    public void validate_withNullValue_shouldPass_whenNotRequired() {
        assertPass(classUnderTest.validate(null, intParam(false)));
    }

    @Test
    public void validate_withEmptyValue_shouldPass_whenNotRequired() {
        assertPass(classUnderTest.validate("", intParam(false)));
    }

    @Test
    public void validate_withNullValue_shouldFail_whenRequired() {
        assertFail(classUnderTest.validate(null, intParam(true)), "validation.request.parameter.missing");
    }

    @Test
    public void validate_withEmptyValue_shouldFail_whenRequired() {
        assertFail(classUnderTest.validate("", intParam(true)), "validation.request.parameter.missing");
    }

    @Test
    public void validate_withNonNumericValue_shouldFail() {
        assertFail(classUnderTest.validate("123a", intParam()), "validation.request.parameter.invalidFormat");
    }

    @Test
    public void validate_withNonIntegerValue_shouldFail() {
        assertFail(classUnderTest.validate("123.1", intParam()), "validation.request.parameter.invalidFormat");
    }

    @Test
    public void validate_withIntegerValue_shouldPass() {
        assertPass(classUnderTest.validate("123", intParam()));
    }

    @Test
    public void validate_withValueGreaterThanMax_shouldFail_ifMaxSpecified() {
        assertFail(classUnderTest.validate("2", intParam(null, 1.0)), "validation.request.parameter.number.aboveMax");
    }

    @Test
    public void validate_withValueLessThanMin_shouldFail_ifMinSpecified() {
        assertFail(classUnderTest.validate("0", intParam(1.0, null)), "validation.request.parameter.number.belowMin");
    }

    @Test
    public void validate_withValueInRange_shouldPass() {
        assertPass(classUnderTest.validate("2", intParam(1.0, 3.0)));
    }

}