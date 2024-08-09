package com.vilim.leapwise.utils;

import com.vilim.leapwise.model.tree.ExpressionNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParserUtilTest {

    @Test
    void getRootNode_withSimpleAndExpression() {
        String expression = "customer.firstName == \"JOHN\" && customer.salary < 900";

        // testing parent
        ExpressionNode root = ParserUtil.getRootNode(expression);
        assertNotNull(root);
        assertEquals("AND", root.getLogicalOperator());

        // testing children
        ExpressionNode leftNode = root.getChildLeft();
        ExpressionNode rightNode = root.getChildRight();

        assertEquals("customer.firstName", leftNode.getRoValue1());
        assertEquals("JOHN", leftNode.getRoValue2());
        assertEquals("==", leftNode.getRelationalOperator());

        assertEquals("customer.salary", rightNode.getRoValue1());
        assertEquals("900", rightNode.getRoValue2());
        assertEquals("<", rightNode.getRelationalOperator());
    }

    @Test
    void getRootNode_withSimpleOrExpression() {
        String expression = "\"Chicago\" == customer.address.city || \"TERRY\" != customer.lastName";

        // testing parent
        ExpressionNode root = ParserUtil.getRootNode(expression);
        assertNotNull(root);
        assertEquals("OR", root.getLogicalOperator());

        // testing children
        ExpressionNode leftNode = root.getChildLeft();
        ExpressionNode rightNode = root.getChildRight();

        assertEquals("Chicago", leftNode.getRoValue1());
        assertEquals("customer.address.city", leftNode.getRoValue2());
        assertEquals("==", leftNode.getRelationalOperator());

        assertEquals("TERRY", rightNode.getRoValue1());
        assertEquals("customer.lastName", rightNode.getRoValue2());
        assertEquals("!=", rightNode.getRelationalOperator());
    }

    @Test
    void getRootNode_withComplexExpression() {
        String expression = "customer.firstName == \"JOHN\" && customer.salary < 900 "
                + "OR customer.address.zipCode != null && customer.address.city == \"Chicago\"";

        // testing parent
        ExpressionNode root = ParserUtil.getRootNode(expression);
        assertNotNull(root);
        assertEquals("AND", root.getLogicalOperator());

        // testing children
        ExpressionNode leftNode1 = root.getChildLeft();
        ExpressionNode rightNode1 = root.getChildRight();

        assertEquals("customer.firstName", leftNode1.getRoValue1());
        assertEquals("JOHN", leftNode1.getRoValue2());
        assertEquals("==", leftNode1.getRelationalOperator());
        assertEquals("OR", rightNode1.getLogicalOperator());

        ExpressionNode leftNode2 = rightNode1.getChildLeft();
        ExpressionNode rightNode2 = rightNode1.getChildRight();

        assertEquals("customer.salary", leftNode2.getRoValue1());
        assertEquals("900", leftNode2.getRoValue2());
        assertEquals("<", leftNode2.getRelationalOperator());
        assertEquals("AND", rightNode2.getLogicalOperator());

        ExpressionNode leftNode3 = rightNode2.getChildLeft();
        ExpressionNode rightNode3 = rightNode2.getChildRight();

        assertEquals("customer.address.zipCode", leftNode3.getRoValue1());
        assertEquals("null", leftNode3.getRoValue2());
        assertEquals("!=", leftNode3.getRelationalOperator());

        assertEquals("customer.address.city", rightNode3.getRoValue1());
        assertEquals("Chicago", rightNode3.getRoValue2());
        assertEquals("==", rightNode3.getRelationalOperator());
    }
}