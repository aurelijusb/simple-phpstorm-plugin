package lt.banelis.aurelijus.simpleplugin;

import com.btr.proxy.util.Logger;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocReturnTag;
import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TraitLikeCompletion extends CompletionContributor {
    private final static ArrayDescriptionParser parser = new ArrayDescriptionParser();

    public TraitLikeCompletion() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(PhpLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        PsiElement parameter = parameters.getPosition();
                        ArrayAccessExpression arrayAccess = PsiTreeUtil.getParentOfType(parameter, ArrayAccessExpression.class);
                        if (arrayAccess == null) { return; }

                        FunctionReference[] references = PsiTreeUtil.getChildrenOfType(arrayAccess, FunctionReference.class);
                        if (references == null || references.length < 1) { return; }

                        FunctionReference reference = references[0];
                        Function function = (Function) reference.resolve();
                        if (function == null) { return; }

                        PhpDocComment comment = function.getDocComment();
                        if (comment == null) { return; }

                        PhpDocReturnTag returnTag = comment.getReturnTag();
                        if (returnTag == null) { return; }

                        // functionName()['complete...']
                        addArrayKeyCompletions(resultSet, returnTag.getTagValue());
                    }
                }
        );
    }

    private void addArrayKeyCompletions(@NotNull CompletionResultSet resultSet, String description) {
        Map<String, String> keysValues = parser.parse(description);
        for (String key : keysValues.keySet()) {
            resultSet.addElement(LookupElementBuilder.create(key));
        }

    }
}
