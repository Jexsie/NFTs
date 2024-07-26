package com.noobisoftcontrolcenter.needfortoken.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;

@Service
public class HederaService {
    private static final Logger logger = LoggerFactory.getLogger(HederaService.class);

    private final Client hederaClient;

    public HederaService(
            @Value("${spring.hedera.accountId}") String accountId,
            @Value("${spring.hedera.privateKey}") String privateKey,
            @Value("${spring.hedera.network.name}") String networkName
    ) {
        logger.debug("Hedera Account ID: {}", accountId);
        logger.debug("Hedera Private Key: {}", privateKey);
        try {
            hederaClient = networkName.equalsIgnoreCase("mainnet") ? Client.forMainnet() : Client.forTestnet();
            hederaClient.setOperator(AccountId.fromString(accountId), PrivateKey.fromString(privateKey));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Hedera configuration", e);
        }
    }

    // Add methods to interact with the Hedera network
}
