package com.company;

import com.company.processing_units.common_processing_units.*;
import com.company.processing_units.stats_processing_units.ClassWithMinMethodsSPU;
import com.company.processing_units.stats_processing_units.ClassWithMostAttrAndMethodsSPU;
import com.company.processing_units.stats_processing_units.ClassWithMostAttributesSPU;
import com.company.processing_units.stats_processing_units.ClassWithMostMethodsSPU;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        //ICI préciser le path du projet à analyser et le path du jre
        Processor processor = new Processor("C:\\Users\\Utilisateur\\IdeaProjects\\TP_Sac_a_dos", "C:\\Program Files\\Java\\jdk-11.0.2\\lib\\jrt-fs.jar");

        //Ajouter les processor selon les données désirées
        processor.addProcessingUnit(new ClassCounterCPU());
        processor.addProcessingUnit(new LineCounterCPU());
        processor.addProcessingUnit(new MethodCounterCPU());
        processor.addProcessingUnit(new PackageCounterProcessor());
        processor.addProcessingUnit(new AverageMethPerClassCPU());
        processor.addProcessingUnit(new AveragLineCountPerMethodCPU());
        processor.addProcessingUnit(new AverageAttributCountPerClassCPU());
        processor.addProcessingUnit(new ClassWithMostMethodsSPU(0.10));
        processor.addProcessingUnit(new ClassWithMostAttributesSPU(0.10));
        processor.addProcessingUnit(new ClassWithMostAttrAndMethodsSPU(0.10));
        processor.addProcessingUnit(new ClassWithMinMethodsSPU(2));
        processor.addProcessingUnit(new CallGraphConstructor());
        //Permet d'obtenir tous les résultats sous forme de String
        List<String> results = processor.getResults();
        for (String res : results) {
            System.out.println(res + "\n");
        }

    }

}
