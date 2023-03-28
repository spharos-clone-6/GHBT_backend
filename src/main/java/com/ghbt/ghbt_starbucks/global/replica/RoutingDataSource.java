package com.ghbt.ghbt_starbucks.global.replica;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        System.out.println("Transaction의 Read Only가 " + isReadOnly + " 입니다.");

        if (isReadOnly) {
            System.out.println("Read 서버로 요청합니다.");
            return "read";
        }

        System.out.println("Write 서버로 요청합니다.");
        return "write";
    }

}
