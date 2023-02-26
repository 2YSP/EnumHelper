package cn.sp.service;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/25
 */
public interface GenerateEnumMethodService {

    /**
     *
     * @param project 当前项目
     * @param dataContext 数据上下文
     * @param psiFile 鼠标所在的当前文件
     */
    void doGenerate(Project project, DataContext dataContext, PsiFile psiFile);
}
