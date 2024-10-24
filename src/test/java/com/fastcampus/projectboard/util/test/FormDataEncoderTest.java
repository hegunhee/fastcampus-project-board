package com.fastcampus.projectboard.util.test;

import com.fastcampus.projectboard.util.FormDataEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("테스트 도구 - Form 데이터 인코더")
@Import({FormDataEncoder.class, ObjectMapper.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Void.class)
class FormDataEncoderTest {

    private final FormDataEncoder formDataEncoder;

    public FormDataEncoderTest(@Autowired FormDataEncoder formDataEncoder) {
        this.formDataEncoder = formDataEncoder;
    }

    @DisplayName("객체를 넣으면, url encoding 된 form body data 형식의 문자열을 돌려준다.")
    @Test
    void givenObject_whenEncoding_thenReturnsFormEncodedString() {
        // Given
        TestObject obj = new TestObject(
                "This 'is' \"test\" string.",
                List.of("hello", "my", "friend").toString().replace(" ", ""),
                String.join(",", "hello", "my", "friend"),
                null,
                1234,
                3.14,
                false,
                BigDecimal.TEN,
                TestEnum.THREE
        );

        // When
        String result = formDataEncoder.encode(obj);

        // Then
        assertThat(result).isEqualTo(
                """
                    str=This%20'is'%20%22test%22%20string.
                    &listStr1=%5Bhello,my,friend%5D
                    &listStr2=hello,my,friend
                    &nullStr
                    &number=1234
                    &floatingNumber=3.14
                    &bool=false
                    &bigDecimal=10
                    &testEnum=THREE
                    """.replace("\n", "") // 자동 줄 띄우기 삭제 만약 인코딩 내용에 줄 띄우기가 포함된다면 replace 대신 다른 방법을 사용해야함
        );
    }

    record TestObject(
            String str,
            String listStr1,
            String listStr2,
            String nullStr,
            Integer number,
            Double floatingNumber,
            Boolean bool,
            BigDecimal bigDecimal,
            TestEnum testEnum
    ) {
    }

    enum TestEnum {
        ONE, TWO, THREE
    }

}
