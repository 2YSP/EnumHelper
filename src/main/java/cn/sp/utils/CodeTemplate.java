package cn.sp.utils;

import cn.sp.exception.ShipException;
import cn.sp.model.GenerateMethodInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/26
 */
public class CodeTemplate {
    /**
     * jdk1.8及以上代码模板
     */
    private static Map<Integer, String> codeTemplateMap = new HashMap<>();

    static {
        codeTemplateMap.put(0, "    public static %s getBy%s(%s %s) {\n");
        codeTemplateMap.put(1, "        return Arrays.asList(values()).stream().filter(i -> i.get%s().equals(%s)).findFirst().orElse(null);\n");
        codeTemplateMap.put(2, "    }\n");
    }

    /**
     * jdk1.7及以下代码模板
     */
    private static Map<Integer, String> jdk7CodeTemplateMap = new HashMap<>();

    static {
        jdk7CodeTemplateMap.put(0, "    public static %s getBy%s(%s %s) {\n");
        jdk7CodeTemplateMap.put(1, "        for(%s obj: %s.values()){\n");
        jdk7CodeTemplateMap.put(2, "            if (obj.get%s().equals(%s)){\n");
        jdk7CodeTemplateMap.put(3, "                return obj;\n");
        jdk7CodeTemplateMap.put(4, "            }\n");
        jdk7CodeTemplateMap.put(5, "        }\n");
        jdk7CodeTemplateMap.put(6, "        return null;\n");
        jdk7CodeTemplateMap.put(7, "    }\n");
    }


//    public static TestEnum getByCode(Integer code){
//        for(TestEnum testEnum: TestEnum.values()){
//            if (testEnum.getCode().equals(code)){
//                return testEnum;
//            }
//        }
//        return null;
//    }

    /**
     * @param index 代码行角标
     * @param info
     * @return
     */
    public static String buildCodeLine(int index, GenerateMethodInfo info) {
        switch (index) {
            case 0:
                return String.format(codeTemplateMap.get(index), info.getClassName(), MyUtil.transferToUpCase(info.getFieldName()),
                        info.getFieldType(), info.getFieldName());
            case 1:
                return String.format(codeTemplateMap.get(index), MyUtil.transferToUpCase(info.getFieldName()), info.getFieldName());
            case 2:
                return codeTemplateMap.get(index);
            default:
                throw new ShipException("system error");
        }
    }

    /**
     * @param index
     * @param info
     * @return
     */
    public static String buildCodeLineForJdk7(int index, GenerateMethodInfo info) {
        String baseCode = jdk7CodeTemplateMap.get(index);
        switch (index) {
            case 0:
                return String.format(baseCode, info.getClassName(), MyUtil.transferToUpCase(info.getFieldName()),
                        info.getFieldType(), info.getFieldName());
            case 1:
                return String.format(baseCode, info.getClassName(), info.getClassName());
            case 2:
                return String.format(baseCode, MyUtil.transferToUpCase(info.getFieldName()), info.getFieldName());
            default:
                return baseCode;
        }
    }
}
