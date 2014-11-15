package hudson.plugins.ast.specific;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Is responsible for methods and constructors.
 *
 * @author Christian M&ouml;stl
 */
public class MethodAst extends Ast {

    /**
     * Creates a new instance of {@link MethodAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public MethodAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        List<DetailAST> elementsInLine = getElementsInSameLine();
        DetailAST methodStart = getRootOfMethod(elementsInLine.get(0));

        List<DetailAST> chosenArea = new ArrayList<DetailAST>();
        chosenArea.add(methodStart);
        chosenArea.addAll(calcAllChildren(methodStart.getFirstChild()));
        return chosenArea;
    }

    private DetailAST getRootOfMethod(final DetailAST elementInMethod) {
        if (elementInMethod.getType() == TokenTypes.METHOD_DEF || elementInMethod.getType() == TokenTypes.CTOR_DEF) {
            return elementInMethod;
        }
        else {
            return getRootOfMethod(elementInMethod.getParent());
        }
    }
}