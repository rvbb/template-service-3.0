package com.rvbb.food.template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SqlOperationName {
    EQUALS,
    GREATER,
    LESS,
    GREATEROREQUALS,
    LESSOREQUALS,
    LIKE,
    NOTLINE,
    IN,
    NOTIN,
    LIKELEFT,
    LIKERIGHT,
    EXISTS;

    public static String asString() {
        return EQUALS.toString()
                + ", " + GREATER.toString()
                + ", " + LESS.toString()
                + ", " + GREATEROREQUALS.toString()
                + ", " + LESSOREQUALS.toString()
                + ", " + LIKE.toString();
    }

    public static boolean contains(String enumAsString){
        for(SqlOperationName item : SqlOperationName.values()){
            if(item.toString().equalsIgnoreCase(enumAsString)){
                return true;
            }
        }
        return false;
    }
}
