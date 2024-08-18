package com.noobisoftcontrolcenter.tokemon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TokenCreateTransaction;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TransactionResponse;

@Service
public class TokenService {

    @Value("${spring.hedera.accountId}")
    private String operatorId;

    @Value("${spring.hedera.privateKey}")
    private String operatorKey;

    public TokenId createToken(String tokenName, String tokenSymbol, long initialSupply) throws Exception {
        Client client = Client.forTestnet();
        client.setOperator(AccountId.fromString(operatorId), PrivateKey.fromString(operatorKey));

        TransactionResponse transactionResponse = new TokenCreateTransaction()
                .setTokenName(tokenName)
                .setTokenSymbol(tokenSymbol)
                .setTreasuryAccountId(AccountId.fromString(operatorId))
                .setInitialSupply(initialSupply)
                .setAdminKey(PrivateKey.fromString(operatorKey).getPublicKey())
                //.setTokenType(TokenType.NON_FUNGIBLE_UNIQUE)
                .setSupplyKey(PrivateKey.fromString(operatorKey).getPublicKey())
                .execute(client);

        return transactionResponse.getReceipt(client).tokenId;
    }
}
