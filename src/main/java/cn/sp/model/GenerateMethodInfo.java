package cn.sp.model;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/25
 */
public class GenerateMethodInfo {

    /**
     * 类名
     */
    private String className;
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 属性类型，如Integer
     */
    private String fieldType;

    private Boolean invalidCall = false;

    public Boolean getInvalidCall() {
        return invalidCall;
    }

    public void setInvalidCall(Boolean invalidCall) {
        this.invalidCall = invalidCall;
    }



    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
