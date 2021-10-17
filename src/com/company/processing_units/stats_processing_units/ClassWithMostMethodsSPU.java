package com.company.processing_units.stats_processing_units;

import com.company.processing_units.common_processing_units.ClassCounterCPU;
import com.company.visitors.MethodDeclarationVisitor;
import com.company.visitors.TypeDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.*;

public class ClassWithMostMethodsSPU extends StatisticProcessingUnit {
    double percent;
    List<ClassDeclarationSet> order = new ArrayList<ClassDeclarationSet>();

    public ClassWithMostMethodsSPU(double percent) {
        if (percent > 1) {
            this.percent = 1;
        } else if (percent < 0) {
            this.percent = 0;
        } else this.percent = percent;
        description = this.percent * 100 + " % des classes ayant le plus de méthodes :";
    }

    @Override
    protected void process() {
        ClassCounterCPU classCounterCPU = new ClassCounterCPU();
        classCounterCPU.register(compilationUnits);
        int classCount = Integer.parseInt(classCounterCPU.getResult());
        int toKeep = (int) Math.ceil(classCount * percent);

        List<ClassDeclarationSet> sortedClass = new ArrayList<ClassDeclarationSet>();

        for (CompilationUnit unit :
                compilationUnits) {
            TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
            unit.accept(visitor);
            for (TypeDeclaration dec :
                    visitor.getTypes()) {
                MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
                dec.accept(visitor1);
                sortedClass.add(new ClassDeclarationSet(visitor1.getMethods().size(), dec));
            }
        }
        Collections.sort(sortedClass);
        Collections.reverse(sortedClass);
        order = sortedClass;
        result = "\n";
        for (int i = 0; i < toKeep; i++) {
            result += sortedClass.get(i).declaration.getName() + " => " + sortedClass.get(i).methodCount + " méthodes;\n";
        }
    }

    public List<ClassDeclarationSet> getOrder() {
        process();
        return order;
    }

    class ClassDeclarationSet implements Comparable<ClassDeclarationSet> {

        int methodCount;
        TypeDeclaration declaration;

        public ClassDeclarationSet(int methodCount, TypeDeclaration declaration) {
            this.methodCount = methodCount;
            this.declaration = declaration;
        }

        @Override
        public int compareTo(ClassDeclarationSet o) {
            //if (methodCount == o.methodCount) return 0;
            return methodCount - o.methodCount;
        }
    }
}
