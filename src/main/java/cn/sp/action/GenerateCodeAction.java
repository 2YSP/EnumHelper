package cn.sp.action;

import cn.sp.exception.ShipException;
import cn.sp.service.GenerateEnumMethodService;
import cn.sp.service.impl.GenerateEnumMethodServiceImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/25
 */
public class GenerateCodeAction extends AnAction {

    private GenerateEnumMethodService GenerateEnumMethodService = new GenerateEnumMethodServiceImpl();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        try {
            GenerateEnumMethodService.doGenerate(project, e.getDataContext(), psiFile);
        } catch (ShipException shipException) {
            Messages.showErrorDialog(project, shipException.getMessage(), "Operation Error");
        } catch (Exception exception) {
            Messages.showErrorDialog(project, "system error", "System Error");
        }
    }
}
