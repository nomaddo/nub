package com.github.kmizu.nub;

import java.util.HashMap;
import java.util.Map;

public class Evaluator implements AstNode.ExpressionVisitor<Integer> {
    private Map<String, Integer> environment = new HashMap<>();

    public Integer visitBinaryOperation(AstNode.BinaryOperation node) {
        switch (node.operator()) {
            case "+":
                return node.lhs().accept(this) + node.rhs().accept(this);
            case "-":
                return node.lhs().accept(this) - node.rhs().accept(this);
            case "*":
                return node.lhs().accept(this) * node.rhs().accept(this);
            case "/":
                return node.lhs().accept(this) / node.rhs().accept(this);
            case "<=":
                return (node.lhs().accept(this) <= node.rhs().accept(this)) ? 1 : 0;
            case ">=":
                return (node.lhs().accept(this) >= node.rhs().accept(this)) ? 1 : 0;
            case "<":
                return (node.lhs().accept(this) < node.rhs().accept(this)) ? 1 : 0;
            case ">":
                return (node.lhs().accept(this) > node.rhs().accept(this)) ? 1 : 0;
            case "==":
                return (node.lhs().accept(this) == node.rhs().accept(this)) ? 1 : 0;
            case "!=":
                return (node.lhs().accept(this) != node.rhs().accept(this)) ? 1 : 0;
            case "&&": {
                Integer value1 = node.lhs().accept(this);
                return value1 == 0 ? 0 : node.rhs().accept(this);
            }
            case "||": {
                Integer value1 = node.lhs().accept(this);
                return value1 != 0 ? value1 : node.rhs().accept(this);
            }
            default:
                throw new RuntimeException("cannot reach here");
        }
    }

    @Override
    public Integer visitNumber(AstNode.Number node) {
        return node.value();
    }

    @Override
    public Integer visitLetExpression(AstNode.LetExpression node) {
        Integer value = node.expression().accept(this);
        if(environment.containsKey(node.variableName())) {
            throw new NubRuntimeException("variable " + node.variableName() + " is already defined");
        }
        environment.put(node.variableName(), value);
        return value;
    }

    @Override
    public Integer visitAssignmentOperation(AstNode.AssignmentOperation node) {
        Integer value = node.expression().accept(this);
        environment.put(node.variableName(), value);
        return value;
    }

    @Override
    public Integer visitPrintExpression(AstNode.PrintExpression node) {
        Integer value = node.target().accept(this);
        System.out.println(value);
        return value;
    }

    @Override
    public Integer visitExpressionList(AstNode.ExpressionList node) {
        Integer last = 0;
        for (AstNode.Expression e : node.expressions()) {
            last = e.accept(this);
        }
        return last;
    }

    public Integer visitForExpression(AstNode.ForExpression node) {
        Integer last = 0;
        Integer i = node.init.accept(this);
        while (true){
            environment.put (node.variable, i);
            if (i > node.term.accept(this))
                break;

            for(AstNode.Expression e:node.body){
                e.accept(this);
            }

            i++;
        }

        return last;
    }

    public Integer visitWhileExpression(AstNode.WhileExpression node) {
        Integer last = 0;
        while(node.condition().accept(this) != 0) {
            for(AstNode.Expression e:node.body()) {
                last = e.accept(this);
            }
        }
        return last;
    }

    @Override
    public Integer visitIfExpression(AstNode.IfExpression node) {
        Integer condition = node.condition().accept(this);
        Integer last = 0;
        if (condition != 0) {
            for (AstNode.Expression e : node.thenClause()) {
                last = e.accept(this);
            }
        } else {
            for (AstNode.Expression e : node.elseClause()) {
                last = e.accept(this);
            }
        }
        return last;
    }

    @Override
    public Integer visitIdentifier(AstNode.Identifier node) {
        return environment.get(node.name());
    }
}
