package com.company.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;

public class LineVisitor extends ASTVisitor {
    @Override
    public boolean visit(ExpressionStatement node) {
        return super.visit(node);
    }
}
