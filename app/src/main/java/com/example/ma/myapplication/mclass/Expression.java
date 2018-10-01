package com.example.ma.myapplication.mclass;

import java.util.Arrays;
import java.util.Stack;

public class Expression {
    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MULTI = '×';
    private static final char DIV = '÷';
    private static final String SPACE = " ";
    private static final char OPEN_BRACKETS = '(';
    private static final char CLOSE_BRACKETS = ')';
    private static Expression expression = new Expression();

    private int priority(char c) {
        if (c == ADD || c == SUB) return 1;
        else if (c == MULTI || c == DIV) return 2;
        else return 0;
    }

    private boolean isOperator(char c) {
        char operator[] = {ADD, SUB, MULTI, DIV, OPEN_BRACKETS, CLOSE_BRACKETS};
        Arrays.sort(operator);
        return (Arrays.binarySearch(operator, c) > -1);
    }

    private boolean checkOperator(char c, char operator) {
        return (c == operator);
    }

    private String[] processString(String sMath) {
        String s = "";
        sMath = sMath.trim().replaceAll("\\s+", SPACE);
        if (expression.isOperator(sMath.charAt(sMath.length() - 1))
                && !expression.checkOperator(CLOSE_BRACKETS, sMath.charAt(sMath.length()-1))
                && expression.checkOperator(CLOSE_BRACKETS, sMath.charAt(sMath.length()-2))) {
            sMath = sMath.substring(0, sMath.length()-2);
        }
        if (expression.isOperator(sMath.charAt(0))
                && !expression.checkOperator(OPEN_BRACKETS, sMath.charAt(0))
                && expression.checkOperator(OPEN_BRACKETS, sMath.charAt(1))) {
            sMath= MyKey.ZERO.concat(sMath);
        }
        for (int i = 0; i < sMath.length(); i++) {
            char c = sMath.charAt(i);
            if (!expression.isOperator(c)) {
                s = s.concat(Character.toString(c));
            } else {
                s = s.concat(SPACE).concat(Character.toString(c)).concat(SPACE);
            }
        }
        s = s.trim().replaceAll("\\s+", SPACE);
        return s.split(SPACE);
    }

    private String[] changeInfixToPostfix(String[] elementMath) {
        String s = "";
        Stack<String> strings = new Stack<>();
        for (String element : elementMath) {
            char c = element.charAt(0);
            if (!expression.isOperator(c)) {
                s = s.concat(SPACE).concat(element);
            } else {
                if (checkOperator(c, OPEN_BRACKETS))
                    strings.push(element);
                else {
                    if (checkOperator(c, CLOSE_BRACKETS)) {
                        char c1;
                        do {
                            c1 = strings.peek().charAt(0);
                            if (!checkOperator(c1, OPEN_BRACKETS)) {
                                s = s.concat(SPACE).concat(strings.peek());
                            }
                            strings.pop();
                        } while (!checkOperator(c1, OPEN_BRACKETS));
                    } else {
                        while (!strings.isEmpty()
                                && expression.priority(strings.peek().charAt(0)) >= expression.priority(c)) {
                            s = s.concat(SPACE).concat(strings.peek());
                            strings.pop();
                        }
                        strings.push(element);
                    }
                }
            }
        }
        while (!strings.isEmpty()) {
            s = s.concat(SPACE).concat(strings.peek());
            strings.pop();
        }
        return s.split(SPACE);
    }

    private Double calculatorValue(String e[]) {
        Stack<Double> s = new Stack<>();
        for (String element : e) {
            if (element.equals(Character.toString(ADD))) {
                s.push(s.pop() + s.pop());
            } else if (element.equals(Character.toString(SUB))) {
                s.push(-s.pop() + s.pop());
            } else if (element.equals(Character.toString(MULTI))) {
                s.push(s.pop() * s.pop());
            } else if (element.equals(Character.toString(DIV))) {
                s.push((1 / s.pop()) * s.pop());
            } else if (!element.equals(MyKey.NULL)) {
                s.push(Double.parseDouble(element));
            }
        }
        return s.pop();
    }
    public Double getValue(String sMath){
        return expression.calculatorValue(expression
                            .changeInfixToPostfix(expression.processString(sMath)));
    }
}
