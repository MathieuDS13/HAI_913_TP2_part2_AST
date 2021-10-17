package com.company.processing_units.common_processing_units;

import com.company.visitors.PackageDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class PackageCounterProcessor extends CommonProcessingUnit{

    public PackageCounterProcessor() {
        this.description = "Nombre de packages : ";
    }

    @Override
    protected void process() {
        int accumulator = 0;
        for (CompilationUnit unit :
                compilationUnits) {
            PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
            unit.accept(visitor);
            accumulator += visitor.getPackages().size();
        }
        result = String.valueOf(accumulator);
    }
}
