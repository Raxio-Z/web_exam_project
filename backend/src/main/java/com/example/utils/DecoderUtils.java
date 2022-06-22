package com.example.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DecoderUtils {
    public static List<Integer> decodeIds(String Ids)
    {
        return Stream.of(Ids.split("-")).map(Integer::parseInt).collect(Collectors.toList());
    }

}
