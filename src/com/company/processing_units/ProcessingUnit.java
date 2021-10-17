package com.company.processing_units;

import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.List;

public abstract class ProcessingUnit {

    protected String description;
    protected String result;
    protected List<CompilationUnit> compilationUnits;

    public String getVerboseResult(){
        process();
        return description + " " + result;
    }

    public String getResult() {
        process();
        return this.result;
    }

    protected abstract void process();

    public void register(List<CompilationUnit> compilationUnits){
        this.compilationUnits = compilationUnits;
    }
}
