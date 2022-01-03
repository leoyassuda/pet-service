package com.lny.petservice.domain.model.enums;

import com.lny.petservice.common.error.InvalidOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Domain Size Enum")
@IndicativeSentencesGeneration(separator = " -> ")
class SizeTest {

    private Size size;

    @Test
    @DisplayName("Return each string values of Size Enums")
    void canRetrieveStringValueOfEachSizeEnum() {
        assertAll(() -> {
            assertEquals("Small", Size.SMALL.getValue());
            assertEquals("Medium", Size.MEDIUM.getValue());
            assertEquals("Large", Size.LARGE.getValue());
        });
    }

    @Test
    @DisplayName("Return list of Size Enums and verify total of sizes and each string values")
    void canRetrieveAsStreamOfValuesSizeEnum() {
        List<Size> sizeList = Size.list();

        assertEquals(3, sizeList.size(), "Fail to count total of sizes");
        List<String> stringListToTest = Arrays.asList("small", "medium", "large");
        List<String> stringList = sizeList.stream()
                .map(size1 -> size1.getValue().toLowerCase())
                .collect(Collectors.toList());
        assertEquals(stringListToTest, stringList, "Fail to valid all string size values");
    }

    @Test
    @DisplayName("Return all size values in a simple array")
    void canRetrieveSizeValuesAsSimpleArray() {
        Size[] sizes = Size.values();
        assertEquals(3, sizes.length);
        List<String> stringList = Arrays.stream(sizes).map(size1 -> size1.getValue().toUpperCase())
                .collect(Collectors.toList());
        List<String> stringListToTest = Arrays.asList("SMALL", "MEDIUM", "LARGE");
        assertEquals(stringListToTest, stringList, "Fail to retrieve all string size values");
    }

    @Test
    @DisplayName("Return a valid size enum using valueOf string")
    void canRetrieveASizeEnumWhenValidStringValueOf() {
        Size small = Size.valueOf("SMALL");
        assertEquals(Size.SMALL, small, "Fail to return correct size enum");
    }

    @Test
    @DisplayName("Return a IllegalArgumentException size enum using a wrong valueOf string")
    void canReturnIllegalArgumentExceptionWhenWrongValueOfString() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Size.valueOf("Small"));
        assertEquals("No enum constant com.lny.petservice.domain.model.enums.Size.Small", exception.getMessage());
    }
}