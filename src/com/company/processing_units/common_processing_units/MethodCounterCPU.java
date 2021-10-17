package com.company.processing_units.common_processing_units;

import com.company.visitors.MethodDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class MethodCounterCPU extends CommonProcessingUnit {

    public MethodCounterCPU() {
        this.description = "Nombre de méthodes :";
    }

    @Override
    protected void process() {
        int accumulator = 0;
        for (CompilationUnit unit : compilationUnits
        ) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            unit.accept(visitor);
            accumulator += visitor.getMethods().size();
        }
        result = String.valueOf(accumulator);
    }
}
