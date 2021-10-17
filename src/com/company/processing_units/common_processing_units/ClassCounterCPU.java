package com.company.processing_units.common_processing_units;

import com.company.visitors.TypeDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.List;

public class ClassCounterCPU extends CommonProcessingUnit {

    public ClassCounterCPU() {
        this.description = "Nombres de classes de l'application :";
    }

    public void process() {
        int accumulator = 0;
        for (CompilationUnit unit :
                compilationUnits) {
            TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
            unit.accept(visitor);
            accumulator += visitor.getTypes().size();
        }
        result = String.valueOf(accumulator);
    }
}
