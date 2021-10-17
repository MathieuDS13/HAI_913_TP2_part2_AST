package com.company.processing_units.common_processing_units;

import com.company.processing_units.ProcessingUnit;
import com.company.visitors.MethodDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.List;

public class AveragLineCountPerMethodCPU extends ProcessingUnit {


    public AveragLineCountPerMethodCPU() {
        this.description = "Nombre moyen de lignes de code par méthode :";
    }

    @Override
    protected void process() {
        double methodLineAcc = 0, methodCount = 0;

        for (CompilationUnit unit :
                compilationUnits) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            unit.accept(visitor);
            List<MethodDeclaration> methods = visitor.getMethods();
            methodCount += methods.size();
            for (MethodDeclaration method :
                    methods) {
                int startLineNumber = unit.getLineNumber(method.getStartPosition()) - 1;
                int nodeLength = method.getLength();
                int endLineNumber = unit.getLineNumber(method.getStartPosition() + nodeLength) - 1;
                methodLineAcc += (endLineNumber - startLineNumber);
            }
        }

        result = methodCount == 0 ? "N/A" : String.valueOf(methodLineAcc / methodCount);
    }
}
