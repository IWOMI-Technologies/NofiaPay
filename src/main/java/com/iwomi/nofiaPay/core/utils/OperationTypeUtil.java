package com.iwomi.nofiaPay.core.utils;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

import java.util.Arrays;
import java.util.Locale;

public class OperationTypeUtil {

    // Utility method to map a string to the appropriate OperationTypeEnum
    public static OperationTypeEnum getOperationTypeFromString(String operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }

        // Normalize the input string (to handle case-insensitive matching)
        String normalizedOperation = operation.trim().toUpperCase(Locale.ROOT);

        // Map the input string to the appropriate enum value
        switch (normalizedOperation) {
            case "CASH":
                return OperationTypeEnum.AGENT_CASH_COLLECTION;
            case "MOMO":
                return OperationTypeEnum.AGENT_DIGITAL_COLLECTION_MOMO;
            case "OM":
                return OperationTypeEnum.AGENT_DIGITAL_COLLECTION_OM;
            case "SELF_SERVICE_OM":
                return OperationTypeEnum.SELF_SERVICE_OM;
            case "SELF_SERVICE_MOMO":
                return OperationTypeEnum.SELF_SERVICE_MOMO;
            case "MERCHANT_CASH":
                return OperationTypeEnum.MERCHANT_CASH;
            case "MERCHANT_DIGITAL_MOMO":
                return OperationTypeEnum.MERCHANT_DIGITAL_MOMO;
            case "MERCHANT_DIGITAL_OM":
                return OperationTypeEnum.MERCHANT_DIGITAL_OM;
            case "AGENT_TO_TELLER":
                return OperationTypeEnum.AGENT_TO_TELLER;
            case "REVERSEMENT":
                return OperationTypeEnum.REVERSEMENT;
            default:
                throw new IllegalArgumentException("Invalid operation type: " + operation);
        }
    }
}
