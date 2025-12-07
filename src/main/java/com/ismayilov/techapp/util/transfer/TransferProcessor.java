package com.ismayilov.techapp.util.transfer;

import com.ismayilov.techapp.dto.response.mbdto.ValCursResponseDTO;
import com.ismayilov.techapp.dto.response.mbdto.ValTypeResponseDTO;
import com.ismayilov.techapp.dto.response.mbdto.ValuteResponseDTO;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.restclient.CbarRestClient;
import com.ismayilov.techapp.util.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
public class TransferProcessor {

    @Autowired
    CbarRestClient cbarRestClient;

    public void process(Account debit, Account credit, BigDecimal amount) {
        if (!debit.getCurrency().equals(credit.getCurrency())) {
            ValCursResponseDTO currency = cbarRestClient.getCurrency();
            currency.getValTypeList().forEach(valTypeResponseDTO -> {

                List<ValuteResponseDTO> valuteList = valTypeResponseDTO.getValuteList();
                if (Objects.nonNull(valuteList) && !ObjectUtils.isEmpty(valuteList)) {

                    //* USD -> AZN
                    valuteList.stream().filter(valuteResponseDTO ->
                                    Objects.nonNull(valuteResponseDTO)
                                            && !ObjectUtils.isEmpty(valuteResponseDTO)
                                            && valuteResponseDTO.getCode().equals(debit.getCurrency().toString())
                                            && debit.getCurrency().equals(Currency.USD)).findFirst()
                            .ifPresent(valuteResponseDTO -> {
                                debit.setBalance(debit.getBalance().subtract(amount));
                                credit.setBalance(credit.getBalance().add(amount.multiply(valuteResponseDTO.getValue())));
                            });

                    //* AZN -> USD
                    valuteList.stream().filter(valuteResponseDTO ->
                                    Objects.nonNull(valuteResponseDTO)
                                            && !ObjectUtils.isEmpty(valuteResponseDTO)
                                            && !valuteResponseDTO.getCode().equals(debit.getCurrency().toString())
                                            && valuteResponseDTO.getCode().equals(Currency.USD.toString())).findFirst()
                            .ifPresent(valuteResponseDTO -> {
                                debit.setBalance(debit.getBalance().subtract(amount));
                                credit.setBalance(credit.getBalance().add(amount.divide(valuteResponseDTO.getValue(), RoundingMode.DOWN)));
                            });
                }
            });

        } else {
            debit.setBalance(debit.getBalance().subtract(amount));
            credit.setBalance(credit.getBalance().add(amount));
        }
    }
}

