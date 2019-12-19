package org.lucene.research.test;

public enum FieldType {
    stringfield("string"), intfield("int"), floatfield("float"), doublefield("double"), longfield("long"), textfield("text"),
    /**
     * 分组统计
     */
    groupfield("group"),
    /**
     * 排序统计（long类型字段）
     */
    sortlongfield("sortlong"),
    /**
     * 排序统计（double类型字段）
     */
    BinaryDocValuesField("BinaryDocValues"),
    /**
     * 统计（string类型字段）
     */
    sortdoublefield("sortdouble"),
    /**
     * 范围
     */
    polygon("polygon"),
    /**
     * 距离
     */
    distance("distance"),

    geometry("geometry"),

    geohash("geohash"),

    vertex("vertex"),

    sortvertex("sortvertex");

    @SuppressWarnings("unused")
    public final String type;

    public static String convertTypeToValue(String type) {
        for (FieldType fieldType : values()) {
            if (fieldType.type.equals(type)) {
                return fieldType.toString();
            }
        }
        return "";
    }

    FieldType(String type) {
        this.type = type;
    }

    public static void main(String[] args) {
        System.out.println(FieldType.convertTypeToValue("string"));
    }
}
