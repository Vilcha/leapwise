package com.vilim.leapwise.model.tree;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ExpressionNode {
    private String roValue1;
    private String roValue2;
    private String relationalOperator;
    private String logicalOperator;
    private ExpressionNode childRight;
    private ExpressionNode childLeft;

    public boolean evaluate(final Map<String, String> flatJson) {
        if (childLeft != null && childRight != null) {
            if (logicalOperator.equals("AND")) {
                return childLeft.evaluate(flatJson) && childRight.evaluate(flatJson);
            } else {
                return childLeft.evaluate(flatJson) || childRight.evaluate(flatJson);
            }
        }
        return switch (relationalOperator) {
            case "==" -> compareValues(flatJson, relationalOperator) == 0;
            case "!=" -> compareValues(flatJson, relationalOperator) != 0;
            case "<" -> compareValues(flatJson, relationalOperator) < 0;
            case "<=" -> compareValues(flatJson, relationalOperator) <= 0;
            case ">" -> compareValues(flatJson, relationalOperator) > 0;
            case ">=" -> compareValues(flatJson, relationalOperator) >= 0;
            default -> throw new IllegalStateException("Unexpected relational operator: " + relationalOperator);
        };
    }

    private int compareValues(final Map<String, String> flatJson, final String relationalOperator) {
        String stringRoValue1 = getMapValue(flatJson, roValue1);
        String stringRoValue2 = getMapValue(flatJson, roValue2);

        Double numericRoValue1 = tryParseDouble(stringRoValue1);
        Double numericRoValue2 = tryParseDouble(stringRoValue2);
        if ("==".equals(relationalOperator) || "!=".equals(relationalOperator)) {
            if (numericRoValue1 != null && numericRoValue2 != null) {
                return Double.compare(numericRoValue1, numericRoValue2);
            } else if (numericRoValue1 == null && numericRoValue2 == null) {
                return String.CASE_INSENSITIVE_ORDER.compare(stringRoValue1, stringRoValue2);
            } else {
                return 0;
            }
        } else {
            if (numericRoValue1 != null && numericRoValue2 != null) {
                return Double.compare(numericRoValue1, numericRoValue2);
            } else if (numericRoValue1 == null && numericRoValue2 == null) {
                return String.CASE_INSENSITIVE_ORDER.compare(stringRoValue1, stringRoValue2);
            } else {
                throw new IllegalStateException("Incomparable value types: "
                        + stringRoValue1 + " " + relationalOperator + " " + stringRoValue2);
            }
        }
    }

    private String getMapValue(final Map<String, String> flatJson, final String roValue) {
        if (flatJson.containsKey(roValue)) {
            String val = flatJson.get(roValue);
            if (val == null) {
                return "null";
            }
            return val;
        } else {
            return roValue;
        }
    }

    private Double tryParseDouble(final Object value) {
        if (value == null) {
            return null;
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
