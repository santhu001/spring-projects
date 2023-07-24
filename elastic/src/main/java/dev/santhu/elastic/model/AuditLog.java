package dev.santhu.elastic.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

@Data
public class AuditLog {
    private String message;
    private String category;

    private Long timestamp;
    private String transactionType;
    private String operation;
    private String status;
    private String id;
    private String currency;
    private String value;
    private String shopId;
    private String terminalId;
    private String brand;
    private String userId;
    private String userName;
    private String level;
    private String transactionId;
    private String source;
    private String betSlipType;
    private String betSlipStatus;
    private String betSlipPayoutStatus;
    private String ticketType;
    private String amlDecisionStatus;
    private String errorCode;
    private String errorMessage;
    private String errorDescription;
    private Long backendProcessingTime;
    private Long totalProcessingTime;
    private String country;
    private String regionCode;
    private String regionAreaCode;
}
