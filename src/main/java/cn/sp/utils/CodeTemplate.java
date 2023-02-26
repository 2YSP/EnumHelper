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

    private static Map<Integer, String> codeTemplateMap = new HashMap<>();

    static {
        codeTemplateMap.put(0, "public static %s getBy%s(%s %s) {\n");
        codeTemplateMap.put(1, "return Arrays.asList(values()).stream().filter(i -> i.get%s().equals(%s)).findFirst().orElse(null);\n");
        codeTemplateMap.put(2, "}\n");
    }

    public static String buildCodeLine(String blankSpace, int index, GenerateMethodInfo info) {
        if (index == 0) {
            return blankSpace + String.format(codeTemplateMap.get(index), info.getClassName(), MyUtil.transferToUpCase(info.getFieldName()),
                    info.getFieldType(), info.getFieldName());
        }
        if (index == 1) {
            return blankSpace + blankSpace + String.format(codeTemplateMap.get(index), MyUtil.transferToUpCase(info.getFieldName()), info.getFieldName());
        }
        if (index == 2) {
            return blankSpace + codeTemplateMap.get(index);
        }
        throw new ShipException("system error");
    }
}
