package cn.sp.utils;

import cn.sp.exception.ShipException;
import cn.sp.model.GenerateContext;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiJavaFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/25
 */
public class MyUtil {

    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    public static String transferToUpCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母转小写
     *
     * @param str
     * @return
     */
    public static String transferToLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

//    public static void main(String[] args) {
//        String str = "code";
//        System.out.println(MyUtil.transferToUpCase(str));
//    }

    /**
     * 获取剪切板数据，也就是 Ctrl + C
     */
    public static String getSystemClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (null == clipTf) {
            return ret;
        }

        // 检查内容是否是文本类型
        if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ret;
    }


    public static int getWordStartOffset(CharSequence editorText, int cursorOffset) {
        if (editorText.length() == 0) {
            return 0;
        }

        if (cursorOffset > 0 && !Character.isJavaIdentifierPart(editorText.charAt(cursorOffset))
                && Character.isJavaIdentifierPart(editorText.charAt(cursorOffset - 1))) {
            cursorOffset--;
        }

        if (Character.isJavaIdentifierPart(editorText.charAt(cursorOffset))) {
            int start = cursorOffset;
            int end = cursorOffset;

            // 定位开始位置
            while (start > 0 && Character.isJavaIdentifierPart(editorText.charAt(start - 1))) {
                start--;
            }
            return start;

        }

        return 0;

    }

    /**
     * jdk版本是至少1.8
     *
     * @return
     */
    public static boolean isJava8OrHigher(GenerateContext generateContext) {
        if (!(generateContext.getPsiFile() instanceof PsiJavaFile)) {
            throw new ShipException("system error");
        }
        PsiJavaFile psiJavaFile = (PsiJavaFile) generateContext.getPsiFile();
        boolean isJava8OrHigher = psiJavaFile.getLanguageLevel().isAtLeast(LanguageLevel.JDK_1_8);
        return isJava8OrHigher;
    }


}
