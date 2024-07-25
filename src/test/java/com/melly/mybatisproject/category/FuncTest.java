package com.melly.mybatisproject.category;

import org.junit.jupiter.api.Test;
public class FuncTest {
    @Test
    public void funcTest() {
        Integer num = 1;
        Long lNum = 10L;
        Character ch = 20;

        System.out.println(String.format("funcTest : num=%d, lNum=%d, ch=%d"
                , num, lNum, (int)ch));
        long result = this.callByValueFunc(num, lNum, ch);
        System.out.println(String.format("funcTest : num=%d, lNum=%d, ch=%d, result=%d"
                , num, lNum, (int)ch, result));

        CategoryDto dto = CategoryDto.builder().id(30L).name("dto").build();
        System.out.println(String.format("funcTest : id=%d, name=%s"
                , dto.getId(), dto.getName()));
        this.callByReferenceFunc(dto);
        System.out.println(String.format("funcTest : id=%d, name=%s"
                , dto.getId(), dto.getName()));
    }

    private long callByValueFunc(int num, long lNum, char ch) {
        long result = num + lNum + ch;
        num = 100;
        lNum = 1000L;
        ch = 'A';
        System.out.println(String.format("callByValueFunc : num=%d, lNum=%d, ch=%d, result=%d"
                , num, lNum, (int)ch, result));
        return result;
    }

    private void callByReferenceFunc(CategoryDto dto) {
        dto.setId(70L);
        dto.setName("update");
        System.out.println(String.format("callByReferenceFunc : id=%d, name=%s"
                , dto.getId(), dto.getName()));
    }
}
