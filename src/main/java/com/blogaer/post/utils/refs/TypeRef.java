package com.blogaer.post.utils.refs;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

public final class TypeRef {

    private TypeRef() {}

    public static final TypeReference<Map<String, Object>> STR_OBJ = new TypeReference<Map<String, Object>>() {};
}
