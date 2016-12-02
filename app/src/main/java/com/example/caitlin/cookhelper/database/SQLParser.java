package com.example.caitlin.cookhelper.database;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class SQLParser {

    public static String generateSQLQuery(String q, String prefix, Map<String, String> rankArgs)
            throws IllegalArgumentException {

        // This regex tokenizes our String properly (but still needs trimming).
        String tokenizer = "(?=AND)|(?<=AND )|(?=OR)|(?<=OR )|(?=NOT)|(?<=NOT )|(?=\\()|(?<=\\()|(?=\\))|(?<=\\) )";
        String[] elements = q.split(tokenizer);
        for (int i=0; i<elements.length; i++) {
            elements[i] = elements[i].trim();
        }

        // Convert to Postfix expression
        LinkedList<String>postFixTree = convertToPostfix(elements);
        // Create Binary Expression Tree
        Stack<BinaryExpressionTree> stack = getBinaryExpressionTree(postFixTree);

        //Traverse the Expression Tree
        BinaryExpressionTree root = stack.pop();
        rankArgs = leafOrder(root, rankArgs);
        return inOrder(root, prefix);
    }

    public static Stack<BinaryExpressionTree> getBinaryExpressionTree(LinkedList<String> inFixList) {
        BinaryExpressionTree binaryExpressionTree1;
        BinaryExpressionTree binaryExpressionTree2;

        BinaryExpressionTree.Node node1;
        BinaryExpressionTree.Node node2;
        BinaryExpressionTree<String> newBinaryExpressionTree;


        // Now make the BinaryExpressionTree Stack
        Stack<BinaryExpressionTree> stack = new Stack<>();
        while (!inFixList.isEmpty()) {
            String element = inFixList.removeLast();
            if (isBinaryOperator(element)) {
                // Pop two operands
                newBinaryExpressionTree = new BinaryExpressionTree<>(element);
                binaryExpressionTree1 = stack.pop();
                binaryExpressionTree2 = stack.pop();
                node1 = binaryExpressionTree1.getRoot();
                node2 = binaryExpressionTree2.getRoot();

                // Make a new tree with operator at root
                newBinaryExpressionTree.getRoot().setLeft(node1);
                node1.setParent(newBinaryExpressionTree.getRoot());

                newBinaryExpressionTree.getRoot().setRight(node2);
                node2.setParent(newBinaryExpressionTree.getRoot());
                stack.push(newBinaryExpressionTree);

            }
            else if (isUnaryOperator(element)) {
                newBinaryExpressionTree = new BinaryExpressionTree<>(element);
                binaryExpressionTree1 = stack.pop();
                node1 = binaryExpressionTree1.getRoot();

                newBinaryExpressionTree.getRoot().setRight(node1);
                node1.setParent(newBinaryExpressionTree.getRoot());

                stack.push(newBinaryExpressionTree);
            }
            else {
                stack.push(new BinaryExpressionTree<>(element));
            }
        }
        return stack;
    }


    private static LinkedList<String> convertToPostfix(String[] elements) {
        LinkedList<String> operandStack = new LinkedList<>();
        Stack<String> operatorStack = new Stack<>();
        String operator;

        boolean unaryFlag = false;

        /** Constructing the postfix Expression */
        for (String el : elements) {
            if (unaryFlag) {
                operandStack.push(el);
                operandStack.push(operatorStack.pop());
                unaryFlag = false;
            }

            else if (isLeftParens(el)) {
                operatorStack.push(el);
            }
            else if (isRightParens(el)) {
                while(!isLeftParens(operatorStack.peek())) {
                    operator = operatorStack.pop();

                    operandStack.push(operator);
                }
                // remove the left parens from the stack
                operatorStack.pop();
            }

            else if (isUnaryOperator(el)) {
                unaryFlag = true;
                operatorStack.push(el);
            }

            else if (isBinaryOperator(el)) {
                while(!operatorStack.empty() && hasPrecedence(operatorStack.peek())) {
                    operator = operatorStack.pop();
                    operandStack.push(operator);
                }
                operatorStack.push(el);
            }
            else {
                operandStack.push(el);
            }

        }

        while (!operatorStack.empty()) {
            operandStack.push(operatorStack.pop());
        }
        return operandStack;
    }

    private static boolean isBinaryOperator(String s) {
        return (s.equals("AND") || s.equals("OR"));
    }

    private static boolean hasPrecedence(String s1) {
        // TODO Method stub
        return (s1.equals("AND"));
    }

    private static boolean isUnaryOperator(String s) {
        return s.equals("NOT");
    }

    private static boolean isLeftParens(String s) {
        return s.equals("(");
    }

    private static boolean isRightParens(String s) {
        return s.equals(")");
    }


    private static String inOrder(BinaryExpressionTree binaryExpressionTree, String cond) {
        String result = "";
        BinaryExpressionTree.Node root = binaryExpressionTree.getRoot();
        if (root.getLeft() == null && root.getRight() == null) {
            return cond + " LIKE \"%" + root.getElement() + "%\" ";
        }

        return result + inOrder(root, cond);
    }
    private static String inOrder(BinaryExpressionTree.Node node, String cond) {
        String result = "";

        if (node.getParent() != null && isUnaryOperator((String)node.getParent().getElement())) {
            return "\"%" + node.getElement() + "%\"";
        }
        else if (isUnaryOperator((String) node.getElement())) {
            return cond + " NOT LIKE " + inOrder(node.getRight(), cond);
        }
        else if (node.isLeftChild() && node.isLeaf()) {
            result = cond + " LIKE \"%" + node.getElement() + "%\"";
            return result;
        }
        else if (node.isRightChild() && node.isLeaf()) {
            result = cond + " LIKE " + "\"%" + node.getElement() + "%\"";
            return result;
        }
        else {
            result += inOrder(node.getLeft(), cond);
            result += " " + node.getElement() + " ";
            result += inOrder(node.getRight(), cond);
        }
        return result ;
    }

    private static Map<String, String> leafOrder(BinaryExpressionTree binaryExpressionTree,
                                                 Map<String, String> rankArgs) {
        BinaryExpressionTree.Node root = binaryExpressionTree.getRoot();
        if (root.getLeft() == null && root.getRight() == null) {
            rankArgs.put("", "");
        }

        leafOrder(root, rankArgs);
        return rankArgs;
    }
    private static void leafOrder(BinaryExpressionTree.Node node, Map<String, String> rankArgs) {

        if (node.getParent() != null && isUnaryOperator((String)node.getParent().getElement())) {
            return;
        }
        else if ((node.isLeftChild() || node.isRightChild()) && node.isLeaf()) {
            rankArgs.put(""+node.getElement(), ""+node.getElement());
            return;
        }
        else {
            leafOrder(node.getLeft(), rankArgs);
            leafOrder(node.getRight(), rankArgs);
        }
        return;
    }

}


