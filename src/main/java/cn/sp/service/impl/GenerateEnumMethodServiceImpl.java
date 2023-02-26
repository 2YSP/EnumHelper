package cn.sp.service.impl;

import cn.sp.exception.ShipException;
import cn.sp.model.GenerateContext;
import cn.sp.model.GenerateMethodInfo;
import cn.sp.service.GenerateEnumMethodService;
import cn.sp.utils.CodeTemplate;
import cn.sp.utils.MyUtil;
import com.intellij.lang.jvm.JvmClassKind;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/25
 */
public class GenerateEnumMethodServiceImpl implements GenerateEnumMethodService {

    @Override
    public void doGenerate(Project project, DataContext dataContext, PsiFile psiFile) {
        GenerateContext generateContext = getGenerateContext(project, dataContext, psiFile);
        GenerateMethodInfo generateMethodInfo = getMethodInfoFromClipboard(generateContext);
        this.generateCode(generateContext, generateMethodInfo);
    }

    private GenerateMethodInfo getMethodInfoFromClipboard(GenerateContext generateContext) {
        String clipboardText = MyUtil.getSystemClipboardText().trim();
        if (clipboardText == null || clipboardText == "") {
            throw new ShipException("please copy enum field first!");
        }
        PsiClass psiClass = generateContext.getPsiClass();
        JvmClassKind classKind = psiClass.getClassKind();
        if (!classKind.equals(JvmClassKind.ENUM)) {
            throw new ShipException("the class must be Enum type!");
        }

        PsiField psiField = getField(psiClass, clipboardText);
        if (psiField == null) {
            throw new ShipException("please check you copy field!");
        }
        GenerateMethodInfo generateMethodInfo = new GenerateMethodInfo();
        generateMethodInfo.setClassName(psiClass.getName());
        generateMethodInfo.setFieldName(psiField.getName());
        generateMethodInfo.setFieldType(psiField.getType().getPresentableText());
        return generateMethodInfo;
    }

    private PsiField getField(PsiClass psiClass, String name) {
        for (PsiField psiField : psiClass.getFields()) {
            if (psiField.getName().equals(name)) {
                return psiField;
            }
        }
        return null;
    }

    /**
     * 生成代码
     *
     * @param generateContext
     */
    private void generateCode(GenerateContext generateContext, GenerateMethodInfo generateMethodInfo) {
        Application application = ApplicationManager.getApplication();
        // 获取空格位置长度
        int distance = 4;
        StringBuilder blankSpace = new StringBuilder();
        for (int i = 0; i < distance; i++) {
            blankSpace.append(" ");
        }
        application.runWriteAction(() -> {
            int currLineNumber = generateContext.getLineNumber();
            for (int i = 0; i < 3; i++) {
                // 每一行的起始位置offset
                int lineStartOffset = generateContext.getDocument().getLineStartOffset(currLineNumber++);
                String codeLine = CodeTemplate.buildCodeLine(blankSpace.toString(), i, generateMethodInfo);
                WriteCommandAction.runWriteCommandAction(generateContext.getProject(), () -> {
                    generateContext.getDocument().insertString(lineStartOffset, codeLine);
                    generateContext.getEditor().getCaretModel().moveToOffset(lineStartOffset + 2);
                    generateContext.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
                });
            }
        });
    }


    /**
     * 获取上下文信息
     *
     * @param project
     * @param dataContext
     * @param psiFile
     * @return
     */
    private GenerateContext getGenerateContext(Project project, DataContext dataContext, PsiFile psiFile) {
        GenerateContext generateContext = new GenerateContext();
        generateContext.setProject(project);
        generateContext.setDataContext(dataContext);
        generateContext.setPsiFile(psiFile);

        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        PsiElement psiElement = CommonDataKeys.PSI_ELEMENT.getData(dataContext);
        Document document = editor.getDocument();
        generateContext.setEditor(editor);
        generateContext.setDocument(document);
        generateContext.setPsiElement(psiElement);
        // 编辑器的位置
        generateContext.setOffset(editor.getCaretModel().getOffset());
        // 编辑器所在行
        generateContext.setLineNumber(document.getLineNumber(generateContext.getOffset()));
        generateContext.setStartOffset(document.getLineStartOffset(generateContext.getLineNumber()));
        generateContext.setEditorText(document.getCharsSequence());

        String clazzName = psiFile.getName().replace(".java", "");
        // 获取类
        PsiClass[] psiClasses = PsiShortNamesCache.getInstance(generateContext.getProject()).getClassesByName(clazzName, GlobalSearchScope.projectScope(generateContext.getProject()));
        generateContext.setPsiClass(psiClasses[0]);
        return generateContext;
    }

}
