package com.vilim.leapwise.utils;

import com.vilim.leapwise.model.tree.ExpressionNode;

@SuppressWarnings("checkstyle:MagicNumber")
public final class ParserUtil {

    private static int i = 0;

    private ParserUtil() {
    }

    public static ExpressionNode getRootNode(final String expression) {
        i = 0;
        ExpressionNode root = parseExpression(expression);
        ExpressionNode parent = root;
        while (i < expression.length()) {
            parent.setChildRight(parseExpression(expression));
            parent = parent.getChildRight();
        }
        return root;
    }

    private static ExpressionNode parseExpression(final String expression) {
        int length = expression.length();
        boolean isString = false;
        char quote = 0;
        StringBuilder currentPart = new StringBuilder();

        while (i < length) {
            char c = expression.charAt(i);

            if (c == '"' && !isString) {
                isString = true;
                quote = c;
            } else if (c == quote && isString) {
                isString = false;
            }

            if (!isString) {
                if (expression.startsWith("AND", i) || expression.startsWith("and", i)) {
                    i += 3;
                    return createNode(currentPart, "AND");
                } else if (expression.startsWith("&&", i)) {
                    i += 2;
                    return createNode(currentPart, "AND");
                } else if (expression.startsWith("OR", i)
                        || expression.startsWith("or", i)
                        || expression.startsWith("||", i)) {
                    i += 2;
                    return createNode(currentPart, "OR");
                }
            }

            currentPart.append(c);
            i++;
        }

        return createNode(currentPart, null);
    }

    private static ExpressionNode createNode(final StringBuilder currentPart, final String parent) {
        String value = currentPart.toString().trim();
        if (!value.isEmpty()) {
            currentPart.setLength(0);
            if (parent == null) {
                ExpressionNode rightNode = new ExpressionNode();
                fillNode(rightNode, value);
                return rightNode;
            }
            ExpressionNode parentNode = new ExpressionNode();
            parentNode.setLogicalOperator(parent);
            ExpressionNode leafNode = new ExpressionNode();
            fillNode(leafNode, value);
            parentNode.setChildLeft(leafNode);
            return parentNode;
        }
        throw new IllegalStateException("Part is empty");
    }

    private static void fillNode(final ExpressionNode node, final String value) {
        int length = value.length();
        int i = 0;

        while (i < length) {
            char c = value.charAt(i);

            if (i + 1 < length) {
                String twoChar = value.substring(i, i + 2);
                if (twoChar.equals("==") || twoChar.equals("!=") || twoChar.equals("<=") || twoChar.equals(">=")) {
                    String[] split = value.split(twoChar);
                    node.setRoValue1(split[0].trim().replace("\"", ""));
                    node.setRelationalOperator(twoChar);
                    node.setRoValue2(split[1].trim().replace("\"", ""));
                    break;
                }
            }

            if (c == '<' || c == '>') {
                String[] split = value.split(String.valueOf(c));
                node.setRoValue1(split[0].trim().replace("\"", ""));
                node.setRelationalOperator(String.valueOf(c));
                node.setRoValue2(split[1].trim().replace("\"", ""));
                break;
            }

            i++;
        }
    }
}
