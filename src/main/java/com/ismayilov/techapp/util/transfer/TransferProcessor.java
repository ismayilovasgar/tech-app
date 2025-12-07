package com.ismayilov.techapp.util.transfer;

import com.ismayilov.techapp.dto.response.mbdto.ValuteResponseDTO;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.restclient.CbarRestClient;
import com.ismayilov.techapp.util.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
public class TransferProcessor {

    @Autowired
    CbarRestClient cbarRestClient;


    public void process(Account debit, Account credit, BigDecimal amount) {

        // 1. If both accounts have the same currency → simple transfer
        if (debit.getCurrency().equals(credit.getCurrency())) {
            simpleTransfer(debit, credit, amount);
            return;
        }

        // 2. Fetch all currency rates
        List<ValuteResponseDTO> allRates = getAllRates();

        if (debit.getCurrency().equals(Currency.USD) && credit.getCurrency().equals(Currency.AZN)) {
            // USD -> AZN
            BigDecimal rate = findRate(allRates); // 1 USD = X AZN
            BigDecimal creditAmount = amount.multiply(rate).setScale(2, RoundingMode.DOWN);// convert to AZN
            applyTransfer(debit, credit, amount, creditAmount); // debit in USD, credit in AZN

        } else if (debit.getCurrency().equals(Currency.AZN) && credit.getCurrency().equals(Currency.USD)) {
            // AZN -> USD
            BigDecimal rate = findRate(allRates); // 1 USD = X AZN
            BigDecimal creditAmount = amount.divide(rate, 2, RoundingMode.DOWN);// convert to USD
            applyTransfer(debit, credit, amount, creditAmount); // debit in AZN, credit in USD

        } else {
            throw new RuntimeException("Currency conversion not supported: "
                    + debit.getCurrency() + " -> " + credit.getCurrency());
        }
    }

    // ----------------- UTILITY METHODS -----------------

    /**
     * Get all currency rates from the Cbar response
     */
    private List<ValuteResponseDTO> getAllRates() {
        return cbarRestClient.getCurrency().getValTypeList().stream()
                .filter(Objects::nonNull)
                .flatMap(v -> v.getValuteList().stream())
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Find the conversion rate for a specific currency
     */
    private BigDecimal findRate(List<ValuteResponseDTO> list) {
        return list.stream()
                .filter(v -> v.getCode().equals(Currency.USD.toString()))
                .map(ValuteResponseDTO::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency not found: " + Currency.USD));
    }

    /**
     * Apply the transfer between debit and credit accounts
     *
     * @param debit        debit account (balance decreased by debitAmount)
     * @param credit       credit account (balance increased by creditAmount)
     * @param debitAmount  amount to subtract from debit account
     * @param creditAmount amount to add to credit account
     */
    private void applyTransfer(Account debit, Account credit, BigDecimal debitAmount, BigDecimal creditAmount) {
        debit.setBalance(debit.getBalance().subtract(debitAmount));
        credit.setBalance(credit.getBalance().add(creditAmount));
    }

    private void simpleTransfer(Account debit, Account credit, BigDecimal amount) {
        debit.setBalance(debit.getBalance().subtract(amount));
        credit.setBalance(credit.getBalance().add(amount));
    }

//    public void process(Account debit, Account credit, BigDecimal amount) {
//        if (!debit.getCurrency().equals(credit.getCurrency())) {
//            ValCursResponseDTO currency = cbarRestClient.getCurrency();
//            currency.getValTypeList().forEach(valTypeResponseDTO -> {
//
//                List<ValuteResponseDTO> valuteList = valTypeResponseDTO.getValuteList();
//                if (Objects.nonNull(valuteList) && !ObjectUtils.isEmpty(valuteList)) {
//
//                    //* USD -> AZN
//                    valuteList.stream().filter(valuteResponseDTO ->
//                                    Objects.nonNull(valuteResponseDTO)
//                                            && !ObjectUtils.isEmpty(valuteResponseDTO)
//                                            && valuteResponseDTO.getCode().equals(debit.getCurrency().toString())
//                                            && debit.getCurrency().equals(Currency.USD)).findFirst()
//                            .ifPresent(valuteResponseDTO -> {
//                                debit.setBalance(debit.getBalance().subtract(amount));
//                                credit.setBalance(credit.getBalance().add(amount.multiply(valuteResponseDTO.getValue())));
//                            });
//
//                    //* AZN -> USD
//                    valuteList.stream().filter(valuteResponseDTO ->
//                                    Objects.nonNull(valuteResponseDTO)
//                                            && !ObjectUtils.isEmpty(valuteResponseDTO)
//                                            && !valuteResponseDTO.getCode().equals(debit.getCurrency().toString())
//                                            && valuteResponseDTO.getCode().equals(Currency.USD.toString())).findFirst()
//                            .ifPresent(valuteResponseDTO -> {
//                                debit.setBalance(debit.getBalance().subtract(amount));
//                                credit.setBalance(credit.getBalance().add(amount.divide(valuteResponseDTO.getValue(), RoundingMode.DOWN)));
//                            });
//                }
//            });
//
//        } else {
//            debit.setBalance(debit.getBalance().subtract(amount));
//            credit.setBalance(credit.getBalance().add(amount));
//        }
//    }
}

