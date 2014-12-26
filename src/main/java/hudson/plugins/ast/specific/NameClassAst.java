package hudson.plugins.ast.specific;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Document type NameClassAst.
 *
 * @author Christian M&ouml;stl
 */
public class NameClassAst extends Ast {

    /**
     * Creates a new instance of {@link NameClassAst}.
     * @param filename
     * @param fileAnnotation
     */
    public NameClassAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
        // FIXME Auto-generated constructor stub
    }

    @Override
    public List<DetailAST> chooseArea() {
        // FIXME Auto-generated method stub
        return null;
    }

}

